package easydiner.API.mapper;

import easydiner.API.model.OrdersEntity;
import easydiner.API.requests.AddOrderRequest;
import easydiner.API.requests.UpdateOrderRequest;
import easydiner.API.responses.AddOrderResponse;
import easydiner.API.responses.GetOrderResponse;
import easydiner.API.responses.GetOrdersResponse;
import easydiner.API.responses.UpdateOrderResponse;


import java.util.List;

public interface OrdersMapper {

    OrdersEntity mapToOrdersEntity(AddOrderRequest orderRequest);

    AddOrderResponse mapToAddOrderResponse(OrdersEntity orders);

    GetOrderResponse maptoGetOrderResponse(OrdersEntity orders, String restaurantName);
    UpdateOrderResponse mapUpdateOrderRequestToUserEntity(OrdersEntity entity);

    OrdersEntity mapToOrdersEntity(UpdateOrderRequest orderRequest);

    GetOrdersResponse mapToGetOrdersResponse(List<OrdersEntity> ordersEntities);

}
