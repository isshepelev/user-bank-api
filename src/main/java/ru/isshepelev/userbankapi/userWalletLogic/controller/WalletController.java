package ru.isshepelev.userbankapi.userWalletLogic.controller;


import org.springframework.web.bind.annotation.*;
import ru.isshepelev.userbankapi.CBbankApi.CurrencyApiHandler;
import ru.isshepelev.userbankapi.userWalletLogic.entity.User;
import ru.isshepelev.userbankapi.userWalletLogic.entity.Wallet;
import ru.isshepelev.userbankapi.userWalletLogic.service.UserService;
import ru.isshepelev.userbankapi.userWalletLogic.service.WalletService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
public class WalletController {
    final WalletService walletService;
    final UserService userService;

    public WalletController(WalletService walletService, UserService userService) {
        this.walletService = walletService;
        this.userService = userService;
    }

    @GetMapping("/wallets/{userId}")
    public List<Wallet> getWallets(@PathVariable("userId") Long id) {
        User user = userService.findByOneUser(id);
        return user.getWallets();
    }

    @PostMapping("/wallet/{userId}")
    public Wallet create(@PathVariable("userId") Long id, @RequestBody Wallet wallet) throws IOException {
        CurrencyApiHandler handler = new CurrencyApiHandler();
        for (int i = 0; i < handler.currencyList().size(); i++) {
            if (wallet.getCurrency().equals(handler.currencyList().get(i))) {
                User user = userService.findByOneUser(id);
                wallet.setUser(user);
                return walletService.save(wallet);
            }
        }
        return null;
    }

    @DeleteMapping("/wallet/{walletId}")
    public void delete(@PathVariable("walletId") Long id) {
        Optional<Wallet> optionalWallet = walletService.findById(id);
        if (optionalWallet.isPresent()) {
            Wallet wallet = optionalWallet.get();
            if (wallet.getMoney().compareTo(BigDecimal.ZERO) == 0) {
                walletService.delete(id);
            }
        }
    }

    @PutMapping("/wallet/{walletId}")
    public void updateWallet(@PathVariable("walletId") Long id, @RequestBody Wallet wallet) {
        walletService.updateWallet(wallet, id);

    }

}
