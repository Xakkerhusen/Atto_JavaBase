package org.example.service;

import lombok.Setter;
import org.example.dto.CardDTO;
import org.example.dto.ResponsDTO;
import org.example.dto.TerminalDTO;
import org.example.dto.TransactionDTO;
import org.example.enums.Status;
import org.example.enums.TransactionType;
import org.example.repository.CardRepository;
import org.example.repository.TerminalRepository;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Setter
@Service
public class TransactionService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private TerminalRepository terminalRepository;
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private TransactionRepository transactionRepository;

    public void makePayment(String cardNumber, String terminalCode, double amount, TransactionType type) {
        List<CardDTO> cardDTOList = cardRepository.getCardList();
        List<TerminalDTO> terminalDTOS = terminalRepository.getTerminalList();
        ResponsDTO resultTerminal = null;
        boolean terminalChecking = terminalService.chackTerminalCode(terminalCode);
        boolean checkCardCompany = cardService.chackCardCompany();
        ResponsDTO resultCard = null;
        if (cardDTOList == null) {
            System.out.println("Card if not exist!!!");
        } else if (terminalDTOS == null) {
            System.out.println("Terminal if not exist!!!");
        } else if (amount < 0) {
            System.out.println("Amount entered error!!!");
        } else {
            for (CardDTO cardDTO : cardDTOList) {
                if (cardDTO.getNumber().equals(cardNumber) && terminalChecking && cardDTO.getStatus().equals(Status.ACTIVE) && checkCardCompany) {
                    if (cardDTO.getBalance()<amount){
                        System.out.println("There is not enough balance on the card!!!");
                        return;
                    }
                    resultTerminal = transactionRepository.creadTransaction(cardNumber, terminalCode, amount, type);
                    resultCard = cardRepository.updateCardBalance(cardNumber, amount);
                    cardRepository.updateCardCompany(amount);
                }
            }

            if (!checkCardCompany) {
                System.out.println("Company card not found!!!");
            } else if (!terminalChecking) {
                System.out.println("Terminal not found!!! ");
            } else if (resultCard == null) {
                System.out.println("Card not found or card status is not active!!! ");
            } else if (resultTerminal.success()) {
                System.out.println(resultTerminal.message());
            } else {
                System.out.println(resultTerminal.message());
            }

        }

    }

    public boolean getTransaction(String cardNumber) {
        List<TransactionDTO> transactionList = transactionRepository.getTransactionList(cardNumber);
        return transactionList.isEmpty();
    }

    public boolean getTransactionToday() {
        List<TransactionDTO> transactionDTOS = transactionRepository.gettransactionToday();
        return transactionDTOS.isEmpty();

    }

    public boolean transactionList() {
        List<TransactionDTO> allTransactions = transactionRepository.getAllTransactions();
        return allTransactions.isEmpty();
    }

    public boolean dailyFees(String day) {
        if (!day.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            System.out.println("Enter the day in  (yyyy-mm-dd) format!!!");
            return false;
        } else {
            List<TransactionDTO> dailyFees = transactionRepository.dailyFees(day);
            return dailyFees != null;
        }

    }

    public boolean interimPayments(String day1, String day2) {
        if (!(day1.matches("^\\d{4}-\\d{2}-\\d{2}$") || day2.matches("^\\d{4}-\\d{2}-\\d{2}$")) ||
                Objects.equals(day2, day1)) {
            System.out.println("Enter the day in  (yyyy-mm-dd) format!!!");
            return false;
        } else {
            List<TransactionDTO> dailyFees = transactionRepository.interimPayments(day1, day2);
            return dailyFees != null;
        }

    }

    public boolean transactionByTerminal(String terminalCode) {
        List<TransactionDTO> transactionDTOS = transactionRepository.transactionByTerminal(terminalCode);
        if (transactionDTOS.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean transactionByCard(String cardNumber) {
        List<TransactionDTO> transactionDTOS = transactionRepository.transactionByCard(cardNumber);
        if (transactionDTOS.isEmpty()) {
            return false;
        }
        return true;
    }
}
