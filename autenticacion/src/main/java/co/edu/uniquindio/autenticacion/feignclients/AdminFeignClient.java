package co.edu.uniquindio.autenticacion.feignclients;

import co.edu.uniquindio.autenticacion.dto.ResponseGetUserDTO;
import co.edu.uniquindio.autenticacion.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "AdminKeycloak", url = "${keycloack.admin-url}")
public interface AdminFeignClient {

    @PostMapping("/users")
    ResponseEntity<String> create(@RequestBody UserDTO userDTO, @RequestHeader(value = "Authorization") String bearerToken);

    @GetMapping("/users/")
    ResponseEntity<List<ResponseGetUserDTO>> getUserId(@RequestParam String username, @RequestHeader(value = "Authorization") String bearerToken);

    @PostMapping("/users/{userId}/role-mappings/realm")
    ResponseEntity<Void> setRole(@PathVariable String userId, @RequestBody List<Map<String, String>> role, @RequestHeader(value = "Authorization") String bearerToken);
}
