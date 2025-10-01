package fiap.com.capacitydash.service;

import fiap.com.capacitydash.model.ParkingSpace;
import fiap.com.capacitydash.repository.ParkingSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    public List<ParkingSpace> findAll() {
        return parkingSpaceRepository.findAll();
    }

    public ParkingSpace findById(Long idParkingSpace) {
        return parkingSpaceRepository.findById(idParkingSpace)
                .orElseThrow(() -> new RuntimeException("ID não encontrado"));
    }

    public ParkingSpace update(Long id,ParkingSpace parkingSpace) {
        ParkingSpace parkingSpaceAntigo = findById(id);
        ParkingSpace parkingSpaceAtualizado = ParkingSpace.builder()
                .parkingSpaceId(parkingSpaceAntigo.getParkingSpaceId())
                .department(parkingSpace.getDepartment() != null ? parkingSpace.getDepartment() : parkingSpaceAntigo.getDepartment())
                .motorcycle(parkingSpace.getMotorcycle() != null ? parkingSpace.getMotorcycle() : parkingSpaceAntigo.getMotorcycle())
                .occupied(parkingSpace.getOccupied() != null ? parkingSpace.getOccupied() : parkingSpaceAntigo.getOccupied())
                .code(parkingSpace.getCode() != null ? parkingSpace.getCode() : parkingSpaceAntigo.getCode())
                .build();
        return parkingSpaceRepository.save(parkingSpaceAtualizado);
    }

    public ParkingSpace findByCode(String code) {
        return parkingSpaceRepository.findByCode(code);
    }


}
