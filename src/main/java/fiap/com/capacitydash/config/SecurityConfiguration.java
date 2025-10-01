package fiap.com.capacitydash.config;

import fiap.com.capacitydash.auth.CustomAuthenticationSuccessHandler;
import fiap.com.capacitydash.auth.RoleUpdateFilter;
import fiap.com.capacitydash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final RoleUpdateFilter roleUpdateFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // 👇 APENAS ADMIN - Criar e Deletar
                        .requestMatchers("/moto/form/**", "/dashboard/moto/delete/**", "/movement/form").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("dashboard/moto/GHI9012").permitAll()
                        // 👇 ADMIN E USER - Visualizar
                        .requestMatchers("/dashboard", "/dashboard/**", "/moto/**", "/movement/register").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")

                        .anyRequest().authenticated()
                )
                .oauth2Login(login -> login
                        .successHandler(customAuthenticationSuccessHandler)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .addFilterAfter(roleUpdateFilter, OAuth2LoginAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .build();
    }
}