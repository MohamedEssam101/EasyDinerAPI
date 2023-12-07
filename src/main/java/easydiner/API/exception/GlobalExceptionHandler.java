package easydiner.API.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>>  handleValidationErrors
            (MethodArgumentNotValidException ex) {
        List<String> errors =ex.getBindingResult().getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).collect(Collectors.toList());

        return new ResponseEntity<>(getErrorsMap(errors),new HttpHeaders() , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, List<String>>>
    handleNotFoundException(NotFoundException ex) {
        /*
        Created a list to hold the error message i provided in ArticleService.
        Used SingeltonList to create immutable list containing only 1 error message
         */
        List<String> errors = Collections.singletonList(ex.getMessage());
        /*
        returning the response including (error message with error 404)
         */
        return  new ResponseEntity<>(getErrorsMap(errors),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    private Map <String, List<String>> getErrorsMap(List<String> errors) {

        Map<String, List<String >> errorResponse = new HashMap<>();

        errorResponse.put("errors", errors);

        return errorResponse;
    }
    @ExceptionHandler(UserHasNoRestaurantsException.class)
    public ResponseEntity<String> handleUserHasNoRestaurantsException(UserHasNoRestaurantsException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not have associated restaurants.");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not have access to this operation");
    }
}
