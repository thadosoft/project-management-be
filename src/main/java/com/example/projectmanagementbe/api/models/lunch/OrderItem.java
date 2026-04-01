package com.example.projectmanagementbe.api.models.lunch;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"order_id", "priority"}),
        @UniqueConstraint(columnNames = {"order_id", "menu_item_id"})
})
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private Food menuItem;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false, columnDefinition = "TINYINT")
    private Integer priority;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(length = 500)
    private String note;
}