package org.example.repository;

import lombok.Setter;
import org.example.dto.ResponsDTO;
import org.example.dto.TransactionDTO;
import org.example.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Setter
@Repository
public class TransactionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public ResponsDTO creadTransaction(String cardNumber, String terminalCode, double amount, TransactionType type) {
        int res;
        String sql = "insert into transactions(card_number_user,amount,terminal_code,transaction_type) values (?,?,?,?)";
        res = jdbcTemplate.update(sql, cardNumber, amount, terminalCode, type.name());
        if (res != 0) {
            return new ResponsDTO("Transaction completed👌👌👌 ", true);
        }
        return new ResponsDTO("Error occurred while the operation was being processed!!!", false);
    }
    public List<TransactionDTO> getTransactionList(String cardNumber) {
        List<TransactionDTO> transactionDTOList;
        String sql = "select * from transactions where card_number_user=? order by transaction_time desc ";
        transactionDTOList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionDTO.class), cardNumber);
        return transactionDTOList;
    }

    public List<TransactionDTO> gettransactionToday() {
        List<TransactionDTO> transactionDTOList;
        String sql = "SELECT * FROM   transactions u WHERE  u.transaction_time >= (LOCALTIMESTAMP - interval '1h')::date + interval '1h'";
        transactionDTOList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionDTO.class));
        return transactionDTOList;
    }

    public List<TransactionDTO> getAllTransactions() {
        List<TransactionDTO> transactionDTOList;
        String sql = "select * from transactions ";
        transactionDTOList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionDTO.class));
        return transactionDTOList;
    }

    public List<TransactionDTO> dailyFees(String day) {
        List<TransactionDTO> transactionDTOList;
        String sql = "SELECT * FROM transactions WHERE DATE_TRUNC('day', transaction_time) =? ::date;";
        transactionDTOList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionDTO.class), day);
        return transactionDTOList;
    }

    public List<TransactionDTO> interimPayments(String day1, String day2) {
        List<TransactionDTO> transactionDTOList = new LinkedList<>();
        String sql = "SELECT * FROM transactions WHERE  transaction_time BETWEEN ? AND ? ::date";
        transactionDTOList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionDTO.class), day1, day2);
        return transactionDTOList;
    }
    public List<TransactionDTO> transactionByTerminal(String terminalCode) {
        List<TransactionDTO> transactionDTOList;
        String sql = "SELECT * FROM transactions WHERE  terminal_code=?";
        transactionDTOList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionDTO.class), terminalCode);
        return transactionDTOList;
    }
    public List<TransactionDTO> transactionByCard(String cardNumber) {
        List<TransactionDTO> transactionDTOList;
        String sql = "SELECT * FROM transactions WHERE  card_number_user=?";
        transactionDTOList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionDTO.class), cardNumber);
        return transactionDTOList;
    }

}
