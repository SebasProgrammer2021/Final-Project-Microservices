package co.edu.uniquindio.inventory.services;

import co.edu.uniquindio.inventory.dto.InventoryDTO;
import co.edu.uniquindio.inventory.model.Inventory;
import co.edu.uniquindio.inventory.repo.InventoryRepo;
import co.edu.uniquindio.inventory.services.excepciones.InventoryNotFoundException;
import co.edu.uniquindio.inventory.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    private final Utils inventoryUtils;

    public InventoryDTO save(InventoryDTO productInventory) {
        int isActive = 1;
        Inventory inventoryFormatted = new Inventory();
        List<Inventory> inventoryExistingList = inventoryRepo.findAllByEstadoAndCodigo(isActive, productInventory.codigo());
        System.out.println("inventoryExistingList = " + inventoryExistingList);

        if (inventoryExistingList.isEmpty()) {
            return inventoryUtils.transformInventoryToInventoryResponse(inventoryRepo.save(inventoryUtils.setupInventoryToSave(productInventory)));
        }

        List<Inventory> inventorySalidasList = inventoryExistingList.stream().filter(inventory -> inventory.getSalidas() != 0).toList();
        if (inventorySalidasList.isEmpty()) {
            int sum = 0;
            for (Inventory inventory : inventoryExistingList) {
                sum += inventory.getEntradas();
            }

            inventoryFormatted = inventoryUtils.setupInventoryToSave(productInventory);
            inventoryFormatted.setTotal(sum + productInventory.entradas());
        } else {
            Inventory ultimoElemento = inventorySalidasList.get(inventorySalidasList.size() - 1);
            inventoryFormatted = inventoryUtils.setupInventoryToSave(productInventory);
            inventoryFormatted.setTotal(ultimoElemento.getTotal() + productInventory.entradas());
        }

        return inventoryUtils.transformInventoryToInventoryResponse(inventoryRepo.save(inventoryFormatted));
    }

    public InventoryDTO findById(String inventoryCode) {
        Optional<Inventory> inventory = inventoryRepo.findTopByEstadoAndCodigoOrderByIdDesc(1, inventoryCode);
        System.out.println("inventory = " + inventory);
        return inventoryUtils.transformInventoryToInventoryResponse(inventory.get());
    }

    //ajustar para que traiga solo el ultimo inventario de lque este activo de cada uno
    public List<InventoryDTO> findAll() {
        int estado = 1;
        return inventoryUtils.setListInventoryDTO(inventoryRepo.findByEstado(estado));
    }

    public InventoryDTO update(InventoryDTO inventory) {
        Optional<Inventory> inventoryResult = inventoryRepo.findTopByEstadoAndCodigoOrderByIdDesc(1, inventory.codigo());
        System.out.println("Resultado update de bbdd inventoryResult = " + inventoryResult);
        Inventory inventorySette = inventoryUtils.setupInventoryToUpdate(inventory, inventoryResult.get());

        return inventoryUtils.transformInventoryToInventoryResponse(inventoryRepo.save(inventorySette));
    }

    public void deleteById(String inventoryCode) {
        inventoryUtils.getInventory(inventoryCode);
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
