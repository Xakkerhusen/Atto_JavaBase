package org.example.springConfig;

import org.example.controller.Controller;
import org.example.repository.CardRepository;
import org.example.repository.ProfileRepository;
import org.example.repository.TerminalRepository;
import org.example.repository.TransactionRepository;
import org.example.service.CardService;
import org.example.service.ProfileService;
import org.example.service.TerminalService;
import org.example.service.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "org.example")
@Configuration
public class SpringConfig {

    @Bean
    public Controller controller(ProfileService ps, CardService cs, TerminalService ts, TransactionService trs, TransactionRepository trr) {
        Controller controller = new Controller();
        controller.setProfileService(ps);
        controller.setCardService(cs);
        controller.setTerminalService(ts);
        controller.setTransactionService(trs);
        controller.setTransactionRepository(trr);
        return controller;
    }

    @Bean
    public CardService cardService(CardRepository crr,TransactionRepository trr){
        CardService cardService=new CardService();
        cardService.setCardRepository(crr);
        cardService.setTransactionRepository(trr);
        return cardService;
    }

    @Bean
    public ProfileService profileService(ProfileRepository prr){
        ProfileService profileService=new ProfileService();
        profileService.setProfileRepository(prr);
        return profileService;
    }

    @Bean
    public TerminalService terminalService(TerminalRepository trr){
        TerminalService terminalService=new TerminalService();
        terminalService.setTerminalRepository(trr);
        return terminalService;
    }

    @Bean
    public TransactionService transactionService( CardService cs,CardRepository crr, TerminalService ts, TerminalRepository tr, TransactionRepository trr) {
        TransactionService transactionService = new TransactionService();
        transactionService.setCardService(cs);
        transactionService.setCardRepository(crr);
        transactionService.setTerminalService(ts);
        transactionService.setTerminalRepository(tr);
        transactionService.setTransactionRepository(trr);
        return transactionService;
    }

}
