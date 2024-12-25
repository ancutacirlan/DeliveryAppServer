package com.ppaw.deliveryApp.UserSubscription;

import com.ppaw.deliveryApp.Subscription.Subscription;
import com.ppaw.deliveryApp.User.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_subscription")
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSubscription implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

}
