package fiap.com.capacitydash.service;

import fiap.com.capacitydash.model.Role;
import fiap.com.capacitydash.model.User;
import fiap.com.capacitydash.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String ADMIN_EMAIL = "felipe08santanasantos@gmail.com";
    private final UserRepository userRepository;

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User register(OAuth2User principal) {
        String email = principal.getAttribute("email").toString();
        var userOptional = userRepository.findByEmail(email);

        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            System.out.println("🔄 Usuário existente: " + email);
            System.out.println("🔑 Roles atuais: " + user.getRoles());

            // ATUALIZE: Se for admin, garanta que tem as roles corretas
            if (ADMIN_EMAIL.equals(email) && !user.getRoles().contains(Role.ROLE_ADMIN)) {
                user.getRoles().add(Role.ROLE_ADMIN);
                System.out.println("⭐ ADMIN role adicionada ao usuário existente");
            }
        } else {
            user = new User(principal);
            System.out.println("🆕 Novo usuário: " + email);

            if (ADMIN_EMAIL.equals(email)) {
                user.setRoles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));
                System.out.println("⭐ Roles de ADMIN atribuídas");
            } else {
                user.setRoles(Set.of(Role.ROLE_USER));
                System.out.println("👤 Role de USER atribuída");
            }
        }

        User savedUser = userRepository.save(user);
        System.out.println("💾 Usuário salvo com roles: " + savedUser.getRoles());
        return savedUser;
    }
}