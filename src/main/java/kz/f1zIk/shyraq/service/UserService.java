package kz.f1zIk.shyraq.service;
import kz.f1zIk.shyraq.client.KeycloakClient;
import kz.f1zIk.shyraq.dto.UserCreateDto;
import kz.f1zIk.shyraq.dto.UserDto;
import kz.f1zIk.shyraq.dto.UserLoginDto;
import kz.f1zIk.shyraq.dto.UserTokenDto;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakClient keycloakClient;

    public UserDto createUser(UserCreateDto userCreateDTO){

        UserRepresentation userRepresentation = keycloakClient.createUser(userCreateDTO);
        UserDto userDTO = new UserDto();
        userDTO.setUsername(userRepresentation.getUsername());
        userDTO.setEmail(userRepresentation.getEmail());
        userDTO.setFirstName(userRepresentation.getFirstName());
        userDTO.setLastName(userRepresentation.getLastName());

        return userDTO;

    }

    public UserTokenDto authenticate(UserLoginDto userLoginDto){
        return keycloakClient.Signin(userLoginDto);
    }
}
