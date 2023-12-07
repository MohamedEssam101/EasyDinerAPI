package easydiner.API.config.security;

import easydiner.API.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final UsersRepository usersRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(usersRepository))
                .excludePathPatterns("/restaurants/**"); // Exclude paths configured in Spring Security to permit all
    }
}
