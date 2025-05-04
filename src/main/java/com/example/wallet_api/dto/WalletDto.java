package com.example.wallet_api.dto;

import java.math.BigDecimal;

import com.example.wallet_api.model.enums.Currency;

public class WalletDto {
	private Long id;
    private String walletName;
    private Currency currency;
    private boolean activeForShopping;
    private boolean activeForWithdraw;
    private BigDecimal balance;
    private BigDecimal usableBalance;
    private UserDto customer;
    
    
	public WalletDto() {
		super();
	}
	
	
	public WalletDto(Long id, String walletName, Currency currency, boolean activeForShopping,
			boolean activeForWithdraw, BigDecimal balance, BigDecimal usableBalance, UserDto customer) {
		super();
		this.id = id;
		this.walletName = walletName;
		this.currency = currency;
		this.activeForShopping = activeForShopping;
		this.activeForWithdraw = activeForWithdraw;
		this.balance = balance;
		this.usableBalance = usableBalance;
		this.customer = customer;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWalletName() {
		return walletName;
	}
	public void setWalletName(String walletName) {
		this.walletName = walletName;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public boolean isActiveForShopping() {
		return activeForShopping;
	}
	public void setActiveForShopping(boolean activeForShopping) {
		this.activeForShopping = activeForShopping;
	}
	public boolean isActiveForWithdraw() {
		return activeForWithdraw;
	}
	public void setActiveForWithdraw(boolean activeForWithdraw) {
		this.activeForWithdraw = activeForWithdraw;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getUsableBalance() {
		return usableBalance;
	}
	public void setUsableBalance(BigDecimal usableBalance) {
		this.usableBalance = usableBalance;
	}
	public UserDto getCustomer() {
		return customer;
	}
	public void setCustomer(UserDto customer) {
		this.customer = customer;
	}
    
    

}
