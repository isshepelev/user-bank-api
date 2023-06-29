package ru.isshepelev.userbankapi.userWalletLogic.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import ru.isshepelev.userbankapi.userWalletLogic.entity.Wallet;
import ru.isshepelev.userbankapi.userWalletLogic.repository.WalletRepository;

import java.math.BigDecimal;
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

    public void updateWallet(Wallet walletUpdate, Long id) {
        Optional<Wallet> optionalWallet = repository.findById(id);
        if (optionalWallet.isPresent()) {
            Wallet existingWallet = optionalWallet.get();
            existingWallet.setMoney(walletUpdate.getMoney());
            repository.save(existingWallet);
        }
    }

    public Optional<Wallet> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void updateMoney(BigDecimal senderMoney, BigDecimal recipientMoney, Long senderId, Long recipientId) {
        Optional<Wallet> optionalWalletSender = repository.findById(senderId);
        Optional<Wallet> optionalWalletRecipient = repository.findById(recipientId);
        if (optionalWalletSender.isPresent() && optionalWalletRecipient.isPresent()) {
            Wallet walletSender = optionalWalletSender.get();
            Wallet walletRecipient = optionalWalletRecipient.get();

            walletSender.setMoney(senderMoney);
            walletRecipient.setMoney(recipientMoney);

            repository.save(walletSender);
            repository.save(walletRecipient);
        }
    }
}
