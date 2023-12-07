package easydiner.API.exception;

public class UnauthorizedException extends  RuntimeException{
    public UnauthorizedException() {
        super("User does not have access to this operation");
    }

}
