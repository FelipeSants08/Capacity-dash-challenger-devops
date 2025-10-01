package fiap.com.capacitydash.auth;

import fiap.com.capacitydash.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleUpdateFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User principal = oauthToken.getPrincipal();
            String email = principal.getAttribute("email");

            if (email != null) {
                try {
                    // Busca o usuário do banco para pegar as roles atualizadas
                    var userOptional = userService.getUserByEmail(email);
                    if (userOptional.isPresent()) {
                        var user = userOptional.get();
                        var authorities = user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.name()))
                                .collect(Collectors.toList());

                        System.out.println("🔄 Filter atualizando authorities para: " + authorities);

                        // Cria um novo OAuth2User com as authorities corretas
                        OAuth2User updatedUser = new DefaultOAuth2User(
                                authorities,
                                principal.getAttributes(),
                                "email"
                        );

                        // Cria uma nova autenticação com as authorities atualizadas
                        OAuth2AuthenticationToken newAuthentication = new OAuth2AuthenticationToken(
                                updatedUser,
                                authorities,
                                oauthToken.getAuthorizedClientRegistrationId()
                        );

                        // Atualiza o SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Erro no filter: " + e.getMessage());
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}