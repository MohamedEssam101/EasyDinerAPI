package easydiner.API.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class MenuRequest {

    @NotNull(message = "restaurantId can't be null")
    private int restaurantId;
    @NotBlank(message = "item name can't be null")
    private String itemName;
    @NotBlank(message = "description of the item can't be null")
    private String itemDescription;

    @NotNull(message = "Price can't be null")
    @Min(value = 1, message = "Price must be greater than or equal to 1")
    private float price;

    private byte[] menuImage;
}
