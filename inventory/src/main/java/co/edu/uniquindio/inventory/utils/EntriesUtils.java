package co.edu.uniquindio.inventory.utils;

import co.edu.uniquindio.inventory.dto.EntryDTO;
import co.edu.uniquindio.inventory.dto.ExitDTO;
import co.edu.uniquindio.inventory.dto.InventoryDTO;
import co.edu.uniquindio.inventory.model.Entries;
import co.edu.uniquindio.inventory.model.Exits;
import co.edu.uniquindio.inventory.model.Inventory;
import co.edu.uniquindio.inventory.process.Process;
import co.edu.uniquindio.inventory.repo.EntriesRepo;
import co.edu.uniquindio.inventory.repo.InventoryRepo;
import co.edu.uniquindio.inventory.services.excepciones.InventoryNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class EntriesUtils {
    private final InventoryRepo inventoryRepo;
    private final Process inventoryProcess;
    private final EntriesRepo entriesRepo;

    public Entries setupInventoryToSave(InventoryDTO inventory) {
        return Entries.builder()
                .codigo(inventory.codigo().trim())
                .nombre(inventory.nombre())
                .entradas(inventory.entradas())
                .fecha_entrada(LocalDate.now())
                .estado(1).build();
    }

    public Inventory setupInventoryToUpdate(InventoryDTO inventory, Inventory inventoryResult) {
        return Inventory.builder()
                .codigo(inventoryResult.getCodigo())
                .nombre(inventoryResult.getNombre())
                .entradas(0)
                .fecha_entrada(null)
                .salidas(inventory.salidas())
                .fecha_salida(LocalDate.now())
                .total(inventoryResult.getTotal() - inventory.salidas())
                .estado(inventoryResult.getEstado())
                .build();
    }

    public Inventory setupInventoryDTOToInventory(InventoryDTO inventory) {
        return Inventory.builder()
                .codigo(inventory.codigo())
                .nombre(inventory.nombre())
                .entradas(inventory.entradas())
                .fecha_entrada(inventory.fecha_entrada())
                .salidas(inventory.salidas())
                .fecha_salida(inventory.fecha_salida())
                .total(inventory.total())
                .build();
    }

    public Exits setupExitDTOToExit(ExitDTO exitDTO) {
        return Exits.builder()
                .codigo(exitDTO.codigo())
                .salidas(exitDTO.salidas())
                .fecha_salida(LocalDate.now())
                .build();
    }

    public EntryDTO transformInventoryToInventoryResponse(Entries entries) {
        return new EntryDTO(entries.getCodigo(), entries.getNombre(),
                entries.getEntradas(), entries.getFecha_entrada());
    }

    public EntryDTO transformEntryToEntryResponse(Entries inventory) {
        return new EntryDTO(inventory.getCodigo(), inventory.getNombre(),
                inventory.getEntradas(), inventory.getFecha_entrada());
    }

    public Inventory getInventory(String codigo) {
        Inventory inventory = inventoryRepo.findFirstByEstadoAndCodigo(1, codigo);
        System.out.println("Prueba");

        if (inventory == null) {
            throw new InventoryNotFoundException("El inventario " + codigo + " no existe.");
        }

        return inventory;
    }

    public Entries getEntry(String codigo) {
        Entries entry = entriesRepo.findFirstByEstadoAndCodigo(1, codigo);

        if (entry == null) {
            throw new InventoryNotFoundException("El inventario " + codigo + " no existe.");
        }

        return entry;
    }

    public List<EntryDTO> setListInventoryDTO(List<Entries> inventoryList) {
        return inventoryList.stream().map(this::transformInventoryToInventoryResponse).toList();
    }

}
