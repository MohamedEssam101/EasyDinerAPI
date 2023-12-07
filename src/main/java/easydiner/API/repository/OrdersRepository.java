package easydiner.API.repository;

import easydiner.API.Enum.Status;
import easydiner.API.model.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Orders repository.
 */@Repository

public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

    // Custom query for adding an order
    OrdersEntity save(OrdersEntity order);

    // Custom query for getting an order by ID
    OrdersEntity findByOrderId(int orderId);
    @Modifying
    @Query("UPDATE OrdersEntity o SET o.status = :status WHERE o.orderId = :orderId")
    void updateOrderStatus(@Param("status") Status status, @Param("orderId") int orderId);

    // Custom query for getting the restaurant name by order ID
    @Query("SELECT r.name FROM RestaurantsEntity r WHERE r.restaurantId = (SELECT o.restaurantId.restaurantId FROM OrdersEntity o WHERE o.orderId = :orderId)")
    String getRestaurantNameByOrderId(@Param("orderId") int orderId);


    // Custom query for getting a list of orders
    List<OrdersEntity> findAll();
    @Query("SELECT COUNT(u) > 0 FROM OrdersEntity u WHERE u.orderId = :id")
    boolean checkForExists(@Param("id") int id);
    void deleteByOrderId(int menuId);
    // Custom query for getting order details
//    @Query("SELECT new easydiner.api.orders.response.GetOrderDTO(o.status, r.name AS restaurantName) FROM OrdersEntity o JOIN RestaurantsEntity r ON o.restaurant_id = r.restaurant_id")
//    List<GetOrderDTO> getOrderDetails();
//
//    // Retrieve order details for a specific user
//    @Query("SELECT new easydiner.api.orders.response.GetOrderDTO(o.status, r.name AS restaurantName) FROM OrdersEntity o JOIN RestaurantsEntity r ON o.restaurant_id = r.restaurant_id WHERE o.user_id = :userId")
//    List<GetOrderDTO> getOrderDetailsForUser(@Param("userId") UsersEntity userId);
}
