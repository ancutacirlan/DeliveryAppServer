package com.ppaw.deliveryApp.Subscription;

import com.ppaw.deliveryApp.Configuration.Audit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Where(clause = "is_deleted = false")
@Table(name = "subscription")
public class Subscription extends Audit {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    @Enumerated(value = EnumType.STRING)
    private SubscriptionName name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "transport_price")
    private Double transportPrice;

    @Column(name = "minimum_for_free_transport")
    private Double minimumForFreeTransport;

    @Column(name = "typeSubscription", length = Integer.MAX_VALUE)
    @Enumerated(value = EnumType.STRING)
    private TypeSubscription typeSubscription;

}