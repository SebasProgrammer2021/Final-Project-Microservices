package co.edu.uniquindio.inventory.services.excepciones;

public class InventoryNotFoundException extends RuntimeException{

    public InventoryNotFoundException(String mensaje){
        super(mensaje);
    }

}
