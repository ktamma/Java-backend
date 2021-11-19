package controller;

import dao.UserDao;
import model.Order;
import model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private UserDao dao;

    public UserController(UserDao repository) {
        this.dao = repository;
    }


    @PostMapping("login")
    public Order createOrder(@RequestBody @Valid Order order) {
        return null;
    }

    @GetMapping("version")
    public String home() {
        return "Api version";
    }

    @GetMapping("users/{userName}")
    @PreAuthorize("#userName == authentication.name || authentication.name == 'admin'")
    public User getUserByName(@PathVariable String userName) {
        return dao.getUserByUserName(userName);
    }


    @GetMapping("users")
    @PreAuthorize("authentication.name == 'admin'")
    public List<User> getUsers() {
        return dao.getUsers();
    }
}