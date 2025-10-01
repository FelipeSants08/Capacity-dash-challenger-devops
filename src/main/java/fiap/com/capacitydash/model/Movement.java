package fiap.com.capacitydash.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovement;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MovementType type;

    @NotNull
    @ManyToOne
    private Motorcycle motorcycle;

    @NotNull
    @ManyToOne
    private ParkingSpace parkingSpace;

    private LocalDateTime dateTimeMovement;

}
