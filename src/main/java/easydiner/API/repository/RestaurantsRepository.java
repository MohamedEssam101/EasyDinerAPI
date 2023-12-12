package easydiner.API.repository;

import easydiner.API.model.OrdersEntity;
import easydiner.API.model.RestaurantsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Restaurants repository.
 */@Repository

public interface RestaurantsRepository extends JpaRepository<RestaurantsEntity, Integer> {

    RestaurantsEntity save(RestaurantsEntity restaurants);

    OrdersEntity findByRestaurantId(int orderId);
    @Modifying
    @Query("UPDATE RestaurantsEntity r SET r.name = :#{#restaurantRequest.name}, r.description = :#{#restaurantRequest.description}, r.location = :#{#restaurantRequest.location} WHERE r.restaurantId = :restaurantId")
    void updateRestaurant(@Param("restaurantRequest") RestaurantsEntity restaurantRequest, @Param("restaurantId") int restaurantId);

    // Custom query for getting a restaurant by ID
    RestaurantsEntity findById(int id);

    // Custom query for getting a list of all restaurants
    List<RestaurantsEntity> findAll();

    // Custom query for deleting a restaurant by ID
    @Transactional
    void deleteByRestaurantId(int menuId);

    // Custom query for checking if a restaurant exists by ID
    @Query("SELECT COUNT(r) > 0 FROM RestaurantsEntity r WHERE r.restaurantId = :id")
    boolean checkForExists(@Param("id") int id);
}
