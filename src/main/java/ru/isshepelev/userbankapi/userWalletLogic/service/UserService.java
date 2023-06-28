package ru.isshepelev.userbankapi.userWalletLogic.service;

import org.springframework.stereotype.Service;
import ru.isshepelev.userbankapi.userWalletLogic.entity.User;
import ru.isshepelev.userbankapi.userWalletLogic.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public List<User> findByUsers() {
        return repository.findAll();
    }

    public User findByOneUser(Long id) {
        return repository.findById(id).orElse(null);
    }

    public User create(User user) {
        return repository.save(user);
    }

    public void update(User userUpdate, Long id) {
        Optional<User> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            User user = optionalEntity.get();
            user.setName(userUpdate.getName());
            user.setSurname(userUpdate.getSurname());
            user.setAge(userUpdate.getAge());
            user.setEmail(userUpdate.getEmail());

            repository.save(user);
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
