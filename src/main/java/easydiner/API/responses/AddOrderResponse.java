package easydiner.API.responses;

import easydiner.API.Enum.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AddOrderResponse extends OrderResponse{


    public AddOrderResponse(Status status, String restaurantName) {
        super(status, restaurantName);
    }
}
