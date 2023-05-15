package co.edu.uniquindio.autenticacion.service;

import co.edu.uniquindio.autenticacion.dto.LoginDTO;
import co.edu.uniquindio.autenticacion.dto.TokenResponseDTO;
import co.edu.uniquindio.autenticacion.feignclients.TokenFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private TokenFeignClient tokenFeignClient;

    public TokenResponseDTO getTokenAdmin(){
        return getToken(new LoginDTO("globaladmin", "password"));
    }

    public TokenResponseDTO getToken(LoginDTO loginDTO){

        String clientID = "springboot-keycloak-client";
        String usernameAdmin = loginDTO.username();
        String passwordAdmin  = loginDTO.password();
        String grantType = "password";

        String requestBody = String.format("client_id=%s&username=%s&password=%s&grant_type=%s", clientID, usernameAdmin, passwordAdmin, grantType);

        return sendRequest(requestBody);
    }

    public TokenResponseDTO refreshToken(String expiredToken){

        String clientID = "springboot-keycloak-client";
        String grantType = "refresh_token";

        String requestBody = String.format("client_id=%s&refresh_token=%s&grant_type=%s", clientID, expiredToken, grantType);
        return sendRequest(requestBody);
    }

    private TokenResponseDTO sendRequest(String requestBody){

        ResponseEntity<TokenResponseDTO> responseEntity = tokenFeignClient.sendRequest(requestBody);

        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }

        throw new RuntimeException("Error en la petición "+responseEntity.getStatusCode());

    }
}