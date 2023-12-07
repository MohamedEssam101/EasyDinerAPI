package easydiner.API.service;
import easydiner.API.config.security.CustomUserDetails;
import easydiner.API.exception.NotFoundException;
import easydiner.API.exception.UnauthorizedException;
import easydiner.API.mapper.OrdersMapper;
import easydiner.API.model.OrdersEntity;
import easydiner.API.repository.OrdersRepository;
import easydiner.API.requests.AddOrderRequest;
import easydiner.API.requests.UpdateOrderRequest;
import easydiner.API.responses.AddOrderResponse;
import easydiner.API.responses.GetOrderResponse;
import easydiner.API.responses.GetOrdersResponse;
import easydiner.API.responses.UpdateOrderResponse;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;



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
        return ((CustomUserDetails) authentication.getPrincipal()).getAuthorities();
    }
    private boolean checkIfOrderExists(int id){
        return ordersRepository.checkForExists(id);
    }
    private String getRestaurantName(int orderId) {
        return ordersRepository.getRestaurantNameByOrderId(orderId);
    }

    private int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }
}
