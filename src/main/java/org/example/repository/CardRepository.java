package org.example.repository;

import lombok.Setter;
import org.example.dto.CardDTO;
import org.example.dto.ProfileDTO;
import org.example.dto.ResponsDTO;
import org.example.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Setter
@Repository
public class CardRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public boolean createCard(String cardNumber, LocalDate localDate, Status status) {
        int res;
        String sql = "insert into card(number,exp_date,balance,phone) values (?,?,?,?)";
        res = jdbcTemplate.update(sql, cardNumber,localDate,0,null);
        return res != 0;
    }
    public List<CardDTO> getCardList() {
        List<CardDTO> cardList;
        String sql = "select * from card";
        cardList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardDTO.class));
        return cardList;
    }

    public ResponsDTO addCard(String number, String phone) {
        int res;
        String sql = "update card set phone=?, status=? where number=?";
        res = jdbcTemplate.update(sql, phone,Status.ACTIVE.name(), number);
        if (res > 0) {
            return new ResponsDTO("Card status changed ", true);
        }
        return new ResponsDTO("Card not founded", false);
    }

    public ResponsDTO changeStatus(String cardNumber, ProfileDTO profile, String status) {
        int res;
        String sql = "update card set status=? where number=? and phone=?";
        res = jdbcTemplate.update(sql,status,cardNumber, profile.getPhone());

        if (res > 0) {
            return new ResponsDTO("Card status changed ", true);
        }
        return new ResponsDTO("Card not founded", false);
    }

    public ResponsDTO delete(String cardNumber) {
        int res;
        String sql = "delete from card where code=?";
        res = jdbcTemplate.update(sql, Status.BLOCKED.name(), cardNumber);
        if (res > 0) {
            return new ResponsDTO("Card deleted ", true);
        }
        return new ResponsDTO("Error!", false);
    }

    public ResponsDTO reFill(String cardNumber, ProfileDTO profile, double amount) {
        int res;
        String sql = "update card set balance=balance+? where number=? and phone=?";
        res = jdbcTemplate.update(sql, amount, cardNumber, profile.getPhone());
        if (res > 0) {
            return new ResponsDTO("Depositing money to the card has been done successfully 👌👌👌", true);
        }
        return new ResponsDTO("Error Fill!!!", false);
    }


    public ResponsDTO update(String cardNumber, LocalDate expDate) {
        int res;
        String sql = "update card set exp_date=? where number=?";
        res = jdbcTemplate.update(sql, Date.valueOf(expDate), cardNumber);
        if (res > 0) {
            return new ResponsDTO("Updated successfully 👌👌👌", true);
        }
        return new ResponsDTO("Error Fill!!!", false);
    }

    public ResponsDTO changeProfileStatus(Status status, String phoneNumber) {
        int res;
        String sql = "update profile set status=? where phone=?";
        res = jdbcTemplate.update(sql, status.name(), phoneNumber);
        if (res > 0) {
            return new ResponsDTO("Card status changed 👌👌👌", true);
        }
        return new ResponsDTO("Operation error occurred!!! ", false);
    }

    public ResponsDTO changeCardStatusByAdmin(String cardNumber, String newStatus) {
        int res = 0;
        String sql = "update card set status=? where number=?";
        res = jdbcTemplate.update(sql, newStatus, cardNumber);
        if (res != 0) {
            return new ResponsDTO("Card status changed 👌👌👌", true);
        }
        return new ResponsDTO("Operation error occurred!!! ", false);
    }

    public ResponsDTO deleteCardByAdmin(String cardNumber) {
        int res;
        String sql = "delete from card where number=?";
        res = jdbcTemplate.update(sql, cardNumber);
        if (res != 0) {
            return new ResponsDTO("Card deleted 👌👌👌", true);
        }
        return new ResponsDTO("Operation error occurred!!!", false);
    }

    public ResponsDTO updateCardBalance(String cardNumber, double amount) {
        int res;
        String sql = "update card set balance=balance-? where number=?";
        res = jdbcTemplate.update(sql, amount, cardNumber);
        if (res > 0) {
            return new ResponsDTO("from balance " + amount + " withdrawals.", true);
        }
        return new ResponsDTO("Error Fill!!!", false);
    }

    public ResponsDTO updateCardCompany(double amount) {
        int res = 0;
        String sql = "update card set balance=balance+? where number=?";
        res = jdbcTemplate.update(sql, amount, "9860454217805332");
        if (res > 0) {
            return new ResponsDTO("from balance " + amount + " withdrawals.", true);
        }
        return new ResponsDTO("Error Fill!!!", false);
    }

    public List<CardDTO> showCompanyCardBalance() {
        List<CardDTO> cardList;
        String sql = "select * from card where number='9860454217805332'";
        cardList=jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardDTO.class));
        return cardList;
    }

}

