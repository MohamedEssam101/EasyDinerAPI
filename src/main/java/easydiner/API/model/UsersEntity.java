package easydiner.API.model;

import jakarta.persistence.*;
import lombok.*;
import easydiner.API.Enum.Role;

/**
 * The type Users entity.
 */
@Entity
@Table(name = "Users") // Specify the correct table name
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;


    @Column(name = "role")
    @Enumerated(EnumType.STRING) // Assuming Role is an Enum
    private Role role;

    public UsersEntity(String username,String email) {
        this.username = username;
        this.email = email;
    }
    public UsersEntity(String username,String email,Role role) {
        this.username = username;
        this.email = email;
        this.role=role;
    }
    public UsersEntity(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public UsersEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
