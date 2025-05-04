package com.example.wallet_api.dto;

import com.example.wallet_api.model.enums.Currency;

public class CreateWalletRequest {
	private String walletName;
    private Currency currency;
    private boolean forShopping;
    private boolean forWithdraw;
    
	public CreateWalletRequest() {
		super();
	}
	
	public CreateWalletRequest(String walletName, Currency currency, boolean forShopping, boolean forWithdraw) {
		super();
		this.walletName = walletName;
		this.currency = currency;
		this.forShopping = forShopping;
		this.forWithdraw = forWithdraw;
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
	public boolean isForShopping() {
		return forShopping;
	}
	public void setForShopping(boolean forShopping) {
		this.forShopping = forShopping;
	}
	public boolean isForWithdraw() {
		return forWithdraw;
	}
	public void setForWithdraw(boolean forWithdraw) {
		this.forWithdraw = forWithdraw;
	}
    
    
}
