package com.ppaw.deliveryApp.UserSubscription;

import com.ppaw.deliveryApp.User.UserService;
import com.ppaw.deliveryApp.User.Users;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/user/subscription")
@Validated
public class UserSubscriptionController {

    UserSubscriptionService userSubscriptionService;
    UserService userService;

    @Autowired
    public UserSubscriptionController(UserSubscriptionService userSubscriptionService, UserService userService) {
        this.userSubscriptionService = userSubscriptionService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserSubscriptionDto> create(@RequestBody CreateUserSubscriptionDto userSubscriptionDto) {
        UserSubscriptionDto created = userSubscriptionService.create(userSubscriptionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSubscriptionDto> update(@PathVariable UUID id, @RequestBody UserSubscriptionDto userSubscription) throws NotFoundException {
        UserSubscriptionDto updated = userSubscriptionService.update(id, userSubscription);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSubscriptionDto> getById(@PathVariable UUID id) throws NotFoundException {
        UserSubscriptionDto userSubscription = userSubscriptionService.getById(id);
        return ResponseEntity.ok(userSubscription);
    }

    @GetMapping("")
    public ResponseEntity<List<UserSubscriptionDto>> getAllByUser() {
        List<UserSubscriptionDto> subscriptions = userSubscriptionService.getAllByUser();
        return ResponseEntity.ok(subscriptions);
    }


    @DeleteMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable(value = "id") UUID id) {
        userSubscriptionService.delete(id);
        return ResponseEntity.ok("Subscription deleted successfully.");
    }
}
