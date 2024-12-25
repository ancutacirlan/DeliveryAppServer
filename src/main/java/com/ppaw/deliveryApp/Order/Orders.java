package com.ppaw.deliveryApp.Order;

import com.ppaw.deliveryApp.Configuration.Audit;
import com.ppaw.deliveryApp.OrderClient.OrderClient;
import com.ppaw.deliveryApp.User.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_deleted = false")
@Data
public class Orders extends Audit implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client")
    private Users client;

    @Column(name = "order_price")
    private Double orderPrice;

    @Column(name = "transport_price")
    private Double transportPrice;

    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "delivery_man_id")
    private Users deliveryMan;

    @Column(name = "mentions")
    private String mentions;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderClient> orderItems;

//    @ManyToMany
//    @JoinTable(
//            name = "order_client",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id")
//    )
//    private List<Menu> orderItems = new ArrayList<>();
}

