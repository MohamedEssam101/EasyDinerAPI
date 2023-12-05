package easydiner.API.repository;

import easydiner.API.model.UsersRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface User restaurant repository.
 */@Repository

public interface UsersRestaurantRepository extends JpaRepository<UsersRestaurantEntity, Integer> {

    // Custom query for getting restaurant IDs for a user
    @Query("SELECT ur.restaurant.id FROM UsersRestaurantEntity ur WHERE ur.user.id = :userId")
    List<Integer> getRestaurantIdsForUser(@Param("userId") int userId);

    @Query("SELECT ur.user.id FROM UsersRestaurantEntity ur WHERE ur.restaurant.id = :restaurantId")
    List<Integer> getUserIDByRestaurantID(@Param("restaurantId") int restaurantId);
}
