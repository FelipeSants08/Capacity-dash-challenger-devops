package fiap.com.capacitydash.auth;

import fiap.com.capacitydash.model.User;
import fiap.com.capacitydash.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        System.out.println("🎯 CUSTOM SUCCESS HANDLER EXECUTADO!");

        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        String email = principal.getAttribute("email");
        System.out.println("📧 Email: " + email);

        // Apenas registra o usuário - não mexe no SecurityContext
        User user = userService.register(principal);
        System.out.println("🔑 Roles do usuário: " + user.getRoles());

        // Redireciona para a dashboard
        response.sendRedirect("/dashboard");
    }
}