package easydiner.API.responses;

import easydiner.API.Enum.Status;

import lombok.ToString;


@ToString
public class GetOrderResponse extends OrderResponse {


    public GetOrderResponse(Status status, String restaurantName) {
        super(status, restaurantName);
    }
}
