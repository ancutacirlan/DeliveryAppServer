package com.ppaw.deliveryApp.OrderClient;

import com.ppaw.deliveryApp.Configuration.Audit;
import com.ppaw.deliveryApp.Menu.Menu;
import com.ppaw.deliveryApp.Order.Orders;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "order_client")
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_deleted = false")
@Data
public class OrderClient extends Audit implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Menu product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @Column(name = "poduct_quantity")
    private Integer productQuantity;

}
