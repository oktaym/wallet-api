package com.example.wallet_api.mapper;

import com.example.wallet_api.dto.UserDto;
import com.example.wallet_api.dto.WalletDto;
import com.example.wallet_api.model.Wallet;

public class WalletMapper {
	public static WalletDto toDto(Wallet wallet) {
        return new WalletDto(
            wallet.getId(),
            wallet.getWalletName(),
            wallet.getCurrency(),
            wallet.isActiveForShopping(),
            wallet.isActiveForWithdraw(),
            wallet.getBalance(),
            wallet.getUsableBalance(),
            new UserDto(
                wallet.getCustomer().getId(),
                wallet.getCustomer().getName(),
                wallet.getCustomer().getSurname(),
                wallet.getCustomer().getUsername(),
                wallet.getCustomer().getRole().name()
            )
        );
    }
}
