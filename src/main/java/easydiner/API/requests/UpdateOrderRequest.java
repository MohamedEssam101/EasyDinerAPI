package easydiner.API.requests;

import easydiner.API.Enum.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateOrderRequest {

    private Status status;
    public UpdateOrderRequest(Status status) {
        this.status = status;
    }
}
