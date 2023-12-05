package easydiner.API.requests;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "restaurantId can't be null")
    private int restaurantId;

}
