package org.example.service;


import lombok.Setter;
import org.example.dto.CardDTO;
import org.example.dto.ProfileDTO;
import org.example.dto.ResponsDTO;
import org.example.enums.Status;
import org.example.enums.TransactionType;
import org.example.repository.CardRepository;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
@Setter
@Service
public class CardService {
@Autowired
   private CardRepository cardRepository ;
@Autowired
   private TransactionRepository transactionRepository;
    public boolean createCard(String cardNumber, LocalDate localDate, Status status) {
        List<CardDTO> cardList = getCardList();
        if (cardList != null) {
            for (CardDTO cardDTO : cardList) {
                if (cardDTO.getNumber().equals(cardNumber)) {
                    System.out.println("Card is available !!!");
                    return false;
                }
            }
        }
        boolean result =cardRepository.createCard(cardNumber,localDate,status);
        if (result) {
            System.out.println("Card created successfully 👌👌👌");
        } else {
            System.out.println("An error occurred while creating the card !!!");
        }
        return result;
    }

    public List<CardDTO> getCardList() {
        List<CardDTO> cardList =cardRepository.getCardList();
        return cardList;
    }

    public List<CardDTO> getOwnCards(ProfileDTO profile) {
        List<CardDTO> cards = new LinkedList<>();
        List<CardDTO> cardList = getCardList();
        for (CardDTO cardDTO : cardList) {
            if (cardDTO.getExp_date() != null) {
                if (cardDTO.getPhone() != null) {
                    if (cardDTO.getPhone().equals(profile.getPhone())) {
                        cards.add(cardDTO);
                    }
                }
            }
        }
        return cards;
    }

    public boolean addCard(ProfileDTO profile, String cardNumber) {
        List<CardDTO> cardList = getCardList();
        ResponsDTO responsDTO=null;
        for (CardDTO dto : cardList) {
            if (dto.getStatus().equals(Status.NO_ACTIVE)) {
                if (dto.getNumber() != null) {
                    if (dto.getNumber().equals(cardNumber)) {
                         responsDTO = cardRepository.addCard(dto.getNumber(), profile.getPhone());
                       break;
                    }
                }
            }
        }
        if (responsDTO==null){
            System.out.println("Enter another card number!!!");
            return false;
        }else if (responsDTO.success()) {
            System.out.println(responsDTO.message());
        }else {
            System.out.println(responsDTO.message());
        }
        return responsDTO.success();
    }

    public boolean changeCardStatusByUser(ProfileDTO profile, String cardNumber, String status) {
        if (cardNumber.trim().isEmpty()) {
            System.out.println("Card not founded!!!");
            return false;
        }
        if (!(status.equals("ACTIVE")||status.equals("NO_ACTIVE")||status.equals("BLOCKED"))){
            System.out.println("Enter one of the (ACTIVE,NO_ACTIVE,BLOCKED)");
            return false;
        }
        ResponsDTO result = cardRepository.changeStatus(cardNumber, profile,status);
        if (result.success()) {
            System.out.println(result.message());
        } else {
            System.out.println(result.message());
        }
        return result.success();
    }

    public void deleteCard(String cardNumber) {
        ResponsDTO result =cardRepository.delete(cardNumber);
        if (result.success()) {
            System.out.println(result.message());
        } else {
            System.out.println(result.message());
        }

    }

    public void reFillCard(ProfileDTO profile, String cardNumber, double amount, TransactionType type) {
        ResponsDTO result =cardRepository.reFill(cardNumber, profile, amount);
        transactionRepository.creadTransaction(cardNumber, null, amount, type);
        if (result.success()) {
            System.out.println(result.message());
        } else {
            System.out.println(result.message());
        }
    }

    public void updateCard(String cardNumber, LocalDate expDate) {
        if (cardNumber.trim().isEmpty()) {
            System.out.println("Card not founded!!!");
            return;
        }
        ResponsDTO result = cardRepository.update(cardNumber, expDate);
        if (result.success()) {
            System.out.println(result.message());
        } else {
            System.out.println(result.message());
        }

    }

    public void changeProfileStatus(Status status, String phoneNumber) {
        ResponsDTO result =cardRepository.changeProfileStatus(status, phoneNumber);
        if (result.success()) {
            System.out.println(result.message());
        } else {
            System.out.println(result.message());
        }
    }

    public void changeCardStatusByAdmin(String newStatus, String cardNumber) {
        if (newStatus.equals("ACTIVE") || newStatus.equals("NO_ACTIVE") || newStatus.equals("BLOCKED")) {

            List<CardDTO> cardDTOList =cardRepository.getCardList();
            ResponsDTO result = null;
            if (cardDTOList == null) {
                System.out.println("Card if not exist!!!");
            } else {
                for (CardDTO cardDTO : cardDTOList) {
                    if (cardDTO.getNumber().equals(cardNumber)) {
                        result =cardRepository.changeCardStatusByAdmin(cardNumber, newStatus);
                        break;
                    }
                }

                if (result == null) {
                    System.out.println(" Wrong card number!!! ");
                } else if (result.success()) {
                    System.out.println(result.message());
                } else {
                    System.out.println(result.message());
                }
            }

        } else {
            System.out.println("Status (ACTIVE or NO_ACTIVE or BLOCKED) be written in format!!!");
        }

    }

    public void deleteCardByAdmin(String cardNumber) {
        List<CardDTO> cardDTOList =cardRepository.getCardList();
        ResponsDTO result = null;
        for (CardDTO cardDTO : cardDTOList) {
            if (cardDTO.getNumber().equals(cardNumber)) {
                result =cardRepository.deleteCardByAdmin(cardNumber);
                break;
            }
        }

        if (result == null) {
            System.out.println(" Wrong card number!!! ");
        } else if (result.success()) {
            System.out.println(result.message());
        } else {
            System.out.println(result.message());
        }

    }

    public boolean chackCardCompany() {
        List<CardDTO> cardList =cardRepository.getCardList();
        for (CardDTO cardDTO : cardList) {
            if (cardDTO.getNumber().equals("9860454217805332")) {
                return true;
            }
        }
        return false;
    }

    public void showCompanyCardBalance() {
        List<CardDTO> cardDTOList =cardRepository.getCardList();
        List<CardDTO> cardDTOList1 = null;
        if (cardDTOList == null) {
            System.out.println("No information found!!!");
        } else {
            for (CardDTO cardDTO : cardDTOList) {
                if (cardDTO.getNumber().equals("9860454217805332") && cardDTO.getStatus().equals(Status.ACTIVE)) {
                     cardDTOList1 =cardRepository.showCompanyCardBalance();
                }
            }

            if (cardDTOList1==null){
                System.out.println("Information about such a card was not found in the database!!!");
            }else {
                for (CardDTO cardDTO : cardDTOList1) {
                    System.out.println(cardDTO);
                }
            }
        }

    }


}
