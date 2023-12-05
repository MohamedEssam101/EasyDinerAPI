package easydiner.API.responses;

import easydiner.API.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {

    private Status status;
    private String restaurantName;
}
