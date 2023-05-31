package co.edu.uniquindio.inventory.services;

import co.edu.uniquindio.inventory.dto.EntryDTO;
import co.edu.uniquindio.inventory.dto.InventoryDTO;
import co.edu.uniquindio.inventory.model.Entries;
import co.edu.uniquindio.inventory.model.Inventory;
import co.edu.uniquindio.inventory.repo.EntriesRepo;
import co.edu.uniquindio.inventory.repo.InventoryRepo;
import co.edu.uniquindio.inventory.services.excepciones.InventoryNotFoundException;
import co.edu.uniquindio.inventory.utils.EntriesUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    private final EntriesRepo entriesRepo;
    private final EntriesUtils entriesUtils;

    public EntryDTO save(InventoryDTO productEntries) {
        int isActive = 1;
        Entries inventoryFormatted = inventoryFormatted = entriesUtils.setupInventoryToSave(productEntries);
        return entriesUtils.transformEntryToEntryResponse(entriesRepo.save(inventoryFormatted));
    }

    public EntryDTO findById(String inventoryCode) {
        Optional<Entries> entries = entriesRepo.findTopByEstadoAndCodigoOrderByIdDesc(1, inventoryCode);
        System.out.println("inventory = " + entries);
        return entriesUtils.transformInventoryToInventoryResponse(entries.get());
    }

    //ajustar para que traiga solo el ultimo inventario de lque este activo de cada uno
    public List<EntryDTO> findAll() {
        int estado = 1;
        return entriesUtils.setListInventoryDTO(entriesRepo.findByEstado(estado));
    }

//    public InventoryDTO update(InventoryDTO inventory) {
//        Optional<Inventory> inventoryResult = inventoryRepo.findTopByEstadoAndCodigoOrderByIdDesc(1, inventory.codigo());
//        System.out.println("Resultado update de bbdd inventoryResult = " + inventoryResult);
//        Inventory inventorySette = entriesUtils.setupInventoryToUpdate(inventory, inventoryResult.get());
//
//        return entriesUtils.transformInventoryToInventoryResponse(inventoryRepo.save(inventorySette));
//    }

    public void deleteById(String inventoryCode) {
        entriesUtils.getInventory(inventoryCode);
        List<Inventory> inventoryList = inventoryRepo.findAllByCodigo(inventoryCode);

        for (Inventory inventory : inventoryList) {
            inventory.setEstado(0);
            inventoryRepo.save(inventory);
        }
    }

    public void deleteAll() {
        List<Optional<Inventory>> inventoryList = inventoryRepo.findAllByEstado(1);
        if (inventoryList.isEmpty()) {
            throw new InventoryNotFoundException("No hay inventarios para eliminar");
        }

        for (Optional<Inventory> inventory : inventoryList) {
            if (inventory.isPresent()) {
                Inventory inventoryRes = inventory.get();
                inventoryRes.setEstado(0);
                inventoryRepo.save(inventoryRes);
            }
        }
    }
}
