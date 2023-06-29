package ru.isshepelev.userbankapi.userWalletLogic.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.isshepelev.userbankapi.CBbankApi.CurrencyApiHandler;
import ru.isshepelev.userbankapi.userWalletLogic.entity.Wallet;
import ru.isshepelev.userbankapi.userWalletLogic.service.UserService;
import ru.isshepelev.userbankapi.userWalletLogic.service.WalletService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class TransferController {
    final WalletService walletService;
    final UserService userService;

    public TransferController(WalletService walletService, UserService userService) {
        this.walletService = walletService;
        this.userService = userService;
    }


    @PutMapping("/accounts/{senderId}/transfer/{recipientId}")
    public void transfer(@PathVariable("senderId") Long senderId, @PathVariable("recipientId") Long recipientId, @RequestBody Wallet walletMoney) throws IOException {
        CurrencyApiHandler handler = new CurrencyApiHandler();
        BigDecimal walletBD = walletService.findById(senderId).get().getMoney();
        if (walletMoney.getMoney().compareTo(walletBD) <= 0) {
            String currencySender = walletService.findById(senderId).get().getCurrency();
            String currencyRecipient = walletService.findById(recipientId).get().getCurrency();
            BigDecimal difference = handler.transferCurrency(currencySender, currencyRecipient);

            BigDecimal senderMoney = walletService.findById(senderId).get().getMoney().subtract(walletMoney.getMoney());
            BigDecimal walletDifference = walletMoney.getMoney().multiply(difference);
            BigDecimal recipientMoney = walletService.findById(recipientId).get().getMoney().add(walletDifference);

            walletService.updateMoney(senderMoney, recipientMoney, senderId, recipientId);
        }
    }

}
