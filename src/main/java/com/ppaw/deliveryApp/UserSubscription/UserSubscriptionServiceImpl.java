package com.ppaw.deliveryApp.UserSubscription;


import com.ppaw.deliveryApp.Exceptions.NotFoundException;
import com.ppaw.deliveryApp.Subscription.SubscriptionRepository;
import com.ppaw.deliveryApp.User.UserService;
import com.ppaw.deliveryApp.User.Users;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserSubscriptionServiceImpl implements UserSubscriptionService {
    private static final Logger logger = LoggerFactory.getLogger(UserSubscriptionServiceImpl.class);

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserSubscriptionServiceImpl(ModelMapper modelMapper, UserSubscriptionRepository userSubscriptionRepository,
                                       SubscriptionRepository subscriptionRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
        modelMapper.addMappings(Utils.userSubscriptionFieldMapping);
    }

    @Override
    @CacheEvict(value = "userSubscriptions", key = "#id")
    public void delete(UUID id) {
        logger.info("Attempting to delete UserSubscription with id: {}", id);
        try {
            userSubscriptionRepository.deleteById(id);
            logger.info("Successfully deleted UserSubscription with id: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete UserSubscription with id: {}. Error: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    @CachePut(value = "userSubscriptions", key = "#result.id")
    public UserSubscriptionDto create(CreateUserSubscriptionDto userSubscription) {
        var subscription = subscriptionRepository.getSubscriptionById(userSubscription.getSubscriptionId());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userService.findByEmail(email);

        var newSubscription = new UserSubscription();
        newSubscription.setUser(user);
        newSubscription.setSubscription(subscription);
        //newSubscription.setIsActive(true);
        newSubscription.setStartDate(userSubscription.getStartDate());
        newSubscription.setEndDate(userSubscription.getEndDate());
        var createdSubscription = userSubscriptionRepository.save(newSubscription);

        logger.info("User subscription created: {}", createdSubscription.getId());

        return modelMapper.map(createdSubscription, UserSubscriptionDto.class);
    }

    @Override
    @CachePut(value = "userSubscriptions", key = "#result.id")
    public UserSubscriptionDto update(UUID id, UserSubscriptionDto userSubscription) throws NotFoundException {
        UserSubscription existing = userSubscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserSubscription not found with id " + id));

        existing.setStartDate(userSubscription.getStartDate());
        existing.setEndDate(userSubscription.getEndDate());
        //existing.setIsActive(userSubscription.getIsActive());

        logger.info("User subscription updated: {}", userSubscription.getId());
        return modelMapper.map(userSubscriptionRepository.save(existing), UserSubscriptionDto.class);
    }

    @Override
    @Cacheable(value = "userSubscriptions", key = "#id")
    public UserSubscriptionDto getById(UUID id) throws NotFoundException {
        logger.info("Attempting to retrieve UserSubscription with id: {}", id);

        return userSubscriptionRepository.findById(id)
                .map(subscription -> {
                    logger.debug("Mapping UserSubscription to UserSubscriptionDto for id: {}", id);
                    return modelMapper.map(subscription, UserSubscriptionDto.class);
                })
                .orElseThrow(() -> {
                    logger.error("UserSubscription not found with id: {}", id);
                    return new NotFoundException("UserSubscription not found with id " + id);
                });
    }


    @Override
    public List<UserSubscriptionDto> getAllByUser() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userService.findByEmail(email);

        return userSubscriptionRepository.findByUser(user)
                .stream()
                .map(x -> modelMapper.map(x, UserSubscriptionDto.class))
                .collect(Collectors.toList());
    }

}