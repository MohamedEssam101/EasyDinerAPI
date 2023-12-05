package easydiner.API.config.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * The type Custom user details.
 */
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private int userId;
    private String email;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // Return the actual authorities/roles from your user entity
    }

    @Override
    public String getPassword() {
        return password; // Return the actual password from your user entity
    }

    @Override
    public String getUsername() {
        return username; // Return the actual username from your user entity
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Adjust based on your business logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Adjust based on your business logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Adjust based on your business logic
    }

    @Override
    public boolean isEnabled() {
        return true; // Adjust based on your business logic
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
