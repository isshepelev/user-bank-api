package ru.isshepelev.userbankapi.userWalletLogic.controller;


import org.springframework.web.bind.annotation.*;
import ru.isshepelev.userbankapi.userWalletLogic.entity.User;
import ru.isshepelev.userbankapi.userWalletLogic.service.UserService;

import java.util.List;

@RestController
public class UserController {
    final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return service.findByUsers();
    }

    @GetMapping("/user/{userId}")
    public User getOneUser(@PathVariable("userId") Long id){
        return service.findByOneUser(id);
    }

    @PostMapping("/user/create")
    public User createUser(@RequestBody User user){
        return service.create(user);
    }


    @PutMapping("/user/{userId}/update")
    public void updateUser(@RequestBody User userUpdate, @PathVariable("userId") Long id){
        service.update(userUpdate, id);
    }

    @DeleteMapping("/user/{userId}/delete")
    public void delete(@PathVariable("userId") Long id){
        service.delete(id);
    }

}
