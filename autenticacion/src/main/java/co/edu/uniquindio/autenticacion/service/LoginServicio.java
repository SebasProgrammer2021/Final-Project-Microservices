package co.edu.uniquindio.autenticacion.service;

import co.edu.uniquindio.autenticacion.dto.*;
import co.edu.uniquindio.autenticacion.feignclients.AdminFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginServicio {

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private AdminFeignClient adminFeignClient;

    public TokenDTO login(LoginDTO loginDTO){
        TokenResponseDTO tokenResponseDTO = keycloakService.getToken(loginDTO);
        System.out.println("--->" + tokenResponseDTO.access_token().toString());
        return new TokenDTO( tokenResponseDTO.access_token(), tokenResponseDTO.refresh_token() );
    }

    public TokenDTO refresh(TokenDTO token){
        TokenResponseDTO tokenResponseDTO = keycloakService.refreshToken(token.refreshToken());
        return new TokenDTO( tokenResponseDTO.access_token(), tokenResponseDTO.refresh_token() );
    }

    public boolean createUser(NewUserDTO newUserDTO){

        TokenResponseDTO tokenRequest = keycloakService.getTokenAdmin();
        String tokenAdmin = "Bearer " + tokenRequest.access_token();

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", newUserDTO.password());
        credentials.put("temporary", false);

        UserDTO dto = new UserDTO(
                newUserDTO.username(),
                true,
                newUserDTO.firstName(),
                newUserDTO.lastName(),
                newUserDTO.email(),
                true,
                List.of(credentials)
        );

        ResponseEntity<String> responseEntity = adminFeignClient.create(dto, tokenAdmin);

        if(responseEntity.getStatusCode() == HttpStatus.CREATED){

            String userId = getUserId(newUserDTO.username(), tokenAdmin);
            setRoleUser(userId, tokenAdmin);

            return true;

        }

        throw new RuntimeException("Error creando el usuario: "+responseEntity.getStatusCode());

    }

    private String getUserId(String username, String tokenAdmin){

        ResponseEntity<List<ResponseGetUserDTO>> responseEntity = adminFeignClient.getUserId(username, tokenAdmin);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity.getBody().get(0).id();
        }

        throw new RuntimeException("Error, el usuario no existe: "+responseEntity.getStatusCode());
    }

    private void setRoleUser(String userID, String tokenAdmin){

        Map<String, String> role = new HashMap<>();
        role.put("id", "43ac2346-8b13-482a-b1c4-ac77f71e2095");
        role.put("name", "app_user");

        ResponseEntity<Void> responseEntity = adminFeignClient.setRole(userID, List.of(role), tokenAdmin);

        if(responseEntity.getStatusCode() != HttpStatus.NO_CONTENT){
            throw new RuntimeException("Error asignando el rol: "+responseEntity.getStatusCode());
        }

    }

}

