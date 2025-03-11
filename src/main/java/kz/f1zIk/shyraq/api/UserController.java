package kz.f1zIk.shyraq.api;
import kz.f1zIk.shyraq.converter.UserUtils;
import kz.f1zIk.shyraq.dto.*;
import kz.f1zIk.shyraq.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/current-user-name")
    public String getCurrentUserName() {
        return UserUtils.getCurrentUserName();
    }

    @GetMapping(value = "/current-user")
    public UserDto getCurrentUser() {
        return UserUtils.getCurrentUser();
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordDto userChangePasswordDTO) {

        String userName = UserUtils.getCurrentUserName();
        if (userName == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Couldn't find user");
        }

        try{

            userService.changePassword(userName, userChangePasswordDTO.getNewPassword());
            return ResponseEntity.ok("Password changed successfully!");

        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error on changing password");
        }

    }
}
