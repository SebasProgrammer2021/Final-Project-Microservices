package co.edu.uniquindio.inventory.process;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Process {
    public int calculateTotal(int entradas, int salidas) {
        return entradas - salidas;
    }
}
