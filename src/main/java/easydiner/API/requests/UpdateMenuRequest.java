package easydiner.API.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateMenuRequest extends MenuRequest{

    public UpdateMenuRequest(@NotNull(message = "restaurantId can't be null") int restaurantId, @NotBlank(message = "item name can't be null") String itemName, @NotBlank(message = "description of the item can't be null") String itemDescription, @NotNull(message = "Price can't be null") @Min(value = 1, message = "Price must be greater than or equal to 1") float price, byte[] menuImage) {
        super(restaurantId, itemName, itemDescription, price, menuImage);
    }
}
