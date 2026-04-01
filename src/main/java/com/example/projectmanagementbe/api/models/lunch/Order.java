package com.example.projectmanagementbe.api.models.lunch;
import com.example.projectmanagementbe.api.models.Auditable;
import com.example.projectmanagementbe.auth.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "order_date"})
})
public class Order extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(length = 20, nullable = false)
    private String status = "PENDING";

    // Cascade: create/delete order_items together with order
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("priority ASC")
    private List<OrderItem> orderItems = new ArrayList<>();
}