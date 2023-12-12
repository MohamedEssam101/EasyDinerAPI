package easydiner.API.config.security;

import easydiner.API.Enum.Role;
import easydiner.API.model.UsersEntity;
import easydiner.API.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URL;

@Component
@Log4j2
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final UsersRepository usersRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Check if the request is coming from Google authentication
        log.warn("inside the preHandle Method");
        if (isGoogleAuthentication(request)) {

            String userEmail = extractGoogleUserEmail(request);
            log.info("Extracted email = {}", userEmail);
            if (userExistsInDatabase(userEmail)) {
                UsersEntity usersEntity = getUserFromDatabase(userEmail);
                log.info("User exists in the database: {}", usersEntity);
            } else {
                String randomUsername = generateRandomUsername();
                UsersEntity usersEntity = new UsersEntity(randomUsername, userEmail, Role.USER);
                log.info("User doesn't exist. Created a new user: {}", usersEntity);

                // Save the new user to the database
                usersRepository.save(usersEntity);
            }
        }
        // Continue with the request handling
        return true;
    }

    private boolean isGoogleAuthentication(HttpServletRequest request) {
        Authentication authentication = (Authentication) request.getUserPrincipal();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

            Object issuerObject = oAuth2User.getAttributes().get("iss");

            if (issuerObject instanceof String) {
                String issuer = (String) issuerObject;
                log.warn("the issuer is equal to '{}'", issuer);

                boolean isGoogle = issuer != null && issuer.contains("accounts.google.com");
                log.warn("Google authentication condition is true: {}. Expected: {}", isGoogle, true);
                log.info("the returning type = ? {}", isGoogle);

                return isGoogle;
            } else if (issuerObject instanceof URL) {
                URL issuerUrl = (URL) issuerObject;
                String issuer = issuerUrl.getHost();
                log.warn("the issuer is equal to '{}'", issuer);

                boolean isGoogle = issuer != null && issuer.contains("accounts.google.com");
                log.warn("Google authentication condition is true: {}. Expected: {}", isGoogle, true);
                log.info("the returning type = ? {}", isGoogle);

                return isGoogle;
            }
        }

        log.warn("Google authentication condition is false");
        return false;
    }


    private String extractGoogleUserEmail(HttpServletRequest request) {
        Authentication authentication = (Authentication) request.getUserPrincipal();

        // Check if the authentication is OAuth2-based
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();

            String email = (String) oauth2User.getAttribute("email");
            log.warn("inside extractGoogleUserEmail method email = {}",email);
            return email;
        }
        // Return null if the email couldn't be extracted
        return null;
    }

    private boolean userExistsInDatabase(String userEmail) {
        log.warn("trying to use userExistsInDatabase, for email {}",userEmail);
        return usersRepository.existsByEmail(userEmail);
    }
    private UsersEntity getUserFromDatabase(String userEmail) {
        log.info("Retrieving user from the database for email: {}", userEmail);
        UsersEntity userEntity = usersRepository.findByEmail(userEmail);
        return userEntity;
    }

    private String generateRandomUsername() {
        // Add logic to generate a random username
        // Example: You can use UUID or any other method to generate a unique username
        return "user" + java.util.UUID.randomUUID().toString().substring(0, 8);
    }
}
