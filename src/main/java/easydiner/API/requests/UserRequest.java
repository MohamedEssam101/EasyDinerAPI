package easydiner.API.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type User request.
 */
@Setter
@Getter
@AllArgsConstructor
@ToString
public class UserRequest {

    @NotBlank(message = "username cannot be null")
    private String username;
    @NotBlank(message = "password cannot be null")
    private String password;
    @Email(message = "email is not valid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotBlank(message = "email cannot be empty")
    private String email;
}
