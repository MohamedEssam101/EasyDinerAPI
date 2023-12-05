//package easydiner.API.config.security;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//@Component
//public class CustomInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // Check if the user is authenticated
//        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
//            String userEmail = ((CustomUserDetails) authentication.getPrincipal()).getEmail();
//            // Store the userEmail in a place where other parts of your application can access it
//            request.setAttribute("userEmail", userEmail);
//        }
//
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        // Post-handle logic if needed
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        // After completion logic if needed
//    }
//
//}
