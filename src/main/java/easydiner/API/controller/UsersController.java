package easydiner.API.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import easydiner.API.requests.AddUserRequest;
import easydiner.API.requests.UpdateUserRequest;
import easydiner.API.responses.AddUserResponse;
import easydiner.API.responses.GetUserResponse;
import easydiner.API.responses.GetUsersResponses;
import easydiner.API.responses.UpdateUserResponse;
import easydiner.API.service.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@RestController
@Log4j2
@AllArgsConstructor
@Tag(name = "Users API", description = "Operations related to users")
public class UsersController {

    private final UsersService usersService;

    @Operation(summary = "Add a new user", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully added a new user"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("create/users")
    public ResponseEntity<AddUserResponse> addUser(
            @RequestBody @Valid AddUserRequest userRequest) {
        log.info("Create User API: {}", userRequest);
        AddUserResponse userResponse = usersService.AddUserRequest(userRequest);
        log.info("Inserted AddUserResponse successfully as: {}", userResponse);
        return ResponseEntity.ok().body(userResponse);
    }

    @Operation(summary = "Update an existing user", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("users/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @RequestBody @Valid UpdateUserRequest userRequest,
            @PathVariable("userId") int id) {
        log.info("Update User API: {} for ID: {}", userRequest, id);
        UpdateUserResponse userResponse = usersService.updateUserResponse(userRequest, id);
        log.info("Returning updated UpdateUserResponse successfully as: {}", userResponse);
        return ResponseEntity.ok().body(userResponse);
    }

    @Operation(summary = "Get details of a specific user", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user details"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("users/{userId}")
    public ResponseEntity<GetUserResponse> getResponse(
            @PathVariable("userId") int userId,
            Authentication authentication) {
        log.info("Get User API for ID: {}", userId);
        GetUserResponse userResponse = usersService.getUserResponse(userId, authentication);
        log.info("Returning GetUserResponse successfully as: {}", userResponse);
        return ResponseEntity.ok().body(userResponse);
    }

    @Operation(summary = "Get details of all users", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users"),
            @ApiResponse(responseCode = "404", description = "No users found")
    })
    @GetMapping("users")
    public ResponseEntity<GetUsersResponses> getUsers(Authentication authentication) {
        log.info("Getting all users API");
        GetUsersResponses usersResponses = usersService.getUsersResponses(authentication);
        log.info("Returning GetUsersResponses successfully as: {}", usersResponses);
        return ResponseEntity.ok().body(usersResponses);
    }

    @Operation(summary = "Delete a user by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("users/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable int userId) {
        log.info("Delete User API for ID: {}", userId);
        usersService.deleteUserRequest(userId);
        log.info("User deleted successfully");
        String message = "User with ID " + userId + " is deleted successfully";
        return ResponseEntity.ok().body(message);
    }

    public static String getAuthName(Authentication authentication) {
        return Optional.of(authentication.getPrincipal())
                .filter(OidcUser.class::isInstance)
                .map(OidcUser.class::cast)
                .map(OidcUser::getEmail)
                .orElseGet(authentication::getName);
    }
}

