package ru.isshepelev.userbankapi.userWalletLogic.service;

import org.springframework.stereotype.Service;
import ru.isshepelev.userbankapi.userWalletLogic.entity.Wallet;
import ru.isshepelev.userbankapi.userWalletLogic.repository.WalletRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    public Wallet save(Wallet wallet) {
        return repository.save(wallet);
    }

    public Optional<Wallet> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
