package co.edu.uniquindio.inventory.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Document("Exits")
@NoArgsConstructor
@Data
public class Exits {
    @Id
    private String id = UUID.randomUUID().toString();
    @NotNull
    private String codigo;
    private int salidas;
    private LocalDate fecha_salida;

    @Builder
    public Exits(String id, String codigo, int salidas, LocalDate fecha_salida) {
        this.id = id;
        this.codigo = codigo;
        this.salidas = salidas;
        this.fecha_salida = fecha_salida;
    }
}
