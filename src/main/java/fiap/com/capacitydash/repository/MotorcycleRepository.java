package fiap.com.capacitydash.repository;

import fiap.com.capacitydash.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
    Motorcycle findByPlate(String plate);
    Boolean existsByPlate(String plate);
}
