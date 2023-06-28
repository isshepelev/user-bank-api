package ru.isshepelev.userbankapi.userWalletLogic.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Wallet name is required")
    private String name;

    @NotBlank(message = "The name of the currency is required")
    private String currency;

    @Min(value = 0, message = "Monetary amount must be non-negative")
    private BigDecimal money;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Wallet() {
        this.money = BigDecimal.ZERO;
    }

}
