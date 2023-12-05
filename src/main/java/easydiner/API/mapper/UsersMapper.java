package easydiner.API.mapper;

import easydiner.API.model.UsersEntity;
import easydiner.API.requests.AddUserRequest;
import easydiner.API.requests.UpdateUserRequest;
import easydiner.API.responses.AddUserResponse;
import easydiner.API.responses.GetUserResponse;
import easydiner.API.responses.GetUsersResponses;
import easydiner.API.responses.UpdateUserResponse;
import java.util.List;

/**
 * The interface Users mapper.
 */
public interface UsersMapper {


    /**
     * Map user request to users entity users entity.
     *
     * @param userRequest the user request
     * @return the users entity
     */
    UsersEntity mapUserRequestToUsersEntity(AddUserRequest userRequest);

    /**
     * Map to add user response add user response.
     *
     * @param users the users
     * @return the add user response
     */
    AddUserResponse mapToAddUserResponse (UsersEntity users);

    /**
     * Map update request to user entity users entity.
     *
     * @param userRequest the user request
     * @return the users entity
     */
    UsersEntity mapUpdateRequestToUserEntity(UpdateUserRequest userRequest);

    /**
     * Map to users response update user response.
     *
     * @param entity the entity
     * @return the update user response
     */
    UpdateUserResponse mapToUsersResponse (UsersEntity entity);

    /**
     * Map to get user response get user response.
     *
     * @param users the users
     * @return the get user response
     */
    GetUserResponse mapToGetUserResponse(UsersEntity users);

    /**
     * Map to get users responses get users responses.
     *
     * @param usersEntityList the users entity list
     * @return the get users responses
     */
    GetUsersResponses mapToGetUsersResponses(List<UsersEntity> usersEntityList);
}
