package fiap.com.capacitydash.repository;

import fiap.com.capacitydash.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}
