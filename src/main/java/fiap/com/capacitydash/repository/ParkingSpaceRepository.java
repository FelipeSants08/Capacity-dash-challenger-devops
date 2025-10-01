package fiap.com.capacitydash.repository;

import fiap.com.capacitydash.model.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    ParkingSpace findByCode(String code);
}
