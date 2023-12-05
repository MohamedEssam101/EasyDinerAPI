package easydiner.API.responses;

import easydiner.API.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Get user response.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class GetUserResponse{

    private String username ;
    private String password;
    private String email;
    private Role role;


}
