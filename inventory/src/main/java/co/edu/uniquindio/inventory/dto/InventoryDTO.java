package co.edu.uniquindio.inventory.dto;

import java.time.LocalDate;

public record InventoryDTO(String codigo, String nombre, int entradas, LocalDate fecha_entrada, int salidas,
                           LocalDate fecha_salida, int total) {

}
