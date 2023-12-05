package easydiner.API.responses;

/**
 * The type Add user response.
 */
public class AddUserResponse extends  UserResponse{
    /**
     * Instantiates a new Add user response.
     *
     * @param username the username
     * @param password the password
     * @param email    the email
     */
    public AddUserResponse(String username, String password, String email) {
        super(username, password, email);
    }
}
