package fiap.com.capacitydash.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "dashuser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "dashuser_roles",  // ← ESPECIFIQUE O NOME DA TABELA
            joinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Role> roles = new HashSet<>();

    private String avatarurl;

    public User(OAuth2User principal) {
        this.name = Optional.ofNullable(principal.getAttribute("name"))
                .map(Object::toString)
                .orElse("Usuário Sem Nome");
        this.email = Optional.ofNullable(principal.getAttribute("email"))
                .map(Object::toString)
                .orElseThrow(() -> new IllegalStateException("O Google não forneceu um email."));
        this.avatarurl = Optional.ofNullable(principal.getAttribute("picture"))
                .map(Object::toString)
                .orElse(null);
        this.roles = new HashSet<>(Set.of(Role.ROLE_USER));
    }
}