package easydiner.API.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Get users responses.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class GetUsersResponses {

    private List<GetUserResponse> users;
}
