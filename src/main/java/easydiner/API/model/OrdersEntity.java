package easydiner.API.model;

import jakarta.persistence.*;
import lombok.*;
import easydiner.API.Enum.Status;

/**
 * The type Orders entity.
 */
@Entity
@Table(name = "Orders") // Specify the correct table name
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private UsersEntity userId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantsEntity restaurantId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING) // Assuming Status is an Enum
    private Status status;

    public OrdersEntity(UsersEntity userId, RestaurantsEntity restaurant_id, Status status) {
        this.userId = userId;
        this.restaurantId = restaurant_id;
        this.status = status;
    }

    public OrdersEntity(RestaurantsEntity restaurant_id, Status status) {
        this.restaurantId = restaurant_id;
        this.status = status;
    }

    public OrdersEntity(UsersEntity userId, RestaurantsEntity restaurant_id) {
        this.userId = userId;
        this.restaurantId = restaurant_id;
    }

    public OrdersEntity(Status status) {
        this.status = status;
    }
}
