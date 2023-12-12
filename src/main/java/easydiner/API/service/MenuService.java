package easydiner.API.service;

import easydiner.API.config.security.CustomUserDetails;
import easydiner.API.exception.NotFoundException;
import easydiner.API.mapper.MenuMapper;
import easydiner.API.model.MenuEntity;
import easydiner.API.model.UsersEntity;
import easydiner.API.repository.MenuRepository;
import easydiner.API.repository.UsersRepository;
import easydiner.API.repository.UsersRestaurantRepository;
import easydiner.API.requests.AddMenuRequest;
import easydiner.API.requests.UpdateMenuRequest;
import easydiner.API.responses.AddMenuResponse;
import easydiner.API.responses.GetMenuResponse;
import easydiner.API.responses.GetMenusResponse;
import easydiner.API.responses.UpdateMenuResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class MenuService {


    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final UsersRestaurantRepository usersRestaurantRepository;
    private final UsersRepository usersRepository;



    public AddMenuResponse addMenuResponse(AddMenuRequest menuRequest, byte[] imageData)
    {
        int authenticatedUser = getAuthenticatedUserId();
        List<Integer> restaurantIds = usersRestaurantRepository.getRestaurantIdsForUser(authenticatedUser);
        log.info("restaurantIds in addMenuResponse is {}",restaurantIds);

        if(!restaurantIds.contains(menuRequest.getRestaurantId())){
            throw new AccessDeniedException("User does not have access to the specified restaurant.");
        }
        MenuEntity menuEntity = menuMapper.mapToMenuEntitywithImage(menuRequest,imageData);
        menuRepository.save(menuEntity);
        AddMenuResponse menuResponse = menuMapper.mapToAddMenuResponse(menuEntity);
        return menuResponse;
    }

    public UpdateMenuResponse updateMenuResponse(UpdateMenuRequest menuRequest , int id )
    {
        //authentication user Id
        int authenticatedUserId = getAuthenticatedUserId();
        log.info("the user logged in id = {}",authenticatedUserId);
        // list of the restaurant ids that the user can access
        List<Integer> userIDByRestaurantID = usersRestaurantRepository.getUserIDByRestaurantID(menuRequest.getRestaurantId());
        log.info("the users that have access to restaurantIds is {}",userIDByRestaurantID);
        if (!userIDByRestaurantID.contains(authenticatedUserId)) {
            throw new NotFoundException("Authenticated user does not have access to update this menu");
        }
        MenuEntity menuEntity = menuMapper.mapToMenuEntity(menuRequest,id);
        menuRepository.save(menuEntity);
        UpdateMenuResponse menuResponse = menuMapper.mapToUpdateMenuResponse(menuEntity);
        return menuResponse;
    }
    public GetMenuResponse getMenuResponse(int menuId) {
        MenuEntity menuEntity = getMenu(menuId);
        int authenticatedUserId = getAuthenticatedUserId();
        log.info("authenticatedUserId = {}",authenticatedUserId);
        ensureUserHasAccess(menuEntity, authenticatedUserId);
        return mapMenuToResponse(menuEntity);
    }

    public GetMenusResponse getMenusResponse()
    {
//        int authenticationId = getAuthenticatedUserId();
//        log.info("authenticationId = {}",authenticationId);
//        List<Integer> userRestaurantEntities = userRestaurantRepository.getRestaurantIdsForUser(authenticationId);
//        if (userRestaurantEntities.isEmpty()) {
//            throw new UserHasNoRestaurantsException();
//        }
//        log.info("userRestaurantEntities = {}",userRestaurantEntities);
        List<MenuEntity> menuEntityList = menuRepository.findAll();
        GetMenusResponse menusResponse = menuMapper.mapToGetMenusResponse(menuEntityList);
        return menusResponse;
    }

    public void deleteMenuItem(int id) {
        int authenticatedUserId = getAuthenticatedUserId();
        MenuEntity menuEntity = menuRepository.findByMenuId(id);
        ensureUserHasAccess(menuEntity,authenticatedUserId);
        if (menuItemExists(id)) {
            menuRepository.deleteByMenuId(id);
        } else {
            throw new NotFoundException("Menu item with id: " + id + " is not found");
        }
    }
    private MenuEntity getMenu(int menuId) {
        MenuEntity menuEntity = menuRepository.findByMenuId(menuId);
        if (menuEntity == null) {
            throw new NotFoundException("Menu with id: " + menuId + " is not found");
        }
        return menuEntity;
    }

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


    private void ensureUserHasAccess(MenuEntity menuEntity, int authenticatedUserId) {
        List<Integer> userIDByRestaurantID = usersRestaurantRepository.getUserIDByRestaurantID(menuEntity.getRestaurantId().getRestaurantId());
        if (!userIDByRestaurantID.contains(authenticatedUserId)) {
            throw new NotFoundException("Authenticated user does not have access to this menu");
        }
    }

    private GetMenuResponse mapMenuToResponse(MenuEntity menuEntity) {
        return menuMapper.mapToGetMenuResponse(menuEntity);
    }

    private boolean menuItemExists(int id) {
        return menuRepository.countMenusById(id) == 1;
    }





}
