package org.example.controller;


import lombok.Setter;
import org.example.db.DatabaseUtil;
import org.example.dto.CardDTO;
import org.example.dto.ProfileDTO;
import org.example.dto.TerminalDTO;
import org.example.dto.TransactionDTO;
import org.example.enums.ProfileRole;
import org.example.enums.Status;
import org.example.enums.TransactionType;
import org.example.repository.TransactionRepository;
import org.example.service.CardService;
import org.example.service.ProfileService;
import org.example.service.TerminalService;
import org.example.service.TransactionService;
import org.example.utils.ScannerUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Setter
@org.springframework.stereotype.Controller
public class Controller {

    static ScannerUtils scanner = new ScannerUtils();

//    ProfileService userService = new ProfileService();
//    CardService cardService = new CardService();
//    TerminalService terminalService = new TerminalService();
//    TransactionService transactionService = new TransactionService();
//    TransactionRepository transactionRepository = new TransactionRepository();

    //  spring da tajriba oxshamadi
//    @Autowired(required = false)
   private ProfileService profileService;
//    @Autowired(required = false)
   private CardService cardService;
//    @Autowired(required = false)
   private TerminalService terminalService;
//    @Autowired(required = false)
   private TransactionService transactionService;
//    @Autowired(required = false)
   private TransactionRepository transactionRepository;

    public void start() {
        System.out.println();
        DatabaseUtil databaseUtil = new DatabaseUtil();
        databaseUtil.createProfileTable();
        databaseUtil.createCardTable();
        databaseUtil.createTerminalTable();
        databaseUtil.createTransactionTable();
        do {
            showMain();
            int action = getAction();
            switch (action) {
                case 1 -> login();
                case 2 -> registration();
                default -> System.out.println("Wrong action selected!!!");
            }
        } while (true);

    }

    private void registration() {

        String name = scanner.nextLine("Enter name:");

        String surname = scanner.nextLine("Enter surname:");
        String phone;
        String password;
        do {
            phone = scanner.nextLine("Enter phoneNumber: ");
            password = scanner.nextLine("Enter password: ");
        } while (phone == null || password == null);


        ProfileDTO profile = new ProfileDTO();
        profile.setName(name);
        profile.setSurname(surname);
        profile.setPhone(phone);
        profile.setPassword(password);
        profile.setProfileRole(ProfileRole.USER);


        boolean result = profileService.registration(profile);

        if (result) {
            System.out.println("Successful 👌👌👌");
        } else {
            System.out.println("Error registration!!!");
        }

    }

    private void login() {
        String phoneNumber = null;
        String password = null;
        do {
            phoneNumber = scanner.nextLine("Enter phoneNumber:");
            password = scanner.nextLine(" Enter password:");
//            System.out.println();
        } while (phoneNumber.trim().isEmpty() || password.trim().isEmpty());
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setPhone(phoneNumber);
        profileDTO.setPassword(password);

        ProfileDTO profile = profileService.login(profileDTO);
        if (profile == null) {
            System.out.println("Phone number or password error try again or through registration!!!");
        } else {
            if (profile.getStatus().equals(Status.NO_ACTIVE)) {
                System.out.println("Not found!!!");
                return;
            }
            if (profile.getProfileRole().equals(ProfileRole.USER)) {
                userMenu(profile);
            } else {
                adminMenu(profile);
            }
        }


    }


    private static void showAdminMenu() {
        System.out.println("""
                1.Card
                2.Terminal
                3.Profile
                4.TransactionList
                5.Company Card Balance
                6.Statistic
                0. Exit""");
    }

    private void adminMenu(ProfileDTO profile) {
        do {
            System.out.println();
            System.out.println("-*-*--*-*-ADMIN MENU*-*-**-*-*-");
            showAdminMenu();
            int option = getAction();
            switch (option) {
                case 0 -> {
                    return;
                }
                case 1 -> cardMenu(profile);
                case 2 -> terminalMenu();
                case 3 -> profileMenu(profile);
                case 4 -> transactionList();
                case 5 -> showCompanyCardBalance();
                case 6 -> statisticmenu();
                default -> System.out.println("Wrong action selected!!!");
            }
        } while (true);
    }

    private static void showUserMenu() {
        System.out.println("""
                 1. Add Card:          
                 2. Card List:         
                 3. Card Change Status 
                 4. Delete Card        
                 5. ReFill             
                 6. Transaction        
                 7. Make Payment       
                 0. Exit               
                """);
    }

    private void userMenu(ProfileDTO profile) {
        do {
            showUserMenu();
            int action = getAction();
            switch (action) {
                case 0 -> {
                    return;
                }
                case 1 -> addCardByUser(profile);
                case 2 -> showCardsByUser(profile);
                case 3 -> changeCardStatusByUser(profile);
                case 4 -> deleteCardByUser(profile);
                case 5 -> reFillCard(profile);
                case 6 -> transactionByUser();
                case 7 -> makePayment();
                default -> System.out.println("Wrong action selected!!!");
            }
        } while (true);


    }


    private void showStatisticMenu() {
        System.out.println("""
                1.Bugungi to'lovlar
                2.Kunlik to'lovlar (bir kunlik to'lovlar)
                3.Oraliq to'lovlar
                4.Umumiy balance (company card dagi pulchalar)
                5. Transaction by Terminal
                6.Transaction By Card
                0.Exit
                """);
    }

    private void statisticmenu() {
        System.out.println("-*-*-**--**-STATISTIC MENU*-*-*-*-*-*-");
        do {
            System.out.println();
            showStatisticMenu();
            int action = getAction();
            switch (action) {
                case 0 -> {
                    return;
                }
                case 1 -> paymentsToday();///chala
                case 2 -> dailyFees();
                case 3 -> interimPayments();
                case 4 -> showCompanyCardBalance();
                case 5 -> transactionByTerminal();
                case 6 -> transactionByCard();
                default -> System.out.println("Wrong action selected!!!");
            }
        } while (true);
    }

    private static int showTerminalMenu() {
        System.out.println();
        System.out.println("******TERMINAL SETTING *****");
        System.out.println("""
                1. Create Terminal (code unique,address)
                2. Terminal List
                3. Update Terminal (code,address)
                4. Change Terminal Status
                5. Delete
                0. Exit""");
        int option = scanner.nextInt("Choose option: ");
        return option;
    }

    private void terminalMenu() {
        do {
            int option = showTerminalMenu();

            switch (option) {
                case 0 -> {
                    return;
                }
                case 1 -> createTerminal();
                case 2 -> showTerminalList();
                case 3 -> updateTerminal();
                case 4 -> changeTerminalStatusByAdmin();
                case 5 -> deleteTerminal();
                default -> System.out.println("Wrong action selected!!!");
            }
        } while (true);

    }

    private void profileMenu(ProfileDTO profile) {

        do {
            System.out.println("***** PROFILE SETTINGS *****");
            System.out.println("""
                    1. Profile List
                    2. Change Profile Status
                    0. Exit""");
            int action = getAction();
            switch (action) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    profileService.showProfileList();
                }
                case 2 -> {
                    changeProfileStatusMenu();
                }
                default -> {
                    System.out.println("Wrong action selected!!!");
                }
            }
        } while (true);
    }

    private void changeProfileStatusMenu() {
        do {
            System.out.println();
            System.out.println("***** Change Profile Status *****");
            System.out.println("""
                    1. Make active
                    2. Make block
                    0. Exit""");
            int action = getAction();

            switch (action) {
                case 0 -> {
                    return;
                }
                case 1 -> changeProfileStatus(Status.ACTIVE);
                case 2 -> changeProfileStatus(Status.BLOCKED);
            }

        } while (true);
    }

    private void cardMenu(ProfileDTO profile) {
        do {
            System.out.println();
            System.out.println("****** CARD SETTINGS *****");
            System.out.println("""
                    1. Create Card(number,exp_date)
                    2. Card List
                    3. Update Card (number,exp_date)
                    4. Change Card status
                    5. Delete Card
                    0. Exit""");
            int option = scanner.nextInt("Choose option: ");

            switch (option) {
                case 0 -> {
                    return;
                }
                case 1 -> createCardByAmin(profile);
                case 2 -> showCardsByAdmin();
                case 3 -> updateCardByAdmin(profile);
                case 4 -> changeCardStatusByAdmin();
                case 5 -> deleteCardByAdmin();
                default -> System.out.println("Wrong action selected!!!");
            }
        } while (true);

    }


    private void transactionByCard() {
        String cardNumber = scanner.nextLine("Enter card number: ");
        boolean result = transactionService.transactionByCard(cardNumber);
        if (!result) {
            System.out.println("No transaction was made from a card with such a card number!!!");
        } else {
            List<TransactionDTO> transactionDTOS = transactionRepository.transactionByCard(cardNumber);
            for (TransactionDTO transactionDTO : transactionDTOS) {
                System.out.println(transactionDTO);
            }
        }
    }

    private void transactionByTerminal() {
        String terminalCode = scanner.nextLine("Enter terminal code: ");
        boolean result = transactionService.transactionByTerminal(terminalCode);
        if (!result) {
            System.out.println("No transaction was made from a terminal with such a code!!!");
        } else {
            List<TransactionDTO> transactionDTOS = transactionRepository.transactionByTerminal(terminalCode);
            for (TransactionDTO transactionDTO : transactionDTOS) {
                System.out.println(transactionDTO);
            }
        }
    }

    private void interimPayments() {
        String day1 = scanner.nextLine("Enter the day (yyyy-MM-dd): ");
        String day2 = scanner.nextLine("Enter the day (yyyy-MM-dd): ");
        boolean result = transactionService.interimPayments(day1, day2);
        if (!result) {
            System.out.println("Transaction not implemented!!!");
        } else {
            List<TransactionDTO> transactionDTOS = transactionRepository.interimPayments(day1, day1);
            for (TransactionDTO transactionDTO : transactionDTOS) {
                System.out.println(transactionDTO);
            }
        }
    }

    private void dailyFees() {
        String day = scanner.nextLine("Enter the day (yyyy-MM-dd): ");
        boolean result = transactionService.dailyFees(day);
        if (!result) {
            System.out.println("Transaction not implemented!!!");
        } else {
            List<TransactionDTO> transactionDTOS = transactionRepository.dailyFees(day);
            for (TransactionDTO transactionDTO : transactionDTOS) {
                System.out.println(transactionDTO);
            }
        }

    }

    private void showCompanyCardBalance() {
        cardService.showCompanyCardBalance();
    }

    private void transactionList() {
        boolean result = transactionService.transactionList();
        if (result) {
            System.out.println("No transaction has been completed!!!");
        } else {
            List<TransactionDTO> transactionList = transactionRepository.getAllTransactions();
            for (TransactionDTO transactionDTO : transactionList) {
                System.out.println(transactionDTO);
            }
        }
    }

    private void paymentsToday() {
        boolean result = transactionService.getTransactionToday();

        if (result) {
            System.out.println("Transaction not implemented!!!");
        } else {
            List<TransactionDTO> transactionList = transactionRepository.gettransactionToday();
            for (TransactionDTO transactionDTO : transactionList) {
                System.out.println(transactionDTO);
            }
        }

    }

    private void transactionByUser() {
        String cardNumber = scanner.nextLine("Enter card number:");
        boolean result = transactionService.getTransaction(cardNumber);
        if (result) {
            System.out.println("No transaction has been made with this card!!!");
        } else {
            List<TransactionDTO> transactionList = transactionRepository.getTransactionList(cardNumber);
            for (TransactionDTO transactionDTO : transactionList) {
                System.out.println(transactionDTO);
            }
        }
    }

    private void makePayment() {
        String cardNumber = scanner.nextLine("Enter  card number: ");
        String terminalCode = scanner.nextLine("Enter  terminal code: ");
        double amount = scanner.nextDouble("Enter amount: ");
        transactionService.makePayment(cardNumber, terminalCode, amount, TransactionType.PAYMENT);
    }

    private void deleteTerminal() {
        String terminalCode = scanner.nextLine("Enter terminal code: ");
        terminalService.deleteTerminal(terminalCode);
    }

    private void changeTerminalStatusByAdmin() {
        String terminalCode = scanner.nextLine("Enter terminal code ");
        String newTerminalStatus = scanner.nextLine("Enter  new terminal status (NO_ACTIVE or ACTIVE or BLOCKED): ");
        terminalService.changeTerminalStatusByAdmin(terminalCode, newTerminalStatus);


    }

    private void updateTerminal() {
        String terminalCode, terminalAddress;
        do {
            terminalCode = scanner.nextLine("Enter terminal code: ");
            terminalAddress = scanner.nextLine("Enter terminal address: ");
        } while (terminalCode.trim().isEmpty() || terminalAddress.trim().isEmpty());
        TerminalDTO terminal = new TerminalDTO();
        terminal.setCode(terminalCode);

        terminalService.updateTerminal(terminal, terminalAddress);
    }

    private void showTerminalList() {
        terminalService.showTerminalList();
    }

    private void createTerminal() {
//        (code unique,address)
        String terminalCode, terminalAddress;
        do {
            terminalCode = scanner.nextLine("Enter terminal code: ");
            terminalAddress = scanner.nextLine("Enter terminal address: ");
        } while (terminalCode.trim().isEmpty() || terminalAddress.trim().isEmpty());
        TerminalDTO terminal = new TerminalDTO();
        terminal.setCode(terminalCode);
        terminal.setAddress(terminalAddress);

        terminalService.creatTerminal(terminal);

    }

    private void changeProfileStatus(Status status) {
        String phoneNumber = scanner.nextLine("Enter Phone Number: ");
        cardService.changeProfileStatus(status, phoneNumber);
    }

    private void deleteCardByAdmin() {
        String cardNumber = scanner.nextLine("Enter card number: ");
        cardService.deleteCardByAdmin(cardNumber);
    }

    private void changeCardStatusByAdmin() {
        String cardNumber = scanner.nextLine("Enter card number: ");
        String newStatus = scanner.nextLine("Enter new status (ACTIVE or NO_ACTIVE or BLOCKED): ");
        cardService.changeCardStatusByAdmin(newStatus, cardNumber);

    }

    public void showCardsByAdmin() {
        List<CardDTO> cardList = cardService.getCardList();
        if (cardList != null) {
            for (CardDTO ownCard : cardList) {
                if (ownCard.getStatus().equals(Status.ACTIVE)) {
                    System.out.println(ownCard);
                } else if (ownCard.getStatus().equals(Status.NO_ACTIVE)) {
                    System.out.println(ownCard);
                } else if (ownCard.getStatus().equals(Status.BLOCKED)) {
                    System.out.println(ownCard);
                }
            }
        }
    }

    private void updateCardByAdmin(ProfileDTO profile) {
//        3. Update Card (number,exp_date)
        String cardNumber = scanner.nextLine("Enter Card number: ");
        LocalDate expDate = scanner.nextLocalDate("Enter expiration date: ");

        cardService.updateCard(cardNumber, expDate, profile);

    }

    private void createCardByAmin(ProfileDTO profile) {
        String cardNumber;
        int year;
        do {
            cardNumber = scanner.nextLine("Enter Card number: ");
            year = scanner.nextInt("Enter the expiration date (3-10): ");
        } while (cardNumber.trim().isEmpty() || year <= 0);
        CardDTO card = new CardDTO();
        card.setNumber(cardNumber);
        card.setExp_date(LocalDate.now().plusYears(year));
        card.setStatus(Status.NO_ACTIVE);
//        card.setPhone(profile.getPhone());
        cardService.createCard(card);

    }

    private void reFillCard(ProfileDTO profile) {
        String cardNumber = scanner.nextLine("Enter Card number: ");
        double amount = scanner.nextInt("Enter amount: ");
        cardService.reFillCard(profile, cardNumber, amount, TransactionType.REFILL);

    }

    private void deleteCardByUser(ProfileDTO profile) {
        String cardNumber = scanner.nextLine("Enter Card number: ");
        cardService.deleteCard(profile, cardNumber);

    }

    private void changeCardStatusByUser(ProfileDTO profile) {
        String cardNumber = scanner.nextLine("Enter Card number: ");
        cardService.changeCardStatusByUser(profile, cardNumber);
    }

    private void showCardsByUser(ProfileDTO profile) {
        List<CardDTO> ownCards = cardService.getOwnCards(profile);
        if (ownCards.isEmpty()) {
            System.out.println("You have no cards !!!");
            return;
        }
        for (CardDTO ownCard : ownCards) {
            if (ownCard.getStatus().equals(Status.ACTIVE)) {
                System.out.println(ownCard);
            } else if (ownCard.getStatus().equals(Status.NO_ACTIVE)) {
                System.out.println(ownCard);
            } else if (ownCard.getStatus().equals(Status.BLOCKED)) {
                System.out.println(ownCard);
            }
        }
    }

    private void addCardByUser(ProfileDTO profile) {
        String cardNumber;
        do {
            cardNumber = scanner.nextLine("Enter the card number: ");
        } while (cardNumber.trim().isEmpty());
        boolean result = cardService.addCard(profile, cardNumber);
        if (result) {
            System.out.println("Card added👌👌👌");
        } else {
            System.out.println("Card not added !!!");
        }
    }

    private int getAction() {
        int option = scanner.nextInt("Choose action: ");
        return option;
    }

    private void showMain() {
        System.out.print("""
                 1. Login        
                 2. Registration 
                """);
    }


}
