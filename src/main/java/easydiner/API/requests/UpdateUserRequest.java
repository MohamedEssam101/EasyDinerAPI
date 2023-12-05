package easydiner.API.requests;


import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * The type Update user request.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateUserRequest {
    private String username;
    private String password;

    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            message = "Email is not valid")
    private String email;


}

