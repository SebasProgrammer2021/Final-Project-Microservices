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

@Document("Inventory")
@NoArgsConstructor
@Data
public class Inventory implements Serializable {
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
    private int salidas;
    private LocalDate fecha_salida;
    @NotNull
    private int total;
    @NotNull
    private int estado;


    @Builder

    public Inventory(String codigo, String nombre, int entradas, LocalDate fecha_entrada, int salidas, LocalDate fecha_salida, int total, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.entradas = entradas;
        this.fecha_entrada = fecha_entrada;
        this.salidas = salidas;
        this.fecha_salida = fecha_salida;
        this.total = total;
        this.estado = estado;
    }
}
