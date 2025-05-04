package com.example.wallet_api.controller;


import com.example.wallet_api.dto.CreateWalletRequest;
import com.example.wallet_api.dto.WalletDto;
import com.example.wallet_api.model.enums.Currency;
import com.example.wallet_api.repository.UserRepository;
import com.example.wallet_api.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@WebMvcTest(
	    controllers = WalletController.class,
	    excludeFilters = @ComponentScan.Filter(
	        type = FilterType.ASSIGNABLE_TYPE,
	        classes = {com.example.wallet_api.security.JwtFilter.class,
	                   com.example.wallet_api.config.SecurityConfig.class}
	    )
	)
	@AutoConfigureMockMvc(addFilters = false)

class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;
    


    @MockBean
    private UserRepository userRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    private Principal mockPrincipal() {
        return () -> "ali.yilmaz";
    }


    @Test
    @DisplayName("GET /wallets – başarılı listeleme")
    void listWallets_success() throws Exception {
        WalletDto dto = new WalletDto();
        dto.setId(1L);
        dto.setWalletName("Ali1");
        dto.setCurrency(Currency.TRY);

        Mockito.when(walletService.listWallets(Mockito.any()))
               .thenReturn(List.of(dto));

        mockMvc.perform(get("/wallets")
                        .principal(mockPrincipal())
                        .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success", is(true)))
               .andExpect(jsonPath("$.data[0].walletName", is("Ali1")))
               .andExpect(jsonPath("$.data[0].currency", is("TRY")));
    }


    @Test
    @DisplayName("POST /wallets – başarılı oluşturma")
    void createWallet_success() throws Exception {
    	CreateWalletRequest req = new CreateWalletRequest();
        req.setWalletName("MarketWallet");
        req.setCurrency(Currency.TRY);
        req.setForShopping(true);
        req.setForWithdraw(false);

        WalletDto dto = new WalletDto();
        dto.setId(2L);
        dto.setWalletName("MarketWallet");
        dto.setCurrency(Currency.TRY);

        Mockito.when(walletService.createWallet(
                Mockito.eq("MarketWallet"),
                Mockito.eq(Currency.TRY),
                Mockito.eq(true),
                Mockito.eq(false),
                Mockito.eq("ali.yilmaz")
        )).thenReturn(dto);

        mockMvc.perform(post("/wallets")
                        .principal(mockPrincipal())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success", is(true)))
               .andExpect(jsonPath("$.data.walletName", is("MarketWallet")))
               .andExpect(jsonPath("$.data.currency", is("TRY")));
    }
}
