package kz.f1zIk.shyraq.client;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import kz.f1zIk.shyraq.dto.UserCreateDto;
import kz.f1zIk.shyraq.dto.UserLoginDto;
import kz.f1zIk.shyraq.dto.UserTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakClient {

    private final Keycloak keycloak;

    private final RestTemplate restTemplate;

    @Value("${keycloak.url}")
    private String url;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public UserRepresentation createUser(UserCreateDto userCreateDTO) {

        UserRepresentation newUser = new UserRepresentation();
        newUser.setEmail(userCreateDTO.getEmail());
        newUser.setEmailVerified(true);
        newUser.setUsername(userCreateDTO.getUsername());
        newUser.setFirstName(userCreateDTO.getFirstName());
        newUser.setLastName(userCreateDTO.getLastName());
        newUser.setEnabled(true);

        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(userCreateDTO.getPassword());
        credentials.setTemporary(false);

        newUser.setCredentials(List.of(credentials));
        Response response = keycloak
                .realm(realm)
                .users()
                .create(newUser);

        if (response.getStatus() != HttpStatus.CREATED.value()) {
            log.error("Error on creating user, status : {}", response.getStatus());
            throw new RuntimeException("Failed to create user in keycloak, status = " + response.getStatus());
        }

        List<UserRepresentation> userList = keycloak.realm(realm)
                .users()
                .search(userCreateDTO.getUsername());

        return userList.get(0);
    }

    public UserTokenDto Signin(UserLoginDto userLoginDto) {
        String tokenEndpoint = url + "/realms/" + realm + "/protocol/openid-connect/token";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", userLoginDto.getUsername());
        formData.add("password", userLoginDto.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        ResponseEntity<Map> response = restTemplate
                .postForEntity(tokenEndpoint, new HttpEntity<>(formData, headers), Map.class);

        Map<String, Object> responseBody = response.getBody();

        if (!response.getStatusCode().is2xxSuccessful() || responseBody == null) {
            log.error("Error on signing in with user {}", userLoginDto.getUsername());
            throw new RuntimeException("Error on signing in with user, status = " + response.getStatusCode());
        }

        UserTokenDto userTokenDto = new UserTokenDto();
        userTokenDto.setToken((String) responseBody.get("access_token"));
        userTokenDto.setRefreshToken((String) responseBody.get("refresh_token"));
        return userTokenDto;

    }
}
