package co.edu.uniquindio.inventory.dto;

import java.time.LocalDate;

public record EntryDTO(String codigo, String nombre, int entradas, LocalDate fecha_entrada) {

}
