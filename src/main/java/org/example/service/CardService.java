package org.example.service;


import lombok.Setter;
import org.example.dto.CardDTO;
import org.example.dto.ProfileDTO;
import org.example.dto.ResponsDTO;
import org.example.enums.Status;
import org.example.enums.TransactionType;
import org.example.repository.CardRepository;
import org.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
@Setter
@Service
public class CardService {
//    CardRepository cardRepository = new CardRepository();
//    TransactionRepository transactionRepository = new TransactionRepository();

    CardRepository cardRepository ;
    TransactionRepository transactionRepository;

/*//spring da tajriba oxshamadi
    CardRepository cardRepository ;
    TransactionRepository transactionRepository;*/

    public boolean createCard(CardDTO card) {
        List<CardDTO> cardList = getCardList();
        if (cardList != null) {
            for (CardDTO cardDTO : cardList) {
                if (cardDTO.getNumber().equals(card.getNumber())) {
                    System.out.println("Card is available !!!");
                    return false;
                }
            }
        }
        boolean result = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.createCard(card);
        if (result) {
            System.out.println("Card created successfully 👌👌👌");
        } else {
            System.out.println("An error occurred while creating the card !!!");
        }
        return result;
    }

    public List<CardDTO> getCardList() {
        List<CardDTO> cardList =/* Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.getCardList();
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
        for (CardDTO dto : cardList) {
            if (dto.getStatus().equals(Status.NO_ACTIVE)) {
                if (dto.getNumber() != null) {
                    if (dto.getNumber().equals(cardNumber)) {
                        boolean res =/* Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.addCard(dto.getNumber(), profile.getPhone());
                        return res;
                    }
                }
            }
        }
        return false;
    }

    public boolean changeCardStatusByUser(ProfileDTO profile, String cardNumber) {
        if (cardNumber.trim().isEmpty()) {
            System.out.println("Card not founded!!!");
            return false;
        }
        ResponsDTO result = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.changeStatus(cardNumber, profile);
        if (result.success()) {
            System.out.println(result.message());
        } else {
            System.out.println(result.message());
        }
        return result.success();
    }

    public void deleteCard(ProfileDTO profile, String cardNumber) {
        ResponsDTO result = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.delete(cardNumber, profile);
        if (result.success()) {
            System.out.println(result.message());
        } else {
            System.out.println(result.message());
        }

    }

    public void reFillCard(ProfileDTO profile, String cardNumber, double amount, TransactionType type) {
        ResponsDTO result = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.reFill(cardNumber, profile, amount);
        /*Appl.applicationContext.getBean("transactionRepository", TransactionRepository.class)*/transactionRepository.creadTransaction(cardNumber, null, amount, type);

        if (result.success()) {
            System.out.println(result.message());
        } else {
            System.out.println(result.message());
        }
    }

    public void updateCard(String cardNumber, LocalDate expDate, ProfileDTO profile) {
        if (cardNumber.trim().isEmpty()) {
            System.out.println("Card not founded!!!");
            return;
        }
        ResponsDTO result = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.update(cardNumber, expDate, profile);
        if (result.success()) {
            System.out.println(result.message());
        } else {
            System.out.println(result.message());
        }

    }

    public void changeProfileStatus(Status status, String phoneNumber) {
        ResponsDTO result = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.changeProfileStatus(status, phoneNumber);
        if (result.success()) {
            System.out.println(result.message());
        } else {
            System.out.println(result.message());
        }
    }

    public void changeCardStatusByAdmin(String newStatus, String cardNumber) {
        if (newStatus.equals("ACTIVE") || newStatus.equals("NO_ACTIVE") || newStatus.equals("BLOCKED")) {

            List<CardDTO> cardDTOList = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.getCardList();
            ResponsDTO result = null;
            if (cardDTOList == null) {
                System.out.println("Card if not exist!!!");
                return;
            } else {
                for (CardDTO cardDTO : cardDTOList) {
                    if (cardDTO.getNumber().equals(cardNumber)) {
                        result = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.changeCardStatusByAdmin(cardNumber, newStatus);
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
        List<CardDTO> cardDTOList = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.getCardList();
        ResponsDTO result = null;
        for (CardDTO cardDTO : cardDTOList) {
            if (cardDTO.getNumber().equals(cardNumber)) {
                result = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.deleteCardByAdmin(cardNumber);
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
        List<CardDTO> cardList = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.getCardList();
        for (CardDTO cardDTO : cardList) {
            if (cardDTO.getNumber().equals("9860454217805332")) {
                return true;
            }
        }
        return false;
    }

    public void showCompanyCardBalance() {
        List<CardDTO> cardDTOList = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.getCardList();
        List<CardDTO> cardDTOList1 = null;
        if (cardDTOList == null) {
            System.out.println("No information found!!!");
        } else {
            for (CardDTO cardDTO : cardDTOList) {
                if (cardDTO.getNumber().equals("9860454217805332") && cardDTO.getStatus().equals(Status.ACTIVE)) {
                     cardDTOList1 = /*Appl.applicationContext.getBean("cardRepository",CardRepository.class)*/cardRepository.showCompanyCardBalance();
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
