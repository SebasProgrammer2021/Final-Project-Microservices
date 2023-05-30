package co.edu.uniquindio.inventory.controller;

import co.edu.uniquindio.inventory.dto.InventoryDTO;
import co.edu.uniquindio.inventory.dto.Response;
import co.edu.uniquindio.inventory.services.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/inventory")
@AllArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Response<InventoryDTO>> save(@RequestBody InventoryDTO productInventory) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Inventario creado exitosamente", inventoryService.save(productInventory)));
    }


    @GetMapping("/{inventoryCode}")
    public ResponseEntity<Response<InventoryDTO>> findById(@PathVariable String inventoryCode) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Inventario encontrado", inventoryService.findById(inventoryCode)));
    }

    @GetMapping
    public ResponseEntity<Response<List<InventoryDTO>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Inventarios encontrados", inventoryService.findAll()));
    }

    @PutMapping
    public ResponseEntity<Response<InventoryDTO>> update(@RequestBody InventoryDTO inventory) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("El inventario se modificó exitosamente", inventoryService.update(inventory)));
    }


    @DeleteMapping("/{inventoryCode}")
    public ResponseEntity<Response<String>> deleteById(@PathVariable String inventoryCode) {
        inventoryService.deleteById(inventoryCode);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("El inventario se eliminó exitosamente"));
    }

    @DeleteMapping()
    public ResponseEntity<Response<String>> deleteAll() {
        inventoryService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Todos los inventarios han sido eliminados exitosamente"));
    }


}
