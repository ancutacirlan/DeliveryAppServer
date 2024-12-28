package com.ppaw.deliveryApp.Order;


import com.ppaw.deliveryApp.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {

    private final OrderService service;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.service = orderService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> create(@RequestBody @Valid PlaceOrderDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PostMapping(path = "/calculate/total/price", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderPricesDto> calculateTotalPrice(@RequestBody ListOfProductsDto dto) {
        System.out.println("DTOOOOO:"+dto);
        return ResponseEntity.ok(service.calculateTotalPrice(dto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDto> update(@PathVariable UUID id, @RequestBody OrderStatusDto order) throws NotFoundException {
        OrderDto updated = service.update(id, order);
        return ResponseEntity.ok(updated);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(path = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> getById(@RequestParam("id") UUID id) throws NotFoundException {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable(value = "id") UUID id) throws Exception {
        service.delete(id);
        return ResponseEntity.ok("Order deleted successfully.");
    }

}
