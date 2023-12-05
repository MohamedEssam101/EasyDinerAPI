package easydiner.API.responses;

/**
 * The type Update user response.
 */
public class UpdateUserResponse extends UserResponse{
    /**
     * Instantiates a new Update user response.
     *
     * @param username the username
     * @param password the password
     * @param email    the email
     */
    public UpdateUserResponse(String username, String password, String email) {
        super(username, password, email);
    }
}
