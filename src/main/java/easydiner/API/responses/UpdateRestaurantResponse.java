package easydiner.API.responses;

import jakarta.validation.constraints.NotBlank;

public class UpdateRestaurantResponse extends RestaurantResponse{
    public UpdateRestaurantResponse(@NotBlank(message = "name of the restaurant can't be null") String name, @NotBlank(message = "description can't be null") String description, @NotBlank(message = "location can't be null") String location) {
        super(name, description, location);
    }
}
