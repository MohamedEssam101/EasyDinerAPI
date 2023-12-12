package easydiner.API.config.security;

import easydiner.API.model.UsersEntity;
import easydiner.API.repository.UsersRepository;
import easydiner.API.repository.UsersRestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import easydiner.API.Enum.Role;
import java.util.Collections;

/**
 * The type Custom user details service.
 */
@Service
@Log4j2
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final UsersRestaurantRepository userRestaurantRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load the user entity from the database
        UsersEntity user = usersRepository.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // Create a UserDetails object with the user's ID, username, password, and roles
        Role userRole = user.getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userRole.name());

        UserDetails userDetails = new CustomUserDetails(
                user.getUserId(), // Set the user's ID
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
        return userDetails;
    }

}