package easydiner.API.repository;

import easydiner.API.model.UsersEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Users repository.
 */@Repository

public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

    @Query("INSERT INTO UsersEntity (username, password, email) VALUES (:#{#user.username}, :#{#user.password}, :#{#user.email})")
    UsersEntity insertUser(@Param("user") UsersEntity user);

    // Custom query for updating a user
    @Query("UPDATE UsersEntity u SET u.username = :#{#updateUser.username}, u.password = :#{#updateUser.password}, " +
            "u.email = :#{#updateUser.email} WHERE u.userId = :userId")
    void updateUser(@Param("updateUser") UsersEntity updateUser, @Param("userId") int userId);

    // Custom query for getting a user by email
    UsersEntity findByEmail(String email);

    // Custom query for getting a user by id
    UsersEntity findById(int userId);

    // Custom query for getting all users
    List<UsersEntity> findAll();
    UsersEntity findIdByEmail(String email);
    // Custom query for deleting a user by id
    @Transactional

    void deleteById(int userId);

    // Custom query for checking if a user exists
    boolean existsById(int userId);

    boolean existsByEmail(String email);
    // Custom query for counting users by username
    long countByUsername(String username);

    // Custom query for getting user attributes by name
    @Query("SELECT u FROM UsersEntity u WHERE u.username = :username")
    UsersEntity getUserByName(@Param("username") String username);

    // Custom query for checking if a user exists by id
    @Query("SELECT COUNT(u) > 0 FROM UsersEntity u WHERE u.userId = :id")
    boolean checkForExists(@Param("id") int id);

    // Custom query for counting users by email
    long countUserByEmail(String email);
}

