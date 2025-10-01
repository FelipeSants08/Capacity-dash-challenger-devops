package fiap.com.capacitydash.auth;

import fiap.com.capacitydash.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserService userService;

    public LoginListener(UserService userService) {
        this.userService = userService;
        System.out.println("✅✅✅ LOGIN LISTENER CRIADO! ✅✅✅");
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        System.out.println("🚀🚀🚀 LOGIN LISTENER EXECUTADO! 🚀🚀🚀");

        var principal = (OAuth2User) event.getAuthentication().getPrincipal();
        System.out.println("📧 Email do usuário: " + principal.getAttribute("email"));

        var user = userService.register(principal);
        System.out.println("🔑 Usuário registrado com roles: " + user.getRoles());
    }
}