package easydiner.API.exception;

public class UserHasNoRestaurantsException extends RuntimeException {
    public UserHasNoRestaurantsException() {
        super("User does not have associated restaurants.");
    }
}
