package co.edu.uniquindio.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Response<T>{

    private String message;
    private T response;

    public Response(String mensaje) {
        this.message = mensaje;
    }
}
