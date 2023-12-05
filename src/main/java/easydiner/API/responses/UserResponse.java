package easydiner.API.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type User response.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserResponse {


    private String username;
    private String password;
    private String email;

}
