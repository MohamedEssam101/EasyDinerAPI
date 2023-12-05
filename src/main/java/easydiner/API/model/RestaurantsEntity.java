package easydiner.API.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * The type Restaurants entity.
 */
@Entity
@Table(name="Restaurants") // Specify the correct table name
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restaurant_id")
    private int restaurantId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    public RestaurantsEntity(String name, String description, String location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }
}
