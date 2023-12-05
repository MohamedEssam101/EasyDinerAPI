package easydiner.API.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddOrderRequest  extends OrderRequest{

    public AddOrderRequest(@NotNull(message = "restaurantId can't be null") int restaurantId) {
        super(restaurantId);
    }

}
