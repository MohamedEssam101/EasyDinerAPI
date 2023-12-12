package easydiner.API.service;
import easydiner.API.config.security.CustomUserDetails;
import easydiner.API.exception.NotFoundException;
import easydiner.API.exception.UnauthorizedException;
import easydiner.API.mapper.OrdersMapper;
import easydiner.API.model.OrdersEntity;
import easydiner.API.model.UsersEntity;
import easydiner.API.repository.OrdersRepository;
import easydiner.API.repository.UsersRepository;
import easydiner.API.requests.AddOrderRequest;
import easydiner.API.requests.UpdateOrderRequest;
import easydiner.API.responses.AddOrderResponse;
import easydiner.API.responses.GetOrderResponse;
import easydiner.API.responses.GetOrdersResponse;
import easydiner.API.responses.UpdateOrderResponse;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;

    private final UsersRepository usersRepository;

    public AddOrderResponse addOrder(AddOrderRequest orderRequest) {
        OrdersEntity orders = ordersMapper.mapToOrdersEntity(orderRequest);
        ordersRepository.save(orders);
        int orderId = orders.getOrderId();
        AddOrderResponse orderResponse = ordersMapper.mapToAddOrderResponse(orders);
        return orderResponse;
    }

    public GetOrderResponse getOrderResponse(int orderId) {
        OrdersEntity orders = ordersRepository.findByOrderId(orderId);
        GetOrderResponse orderResponse =  ordersMapper.maptoGetOrderResponse(orders, getRestaurantName(orderId));
        return orderResponse;
    }

    public UpdateOrderResponse updateOrderResponse(UpdateOrderRequest updateOrderRequest, int orderId) {

        OrdersEntity orders =  ordersMapper.mapToOrdersEntity(updateOrderRequest);

        ordersRepository.updateOrderStatus(orders.getStatus(), orderId);
        UpdateOrderResponse orderResponse = ordersMapper.mapUpdateOrderRequestToUserEntity(orders);
        return orderResponse;
    }

    public GetOrdersResponse getOrdersResponse(){
        List<OrdersEntity> orderDetailsFromDB = ordersRepository.findAll();

        GetOrdersResponse ordersResponse = ordersMapper.mapToGetOrdersResponse(orderDetailsFromDB);
        return ordersResponse;
    }
    public void deleteOrder(int orderId) {
        validateAdminRole();
        validateAndDeleteOrder(orderId);
    }
    private void validateAdminRole() {
        Collection<?> roles = getAuthenticatedRole();
        if (!hasAdminRole(roles)) {
            throw new UnauthorizedException();
        }
    }

    private void validateAndDeleteOrder(int orderId) {
        if (!checkIfOrderExists(orderId)) {
            throw new NotFoundException("Order with ID: " + orderId + " is not found");
        }
        ordersRepository.deleteByOrderId(orderId);
    }
    private boolean hasAdminRole(Collection<?> roles) {
        return roles.stream()
                .anyMatch(role -> "ROLE_ADMIN".equals(role.toString()));
    }
    private Collection getAuthenticatedRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) authentication.getPrincipal()).getAuthorities();
            } else if (principal instanceof DefaultOidcUser) {
                DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
                String email = oidcUser.getAttribute("email");
                log.info("inside getAuth email = {}",email);

                // Use the UserService method to get the userId
                UsersEntity usersEntity = usersRepository.findByEmail(email);
                return Collections.singleton(usersEntity.getRole());
            }
            // Handle other principal types if needed
        }
        // Handle unauthenticated users
        throw new IllegalStateException("User not authenticated");
    }

    private boolean checkIfOrderExists(int id){
        return ordersRepository.checkForExists(id);
    }
    private String getRestaurantName(int orderId) {
        return ordersRepository.getRestaurantNameByOrderId(orderId);
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

}
