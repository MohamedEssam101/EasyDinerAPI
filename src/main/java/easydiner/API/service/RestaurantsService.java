package easydiner.API.service;

import easydiner.API.config.security.CustomUserDetails;
import easydiner.API.exception.NotFoundException;
import easydiner.API.mapper.RestaurantsMapper;
import easydiner.API.model.RestaurantsEntity;
import easydiner.API.repository.RestaurantsRepository;
import easydiner.API.requests.AddRestaurantRequest;
import easydiner.API.requests.UpdateRestaurantRequest;
import easydiner.API.responses.AddRestaurantResponse;
import easydiner.API.responses.GetRestaurantResponse;
import easydiner.API.responses.GetRestaurantsResponse;
import easydiner.API.responses.UpdateRestaurantResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class RestaurantsService {

    private final RestaurantsRepository restaurantsRepository;
    private final RestaurantsMapper restaurantsMapper;


    public AddRestaurantResponse addRestaurantResponse(AddRestaurantRequest restaurantRequest) {

        Collection<?> roles = getAuthenticatedRole();
        log.info("the roles are {}", roles);

        if (!hasAdminRole(roles)) {
            // Handle the case where the user does not have the "ADMIN" role
            throw new AccessDeniedException("cant access this ");
        }
        RestaurantsEntity restaurantEntity = restaurantsMapper.mapToRestaurantsEntity(restaurantRequest);

        restaurantsRepository.save(restaurantEntity);
        AddRestaurantResponse restaurantResponse = restaurantsMapper.mapToAddRestaurantResponse(restaurantEntity);
        return restaurantResponse;
    }

    public UpdateRestaurantResponse updateRestaurantResponse(UpdateRestaurantRequest request, int id) {

        Collection<?> roles = getAuthenticatedRole();
        log.info("the roles are {}", roles);

        if (!hasAdminRole(roles)) {
            // Handle the case where the user does not have the "ADMIN" role
            throw new AccessDeniedException("cant access this ");
        }
        RestaurantsEntity restaurantsEntity = restaurantsMapper.mapToRestaurantsEntity(request);

        restaurantsRepository.updateRestaurant(restaurantsEntity, id);

        UpdateRestaurantResponse response = restaurantsMapper.mapToUpdateRestaurantResponse(restaurantsEntity);
        return response;
    }

    public GetRestaurantResponse getRestaurantResponse(int id) {

        RestaurantsEntity restaurantsEntity = restaurantsRepository.findById(id);
        if(restaurantsEntity == null) {
            throw new NotFoundException("There is no Restaurant with id: "+id);
        }
        GetRestaurantResponse restaurantResponse = restaurantsMapper.mapToGetRestaurantResponse(restaurantsEntity);

        return restaurantResponse;
    }
    public GetRestaurantsResponse getRestaurantsResponse(){

        List<RestaurantsEntity> entityList = restaurantsRepository.findAll();

        GetRestaurantsResponse getRestaurantsResponse = restaurantsMapper.mapToGetRestaurantsResponse(entityList);
        return getRestaurantsResponse;
    }
    public void deleteRestaurant(int id ){
        if (restaurantExists(id)) {
            restaurantsRepository.deleteByRestaurantId(id);
        } else {
            throw new NotFoundException("Restaurant with id: "+id +" is not found");
        }
    }
    private boolean restaurantExists(int id) {
        return restaurantsRepository.checkForExists(id);
    }
    private int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }
    private Collection getAuthenticatedRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((CustomUserDetails) authentication.getPrincipal()).getAuthorities();
    }
    private boolean hasAdminRole(Collection<?> roles) {
        return roles.stream()
                .anyMatch(role -> "ROLE_ADMIN".equals(role.toString()));
    }

}
