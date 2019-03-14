package cat.tecnocampus.RESTcontrollers;

import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
public class UserRESTController {
    private UserUseCases userUseCases;

    public UserRESTController(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserLab> listUsers() {
        return userUseCases.getUsers();
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLab showUser(@PathVariable String username) {
        return userUseCases.getUser(username);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLab createUser(@RequestBody @Valid  UserLab user) {
        userUseCases.registerUser(user);

        return user;
    }

    @GetMapping(value = "/userExists/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean existsUser(@PathVariable String username) {
        return userUseCases.existUser(username);
    }

    @DeleteMapping(value = "/{username}")
    public int deleteUser(@PathVariable String username) {
        return userUseCases.deleteUser(username);
    }

}
