package co.edu.uniquindio.inventory.utils;

import co.edu.uniquindio.inventory.dto.InventoryDTO;
import co.edu.uniquindio.inventory.model.Inventory;
import co.edu.uniquindio.inventory.process.Process;
import co.edu.uniquindio.inventory.repo.InventoryRepo;
import co.edu.uniquindio.inventory.services.excepciones.InventoryNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class Utils {
    private final InventoryRepo inventoryRepo;
    private final Process inventoryProcess;

    public Inventory setupInventoryToSave(InventoryDTO inventory) {
        return Inventory.builder()
                .codigo(inventory.codigo().trim())
                .nombre(inventory.nombre())
                .entradas(inventory.entradas())
                .fecha_entrada(LocalDate.now())
                .total(inventory.entradas())
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

    public InventoryDTO transformInventoryToInventoryResponse(Inventory inventory) {
        return new InventoryDTO(inventory.getCodigo(), inventory.getNombre(),
                inventory.getEntradas(), inventory.getFecha_entrada(),
                inventory.getSalidas(), inventory.getFecha_salida(), inventory.getTotal());
    }

    public Inventory getInventory(String codigo) {
        Inventory inventory = inventoryRepo.findFirstByEstadoAndCodigo(1, codigo);
        System.out.println("Prueba");

        if (inventory == null) {
            throw new InventoryNotFoundException("El inventario " + codigo + " no existe.");
        }

        return inventory;
    }

    public List<InventoryDTO> setListInventoryDTO(List<Inventory> inventoryList) {
        return inventoryList.stream().map(this::transformInventoryToInventoryResponse).toList();
    }

}
