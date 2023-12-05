package easydiner.API.requests;

import jakarta.validation.constraints.NotBlank;

public class UpdateRestaurantRequest extends RestaurantRequest{
    public UpdateRestaurantRequest(@NotBlank(message = "name of the restaurant can't be null") String name, @NotBlank(message = "description can't be null") String description, @NotBlank(message = "location can't be null") String location) {
        super(name, description, location);
    }
}
