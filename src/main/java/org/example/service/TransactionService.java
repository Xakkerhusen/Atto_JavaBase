package org.example.service;

import lombok.Setter;
import org.example.controller.Appl;
import org.example.dto.CardDTO;
import org.example.dto.ResponsDTO;
import org.example.dto.TerminalDTO;
import org.example.dto.TransactionDTO;
import org.example.enums.Status;
import org.example.enums.TransactionType;
import org.example.repository.CardRepository;
import org.example.repository.TerminalRepository;
import org.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Setter
@Service
public class TransactionService {
//    CardRepository cardRepository = new CardRepository();
//    CardService cardService = new CardService();
//    TerminalRepository terminalRepository = new TerminalRepository();
//    TerminalService terminalService = new TerminalService();
//    TransactionRepository transactionRepository = new TransactionRepository();

   private CardRepository cardRepository ;
   private CardService cardService;
   private TerminalRepository terminalRepository;
   private TerminalService terminalService;
   private TransactionRepository transactionRepository;

/*//    spring da tajriba oxshamadi
    CardRepository cardRepository;
    CardService cardService;
    TerminalRepository terminalRepository;
    TerminalService terminalService;
    TransactionRepository transactionRepository;*/
    public void makePayment(String cardNumber, String terminalCode, double amount, TransactionType type) {
        List<CardDTO> cardDTOList = /*Appl.applicationContext.getBean("cardRepository", CardRepository.class)*/cardRepository.getCardList();
        List<TerminalDTO> terminalDTOS = /*Appl.applicationContext.getBean("terminalRepository", TerminalRepository.class)*/terminalRepository.getTerminalList();
        ResponsDTO resultTerminal = null;
        boolean terminalChecking = Appl.applicationContext.getBean("terminalService", TerminalService.class).chackTerminalCode(terminalCode);
        boolean checkCardCompany = Appl.applicationContext.getBean("cardService", CardService.class).chackCardCompany();
        ResponsDTO resultCard = null;
        if (cardDTOList == null) {
            System.out.println("Card if not exist!!!");
        } else if (terminalDTOS == null) {
            System.out.println("Terminal if not exist!!!");
        } else if (amount < 0) {
            System.out.println("Amount entered error!!!");
        } else {
            for (CardDTO cardDTO : cardDTOList) {
                if (cardDTO.getNumber().equals(cardNumber)
                        && terminalChecking
                        && cardDTO.getStatus().equals(Status.ACTIVE) && checkCardCompany) {
                    resultTerminal = /*Appl.applicationContext.getBean("transactionRepository", TransactionRepository.class)*/transactionRepository.creadTransaction(cardNumber, terminalCode, amount, type);
                    resultCard = /*Appl.applicationContext.getBean("cardRepository", CardRepository.class)*/cardRepository.updateCardBalance(cardNumber, amount);
                    /*Appl.applicationContext.getBean("cardRepository", CardRepository.class)*/cardRepository.updateCardCompany(amount);
                }
            }

            if (!checkCardCompany) {
                System.out.println("Company card not found!!!");
            } else if (!terminalChecking) {
                System.out.println("Terminal not found!!! ");
            } else if (resultCard == null) {
                System.out.println("Card not found!!! ");
            } else if (resultTerminal.success()) {
                System.out.println(resultTerminal.message());
            } else {
                System.out.println(resultTerminal.message());
            }

        }


    }

    public boolean getTransaction(String cardNumber) {
        List<TransactionDTO> transactionList = /*Appl.applicationContext.getBean("transactionRepository", TransactionRepository.class)*/transactionRepository.getTransactionList(cardNumber);
        return transactionList.isEmpty();
    }

    public boolean getTransactionToday() {
        List<TransactionDTO> transactionDTOS = /*Appl.applicationContext.getBean("transactionRepository", TransactionRepository.class)*/transactionRepository.gettransactionToday();
        return transactionDTOS.isEmpty();

    }

    public boolean transactionList() {
        List<TransactionDTO> allTransactions = /*Appl.applicationContext.getBean("transactionRepository", TransactionRepository.class)*/transactionRepository.getAllTransactions();
        return allTransactions.isEmpty();
    }

    public boolean dailyFees(String day) {
        if (!day.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            System.out.println("Enter the day in  (yyyy-mm-dd) format!!!");
            return false;
        } else {
            List<TransactionDTO> dailyFees = /*Appl.applicationContext.getBean("transactionRepository", TransactionRepository.class)*/transactionRepository.dailyFees(day);
            return dailyFees != null;
        }

    }

    public boolean interimPayments(String day1, String day2) {
        if (!(day1.matches("^\\d{4}-\\d{2}-\\d{2}$") || day2.matches("^\\d{4}-\\d{2}-\\d{2}$")) ||
                Objects.equals(day2, day1)) {
            System.out.println("Enter the day in  (yyyy-mm-dd) format!!!");
            return false;
        } else {
            List<TransactionDTO> dailyFees = /*Appl.applicationContext.getBean("transactionRepository", TransactionRepository.class)*/transactionRepository.interimPayments(day1, day2);
            return dailyFees != null;
        }

    }

    public boolean transactionByTerminal(String terminalCode) {
        List<TransactionDTO> transactionDTOS = /*Appl.applicationContext.getBean("transactionRepository", TransactionRepository.class)*/transactionRepository.transactionByTerminal(terminalCode);
        if (transactionDTOS.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean transactionByCard(String cardNumber) {
        List<TransactionDTO> transactionDTOS = /*Appl.applicationContext.getBean("transactionRepository", TransactionRepository.class)*/transactionRepository.transactionByCard(cardNumber);
        if (transactionDTOS.isEmpty()) {
            return false;
        }
        return true;
    }
}
