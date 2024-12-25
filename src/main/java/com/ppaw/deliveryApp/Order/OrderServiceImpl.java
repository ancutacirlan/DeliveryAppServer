package com.ppaw.deliveryApp.Order;

import com.ppaw.deliveryApp.Menu.Menu;
import com.ppaw.deliveryApp.Menu.MenuRepository;
import com.ppaw.deliveryApp.OrderClient.OrderClient;
import com.ppaw.deliveryApp.OrderClient.PlaceOrderClientDto;
import com.ppaw.deliveryApp.OrderClient.OrderClientRepository;
import com.ppaw.deliveryApp.Subscription.Subscription;
import com.ppaw.deliveryApp.Subscription.SubscriptionName;
import com.ppaw.deliveryApp.Subscription.SubscriptionRepository;
import com.ppaw.deliveryApp.Subscription.TypeSubscription;
import com.ppaw.deliveryApp.User.UserService;
import com.ppaw.deliveryApp.User.Users;
import com.ppaw.deliveryApp.UserSubscription.UserSubscription;
import javassist.NotFoundException;
import org.antlr.v4.runtime.misc.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final MenuRepository menuRepository;
    private final OrderClientRepository orderClientRepository;
    private final ModelMapper modelMapper;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService,
                            MenuRepository menuRepository, OrderClientRepository orderClientRepository,
                            ModelMapper modelMapper, SubscriptionRepository subscriptionRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.menuRepository = menuRepository;
        this.orderClientRepository = orderClientRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.modelMapper = modelMapper;
        modelMapper.addMappings(Utils.orderMapping);
        modelMapper.addMappings(Utils.orderFieldMapping);
        modelMapper.addMappings(Utils.orderClientMapping);
        modelMapper.addMappings(Utils.orderClientFieldMapping);
    }

    @Override
    @CachePut(value = "orders", key = "#result.id")
    public OrderDto create(@NotNull PlaceOrderDto placeOrderDto) {
        logger.info("Creating a new order for the current authenticated user");

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userService.findByEmail(email);

        // Calcularea prețului total și al transportului
        ListOfProductsDto listOfProductsDtos = new ListOfProductsDto();
        listOfProductsDtos.setOrderItems(placeOrderDto.getOrderItems());
        OrderPricesDto orderPrices = calculateTotalPrice(listOfProductsDtos);

        // Crearea și salvarea comenzii
        Orders newOrder = new Orders();
        newOrder.setClient(user);
        newOrder.setStatus(Status.ORDERED);
        newOrder.setMentions(placeOrderDto.getMentions());
        newOrder.setOrderPrice(orderPrices.getProductsPrice());
        newOrder.setTransportPrice(orderPrices.getTransportPrice());
        Orders savedOrder = orderRepository.save(newOrder);
        logger.info("Order created and saved with ID: {}", savedOrder.getId());

        // Procesarea produselor din comandă
        List<OrderClient> orderItems = mapOrderClients(placeOrderDto.getOrderItems(), savedOrder);

        // Salvarea produselor din comandă
        orderItems.forEach(orderClientRepository::save);
        logger.info("Order creation process completed successfully for order ID: {}", savedOrder.getId());

        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAll() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userService.findByEmail(email);

        logger.info("Retrieving all orders for user with ID: {}", user.getId());
        List<Orders> orders = orderRepository.findAllByClient(user);

        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .toList();
    }

    @Override
    @Cacheable(value = "orders", key = "#id")
    public OrderDto getById(UUID id) throws NotFoundException {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));

        logger.info("Successfully retrieved order with ID: {}", id);
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    @CacheEvict(value = "orders", key = "#id")
    public void delete(UUID id) throws Exception {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));

        if (order.getStatus() == Status.IN_PREPARATION || order.getStatus() == Status.OUT_FOR_DELIVERY) {
            throw new Exception("The order cannot be deleted because it is either in preparation or ready for delivery.");
        }

        order.setIsDeleted(true);
        orderRepository.save(order);
    }

    @Override
    @CachePut(value = "orders", key = "#result.id")
    public OrderDto update(UUID id, OrderStatusDto orderStatusDto) throws NotFoundException {
        Orders existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));

        existingOrder.setStatus(orderStatusDto.getStatus());
        logger.info("Order status updated: {}", existingOrder.getId());

        return modelMapper.map(orderRepository.save(existingOrder), OrderDto.class);
    }

    @Override
    public OrderPricesDto calculateTotalPrice(ListOfProductsDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userService.findByEmail(email);

        Map<UUID, Menu> productMap = menuRepository.findAllById(
                dto.getOrderItems().stream()
                        .map(PlaceOrderClientDto::getProductId)
                        .collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(Menu::getId, menu -> menu));

        double productsPrice = dto.getOrderItems().stream()
                .mapToDouble(orderClient -> {
                    Menu product = productMap.get(orderClient.getProductId());
                    return (product != null && orderClient.getProductQuantity() != null)
                            ? product.getPrice() * orderClient.getProductQuantity()
                            : 0.0;
                }).sum();

        var subscription = getNonFreeActiveSubscription(user)
                .orElse(subscriptionRepository.getSubscriptionByName(SubscriptionName.BASIC_PLAN));

        double transportPrice = (subscription.getName().equals(SubscriptionName.BASIC_PLAN) ||
                productsPrice < subscription.getMinimumForFreeTransport())
                ? subscription.getTransportPrice()
                : 0.0;

        return new OrderPricesDto(productsPrice + transportPrice, transportPrice, productsPrice);
    }

    private static Optional<Subscription> getNonFreeActiveSubscription(Users user) {
        LocalDate today = LocalDate.now();
        return user.getSubscriptions().stream()
                .filter(subscription ->
                        subscription.getSubscription() != null &&
                                !TypeSubscription.FREE.toString().equals(subscription.getSubscription().getTypeSubscription().toString()) &&
                                (today.isEqual(subscription.getStartDate()) || today.isAfter(subscription.getStartDate())) &&
                                (today.isEqual(subscription.getEndDate()) || today.isBefore(subscription.getEndDate()))
                )
                .map(UserSubscription::getSubscription)
                .findFirst();
    }

    private List<OrderClient> mapOrderClients(List<PlaceOrderClientDto> orderItems, Orders order) {
        List<UUID> productIds = orderItems.stream()
                .map(PlaceOrderClientDto::getProductId)
                .toList();

        Map<UUID, Menu> productMap = menuRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Menu::getId, menu -> menu));

        return orderItems.stream()
                .map(item -> {
                    Menu product = productMap.get(item.getProductId());
                    if (product != null && item.getProductQuantity() != null) {
                        OrderClient orderClient = new OrderClient();
                        orderClient.setOrder(order);
                        orderClient.setProduct(product);
                        orderClient.setProductQuantity(item.getProductQuantity());
                        return orderClient;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
