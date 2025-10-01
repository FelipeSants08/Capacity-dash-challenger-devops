package fiap.com.capacitydash.repository;

import fiap.com.capacitydash.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Long> {
}
