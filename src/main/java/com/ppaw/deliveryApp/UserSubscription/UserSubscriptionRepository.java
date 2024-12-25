package com.ppaw.deliveryApp.UserSubscription;

import com.ppaw.deliveryApp.Menu.Menu;
import com.ppaw.deliveryApp.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UUID> {

  //  List<UserSubscription> findByUserAndIsActive(Users user, Boolean isActive);

    List<UserSubscription> findByUser(Users user);
}


