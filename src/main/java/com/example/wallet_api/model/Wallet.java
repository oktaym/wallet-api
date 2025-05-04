package com.example.wallet_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

import com.example.wallet_api.model.enums.Currency;


@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User customer;

    private String walletName;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private boolean activeForShopping;
    private boolean activeForWithdraw;

    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal usableBalance = BigDecimal.ZERO;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }

    public String getWalletName() { return walletName; }
    public void setWalletName(String walletName) { this.walletName = walletName; }

    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency currency) { this.currency = currency; }

    public boolean isActiveForShopping() { return activeForShopping; }
    public void setActiveForShopping(boolean activeForShopping) { this.activeForShopping = activeForShopping; }

    public boolean isActiveForWithdraw() { return activeForWithdraw; }
    public void setActiveForWithdraw(boolean activeForWithdraw) { this.activeForWithdraw = activeForWithdraw; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public BigDecimal getUsableBalance() { return usableBalance; }
    public void setUsableBalance(BigDecimal usableBalance) { this.usableBalance = usableBalance; }
}
