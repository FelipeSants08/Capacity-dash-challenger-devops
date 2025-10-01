package fiap.com.capacitydash.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMotorcycle;

    @NotBlank(message = "A placa é obrigatória.")
    @Size(min = 7, max = 7, message = "A placa deve ter exatamente 7 caracteres.")
    @JoinColumn(unique = true)
    private String plate;

    @NotNull
    private String model;

    private String color;

    @OneToOne(optional = true)
    @JoinColumn(name = "parking_space_id", unique = true)
    private ParkingSpace parkingSpace;
}
