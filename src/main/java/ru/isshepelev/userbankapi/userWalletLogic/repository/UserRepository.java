package ru.isshepelev.userbankapi.userWalletLogic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isshepelev.userbankapi.userWalletLogic.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
