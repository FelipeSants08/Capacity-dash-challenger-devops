package fiap.com.capacitydash.service;

import fiap.com.capacitydash.model.Motorcycle;
import fiap.com.capacitydash.model.Movement;
import fiap.com.capacitydash.model.MovementType;
import fiap.com.capacitydash.model.ParkingSpace;
import fiap.com.capacitydash.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovementService {

    private final MovementRepository movementRepository;
    private final MotorcycleService motorcycleService;
    private final ParkingSpaceService parkingSpaceService;
    private final AlertService alertService;


    public List<Movement> findAll() {
        return movementRepository.findAll();
    }

    public Movement create(Movement movement) {
        return null;
    }


    public Movement entrada(String place, String code) {

            Motorcycle moto = motorcycleService.findByPlate(place);
            ParkingSpace parkingSpace = parkingSpaceService.findByCode(code);
            if (moto == null) {
                throw new IllegalArgumentException("A placa da motocicleta não foi encontrada.");
            }

            if (parkingSpace == null) {
                throw new IllegalArgumentException("O código da vaga não foi encontrado.");
            }
            if (parkingSpace.getOccupied()) {
                throw new IllegalArgumentException("A vaga já está ocupada.");
            }
            parkingSpace.setOccupied(true);
            parkingSpace.setMotorcycle(moto);
            moto.setParkingSpace(parkingSpace);

            Movement movement = new Movement();
            movement.setType(MovementType.ENTRADA);
            movement.setParkingSpace(parkingSpace);
            movement.setMotorcycle(moto);
            movement.setDateTimeMovement(LocalDateTime.now());
            parkingSpaceService.update(parkingSpace.getParkingSpaceId(), parkingSpace);
            alertService.gerarAlertaAutomatico(parkingSpace.getDepartment().getId());
            return movementRepository.save(movement);
    }

    public Movement exit(String place) {
        Motorcycle moto = motorcycleService.findByPlate(place);

        if (moto.getParkingSpace() == null) {
            throw new IllegalArgumentException("Esta moto não está em uma vaga.");
        }

        ParkingSpace parkingSpace = moto.getParkingSpace();


        parkingSpace.setMotorcycle(null);
        parkingSpace.setOccupied(false);
        moto.setParkingSpace(null);


        parkingSpaceService.update(parkingSpace.getParkingSpaceId(), parkingSpace);
        motorcycleService.updateMotorcycle(moto.getIdMotorcycle(), moto);


        Movement movement = new Movement();
        movement.setType(MovementType.SAIDA);
        movement.setDateTimeMovement(LocalDateTime.now());
        movement.setParkingSpace(parkingSpace);
        movement.setMotorcycle(moto);

        return movementRepository.save(movement);
    }


}
