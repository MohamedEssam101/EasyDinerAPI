package easydiner.API.responses;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RestaurantResponse {

    @NotBlank(message = "name of the restaurant can't be null")
    private String name;
    @NotBlank(message = "description can't be null")
    private String description;
    @NotBlank(message = "location can't be null")
    private String location;
}
