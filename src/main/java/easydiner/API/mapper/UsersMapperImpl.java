package easydiner.API.mapper;

import easydiner.API.model.UsersEntity;
import easydiner.API.requests.AddUserRequest;
import easydiner.API.requests.UpdateUserRequest;
import easydiner.API.responses.AddUserResponse;
import easydiner.API.responses.GetUserResponse;
import easydiner.API.responses.GetUsersResponses;
import easydiner.API.responses.UpdateUserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Users mapper.
 */
@Component
@AllArgsConstructor
@Log4j2
public class UsersMapperImpl implements UsersMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsersEntity mapUserRequestToUsersEntity(AddUserRequest userRequest) {
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        return new UsersEntity(
                userRequest.getUsername(),
                encodedPassword,
                userRequest.getEmail()
        );
    }

    @Override
    public AddUserResponse mapToAddUserResponse(UsersEntity users) {
        return new AddUserResponse(
                users.getUsername(),
                users.getPassword(),
                users.getEmail()
        );
    }

    @Override
    public UsersEntity mapUpdateRequestToUserEntity(UpdateUserRequest userRequest) {
        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
        return new UsersEntity(
                userRequest.getUsername(),
                hashedPassword,
                userRequest.getEmail());
    }

    @Override
    public UpdateUserResponse mapToUsersResponse(UsersEntity entity) {
        return new UpdateUserResponse(entity.getUsername(),
                entity.getPassword(),
                entity.getEmail());
    }

    @Override
    public GetUserResponse mapToGetUserResponse(UsersEntity users) {
        return new GetUserResponse(
                users.getUsername(),
                users.getPassword(),
                users.getEmail(),
                users.getRole());
    }

    @Override
    public GetUsersResponses mapToGetUsersResponses(List<UsersEntity> usersEntityList) {
        List<GetUserResponse> userResponses = usersEntityList.stream()
                .map(users -> new GetUserResponse(
                        users.getUsername(),
                        users.getPassword(),
                        users.getEmail(),
                        users.getRole())).collect(Collectors.toList());

        return new GetUsersResponses(userResponses);
    }
}
