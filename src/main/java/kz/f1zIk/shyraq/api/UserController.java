package kz.f1zIk.shyraq.api;
import kz.f1zIk.shyraq.dto.UserCreateDto;
import kz.f1zIk.shyraq.dto.UserLoginDto;
import kz.f1zIk.shyraq.dto.UserTokenDto;
import kz.f1zIk.shyraq.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public UserTokenDto login(@RequestBody UserLoginDto user) {
        return userService.authenticate(user);
    }
}
