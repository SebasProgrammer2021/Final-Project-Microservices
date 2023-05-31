package co.edu.uniquindio.inventory.services;

import co.edu.uniquindio.inventory.dto.EntryDTO;
import co.edu.uniquindio.inventory.dto.ExitDTO;
import co.edu.uniquindio.inventory.dto.InventoryDTO;
import co.edu.uniquindio.inventory.model.Entries;
import co.edu.uniquindio.inventory.model.Exits;
import co.edu.uniquindio.inventory.model.Inventory;
import co.edu.uniquindio.inventory.repo.EntriesRepo;
import co.edu.uniquindio.inventory.repo.ExitsRepo;
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
    private final ExitsRepo exitsRepo;
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

    public Exits update(ExitDTO exit) {
        List<Optional<Entries>> entriesResult = entriesRepo.findAllByEstado(1);
        if (entriesResult.isEmpty()) {
            throw new InventoryNotFoundException("No hay inventarios activos");
        }
        Exits exitResult = null;

        for (Optional<Entries> entry : entriesResult) {
            System.out.println("entry = " + entry);
            System.out.println(entry.get().getCodigo());
            System.out.println(exit.codigo());
            if (entry.get().getCodigo().equals(exit.codigo())) {
                System.out.println("entro en validación y guardaicón");
                Exits exitToSave = entriesUtils.setupExitDTOToExit(exit);
                exitResult = exitsRepo.save(exitToSave);
            }
        }

        if (exitResult == null) {
            throw new InventoryNotFoundException("El registro no esta en el inventario");
        }

        return exitResult;
    }

    public void deleteById(String inventoryCode) {
        entriesUtils.getEntry(inventoryCode);
        List<Entries> entriesList = entriesRepo.findAllByCodigo(inventoryCode);

        for (Entries entry : entriesList) {
            entry.setEstado(0);
            entriesRepo.save(entry);
        }
    }

    public void deleteAll() {
        List<Optional<Entries>> entriesList = entriesRepo.findAllByEstado(1);
        if (entriesList.isEmpty()) {
            throw new InventoryNotFoundException("No hay inventarios para eliminar");
        }

        for (Optional<Entries> entry : entriesList) {
            if (entry.isPresent()) {
                Entries entryRes = entry.get();
                entryRes.setEstado(0);
                entriesRepo.save(entryRes);
            }
        }
    }
}
