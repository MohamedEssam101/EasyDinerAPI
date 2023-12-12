package easydiner.API.service;

import easydiner.API.config.security.CustomUserDetails;
import easydiner.API.exception.NotFoundException;
import easydiner.API.mapper.UsersMapper;
import easydiner.API.model.UsersEntity;
import easydiner.API.repository.UsersRepository;
import easydiner.API.requests.AddUserRequest;
import easydiner.API.requests.UpdateUserRequest;
import easydiner.API.responses.AddUserResponse;
import easydiner.API.responses.GetUserResponse;
import easydiner.API.responses.GetUsersResponses;
import easydiner.API.responses.UpdateUserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * The type Users service.
 */
@Service
@AllArgsConstructor
@Log4j2
public class UsersService {


    private final UsersMapper usersMapper;
    private final UsersRepository usersRepository;

    /**
     * Add user request add user response.
     *
     * @param userRequest the user request
     * @return the add user response
     */
    public AddUserResponse AddUserRequest(AddUserRequest userRequest) {
        log.info("inside the service class for AddUserRequest");
        // Map the user request to a UsersEntity to prepare for insertion into the database.
        UsersEntity usersEntity = usersMapper.mapUserRequestToUsersEntity(userRequest);
        // Insert the user into the database using the repository.
        usersRepository.insertUser(usersEntity);
        // Map the database entity to an AddUserResponse for the response.
        AddUserResponse userResponse = usersMapper.mapToAddUserResponse(usersEntity);
        // Return the response object.
        return userResponse;
    }
    /**
     * Update user response update user response.
     *
     * @param updateUserRequest the update user request
     * @param userId            the user id
     * @return the update user response
     */
    public UpdateUserResponse updateUserResponse(UpdateUserRequest updateUserRequest, int userId) {
        int authenticatedUserId = getAuthenticatedUserId();

        // Check if the user making the request has access to update this user
        if (userId != authenticatedUserId) {
            throw new NotFoundException("User does not have access to this operation");
        }

        // Retrieve the user information based on the provided user ID
        UsersEntity user = usersRepository.findById(userId);
        log.info("Retrieved user data: {}", user);

        // Handle the case where the user with the given ID is not found
        if (user == null) {
            throw new NotFoundException("User with ID " + userId + " not found");
        }

        // Build the updated user information based on the current user and the update request
        UpdateUserRequest updatedUser = buildUpdatedUser(user, updateUserRequest);
        log.info("Inside Service for UpdateUserResponse: {}", updatedUser);

        // Map the updated user information to a UsersEntity object
        UsersEntity updatedEntity = usersMapper.mapUpdateRequestToUserEntity(updatedUser);

        // Update the user's information in the repository
        usersRepository.updateUser(updatedEntity, userId);
        log.info("Inside Service for entity: {}", updatedEntity);

        // Map the updated UsersEntity to an UpdateUserResponse object
        UpdateUserResponse userResponse = usersMapper.mapToUsersResponse(updatedEntity);

        // Return the response indicating the result of the user update operation
        return userResponse;
    }


    /**
     * Gets user response.
     *
     * @param userId the user id
     * @return the user response
     */
    public GetUserResponse getUserResponse(int userId, Authentication authentication) {

        log.info(authentication);
        if (isGoogleAuthentication(authentication)) {
            // If using Google authentication, the user is identified by the Google email
            String googleUserEmail = getAuthIdentifier(authentication);

            // Retrieve the user information based on the Google email
            UsersEntity entity = usersRepository.findByEmail(googleUserEmail);
            UsersEntity entity1 = usersRepository.findById(userId);
            // Check if the authenticated user is trying to access their own information
            if (entity != null && !entity.getEmail().equals(entity1.getEmail())) {
                throw new NotFoundException("You do not have access to retrieve this user's information");
            }

            // Map the retrieved UsersEntity to a GetUserResponse object
            GetUserResponse userResponse = usersMapper.mapToGetUserResponse(entity);
            log.info("Retrieved userResponse: {}", userResponse);

            // Return the response containing the user information
            return userResponse;
        } else {
            // Handle username/password authentication
            if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
                int authenticatedUserId = getAuthenticatedUserId();

                // Retrieve the user information based on the provided user ID
                UsersEntity entity = getUserById(userId);

                // Handle the case where the user with the given ID is not found
                if (entity == null) {
                    throw new NotFoundException("User with ID " + userId + " not found");
                }

                // Check if the authenticated user is trying to access their own information
                if (entity.getUserId() != authenticatedUserId) {
                    throw new NotFoundException("You do not have access to retrieve this user's information");
                }

                // Map the retrieved UsersEntity to a GetUserResponse object
                GetUserResponse userResponse = usersMapper.mapToGetUserResponse(entity);
                log.info("Retrieved userResponse: {}", userResponse);

                // Return the response containing the user information for the provided user ID
                return userResponse;
            } else {
                throw new RuntimeException("Unexpected principal type");
            }
        }
    }


    private boolean isGoogleAuthentication(Authentication authentication) {
        return authentication != null && authentication.getPrincipal() instanceof OidcUser;
    }
    /**
     * Gets users responses.
     *
     * @return the users responses
     */

    public GetUsersResponses getUsersResponses(Authentication authentication) {
        log.info("inside GetUsersResponses the authenication email = {}",authentication.getPrincipal());
        // Retrieve the list of user entities from the repository
        log.info("google email = {}", getAuthIdentifier(authentication));

        List<UsersEntity> usersEntityList = usersRepository.findAll();
        // Map the retrieved user entities to a GetUsersResponses object
        GetUsersResponses usersResponses = usersMapper.mapToGetUsersResponses(usersEntityList);

        // Return the response containing information for all users in the system
        return usersResponses;
    }

    /**
     * Delete user request.
     *
     * @param userId the user id
     */
    public void deleteUserRequest(int userId) {
        int authenticatedUserId = getAuthenticatedUserId();

        // Validate the access of the authenticated user to perform the deletion operation
        validateAccess(authenticatedUserId, userId);

        // Check if the user exists before deletion
        if (userExists(userId)) {
            // Delete the user from the repository
            usersRepository.deleteById(userId);
        } else {
            // Handle the case where the user is not found for deletion
            throw new NotFoundException("User with ID: " + userId + " is not found");
        }
    }
    private void validateAccess(int authenticatedUserId, int userId) {
        if (userId != authenticatedUserId) {
            throw new AccessDeniedException("User does not have access to this operation");
        }
    }
    private boolean hasAdminRole(Collection<?> roles) {
        return roles.stream()
                .anyMatch(role -> "ROLE_ADMIN".equals(role.toString()));
    }
    private boolean userExists(int id) {
        return usersRepository.checkForExists(id);
    }

    /**
     * Retrieves the ID of the authenticated user from the security context.
     *
     * @return The ID of the authenticated user.
     */
    private int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal).getUserId();
            } else if (principal instanceof DefaultOidcUser) {
                DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
                String email = oidcUser.getAttribute("email");
                log.info("inside getAuth email = {}",email);

                // Use the UserService method to get the userId
                UsersEntity usersEntity = usersRepository.findByEmail(email);
                return usersEntity.getUserId();
            }
            // Handle other principal types if needed
        }
        // Handle unauthenticated users
        throw new IllegalStateException("User not authenticated");
    }



    private UsersEntity getUserById(int userId) {
        UsersEntity entity = usersRepository.findById(userId);
        // If the user entity is not found, throw a NotFoundException
        if (entity == null) {
            throw new NotFoundException("User with ID: " + userId + " is not found");
        }
        return entity;
    }

    /**
     * Builds an updated user request based on the provided update request and the original user's information.
     */
    private UpdateUserRequest buildUpdatedUser(UsersEntity originalUser, UpdateUserRequest updateUserRequest) {
        UpdateUserRequest updatedUser = new UpdateUserRequest();
        // Set the username to the updated value if provided, otherwise use the original value
        updatedUser.setUsername(updateUserRequest.getUsername() != null ? updateUserRequest.getUsername() : originalUser.getUsername());
        // Set the password to the updated value if provided, otherwise use the original value
        updatedUser.setPassword(updateUserRequest.getPassword() != null ? updateUserRequest.getPassword() : originalUser.getPassword());
        // Set the email to the updated value if provided, otherwise use the original value
        updatedUser.setEmail(updateUserRequest.getEmail() != null ? updateUserRequest.getEmail() : originalUser.getEmail());
        return updatedUser;
    }
    public static String getAuthIdentifier(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OidcUser) {
            // Handle OIDC user
            return ((OidcUser) principal).getEmail();
        } else if (principal instanceof CustomUserDetails) {
            // Handle your custom user
            return ((CustomUserDetails) principal).getUsername();  // or another identifier
        } else {
            // Handle other cases or return null, depending on your requirements
            return null;
        }
    }

}