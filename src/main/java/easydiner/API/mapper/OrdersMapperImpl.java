package easydiner.API.mapper;

import easydiner.API.Enum.Status;
import easydiner.API.config.security.CustomUserDetails;
import easydiner.API.model.OrdersEntity;
import easydiner.API.model.RestaurantsEntity;
import easydiner.API.model.UsersEntity;
import easydiner.API.repository.OrdersRepository;
import easydiner.API.repository.RestaurantsRepository;
import easydiner.API.repository.UsersRepository;
import easydiner.API.requests.AddOrderRequest;
import easydiner.API.requests.UpdateOrderRequest;
import easydiner.API.responses.AddOrderResponse;
import easydiner.API.responses.GetOrderResponse;
import easydiner.API.responses.GetOrdersResponse;
import easydiner.API.responses.UpdateOrderResponse;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Log4j2
public class OrdersMapperImpl implements OrdersMapper{

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final RestaurantsRepository restaurantsRepository;
    @Override
    public OrdersEntity mapToOrdersEntity(AddOrderRequest orderRequest) {
        int authenticatedUserId  = getAuthenticatedUserId();
        UsersEntity authenticatedUser = usersRepository.findById(authenticatedUserId);
        RestaurantsEntity restaurant = restaurantsRepository.findById(orderRequest.getRestaurantId());

        return new OrdersEntity(authenticatedUser, restaurant);
    }

    @Override
    public AddOrderResponse mapToAddOrderResponse(OrdersEntity orders) {
        // manually populating status to send it back
        Status status = Status.pending;
        log.info("orders status = {}",orders.getStatus());
        return new AddOrderResponse(
                status,
                ordersRepository.getRestaurantNameByOrderId(orders.getOrderId()));
    }

    @Override
    public GetOrderResponse maptoGetOrderResponse(OrdersEntity orders, String restaurantName) {
        return new GetOrderResponse(
                orders.getStatus(),
                restaurantName // If this is the correct place to include the restaurant name
        );
    }

    @Override
    public UpdateOrderResponse mapUpdateOrderRequestToUserEntity(OrdersEntity entity) {
        return new UpdateOrderResponse(entity.getStatus());
    }

    @Override
    public OrdersEntity mapToOrdersEntity(UpdateOrderRequest orderRequest) {
        return new OrdersEntity(orderRequest.getStatus());
    }

    @Override
    public GetOrdersResponse mapToGetOrdersResponse(List<OrdersEntity> ordersEntities) {
        List<GetOrderResponse> orderResponseList = ordersEntities.stream()
                .map(orders -> new GetOrderResponse(
                        orders.getStatus(),
                        ordersRepository.getRestaurantNameByOrderId(orders.getOrderId()))).collect(Collectors.toList());

        return new GetOrdersResponse(orderResponseList);
    }
    
    private int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }

}
