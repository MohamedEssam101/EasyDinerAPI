package easydiner.API.requests;

/**
 * The type Add user request.
 */
public class AddUserRequest extends UserRequest{

    /**
     * Instantiates a new Add user request.
     *
     * @param username the username
     * @param password the password
     * @param email    the email
     */
    public AddUserRequest(String username,
                          String password,
                          String email
                          ) {
        super(username, password, email);
    }
}
