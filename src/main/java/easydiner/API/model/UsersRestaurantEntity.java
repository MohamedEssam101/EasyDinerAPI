package easydiner.API.model;

import jakarta.persistence.*;
import lombok.*;


/**
 * The type User restaurant entity.
 */
@Entity
@Table(name = "UserRestaurant") // Specify the correct table name
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersRestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Assuming you have an ID for this entity

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantsEntity restaurant;


    // Constructors...

    // Getters and Setters...
}
