package co.edu.uniquindio.inventory.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Document("Entries")
@NoArgsConstructor
@Data
public class Entries implements Serializable {
    @Id
    private String id = UUID.randomUUID().toString();
    @NotNull
    private String codigo;
    @NotNull
    private String nombre;
    @NotNull
    private int entradas;
    @NotNull
    private LocalDate fecha_entrada;
    @NotNull
    private int estado;

    @Builder
    public Entries(String id, String codigo, String nombre, int entradas, LocalDate fecha_entrada, int estado) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.entradas = entradas;
        this.fecha_entrada = fecha_entrada;
        this.estado = estado;
    }
}
