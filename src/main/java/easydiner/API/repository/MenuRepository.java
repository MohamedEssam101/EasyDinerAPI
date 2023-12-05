package easydiner.API.repository;

import easydiner.API.model.MenuEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

    MenuEntity save(MenuEntity menu);

    // Update a menu item
    // Retrieve a menu item by menu_id
    MenuEntity  findByMenuId(int menuId);

    // Retrieve all menu items
    List<MenuEntity> findAll();

    // Delete a menu item
    @Transactional
    void deleteByMenuId(int menuId);


    // Check if a menu item exists by menu_id
    @Query("SELECT COUNT(*) FROM MenuEntity WHERE menuId = :id")
    int countMenusById(int id);

    // Retrieve a menu item by name
   // MenuEntity getMenuByName(String name);

    // Retrieve menu items for given restaurantIds
   // List<MenuEntity> getMenuItemsForRestaurants(List<Integer> restaurantIds);
}

