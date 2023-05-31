package co.edu.uniquindio.autenticacion.dto;

import java.util.List;
import java.util.Map;

public record UserDTO(String username, boolean enabled, String firstName, String lastName, String email,
                      boolean emailVerified, List<Map<String, Object>> credentials) {
}
