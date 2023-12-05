package easydiner.API.model;
import jakarta.persistence.*;
import lombok.*;
/**
 * The type Menu entity.
 */
@Entity
@Table(name = "Menus") // Specify the correct table name
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="menu_id")
    private int menuId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantsEntity restaurantId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @Column(name = "menu_image")
    private byte[] menu_image;

    public MenuEntity(RestaurantsEntity restaurantId, String name, String description, float price, byte[] menu_image) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.menu_image = menu_image;
    }
    public MenuEntity(int id, RestaurantsEntity restaurantId, String name, String description, float price) {
        this.menuId = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

}

