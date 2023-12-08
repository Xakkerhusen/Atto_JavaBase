package org.example.repository;

import lombok.Setter;
import org.example.db.DatabaseUtil;
import org.example.dto.ResponsDTO;
import org.example.dto.TransactionDTO;
import org.example.enums.TransactionType;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
@Setter
@Repository
public class TransactionRepository {
    public ResponsDTO creadTransaction(String cardNumber, String terminalCode, double amount, TransactionType type) {
        Connection connection = DatabaseUtil.getConnection();
        int res = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into transactions(card_number_user,amount,terminal_code,transaction_type) values (?,?,?,?)");

            preparedStatement.setString(1, cardNumber);
            preparedStatement.setDouble(2, amount);
            preparedStatement.setString(3, terminalCode);
            preparedStatement.setString(4, type.name());

            res = preparedStatement.executeUpdate();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (res != 0) {
            return new ResponsDTO("Transaction completed👌👌👌 ", true);
        }
        return new ResponsDTO("Error occurred while the operation was being processed!!!",false);
    }


    public List<TransactionDTO> getTransactionList(String cardNumber) {
        List<TransactionDTO> transactionDTOList = new LinkedList<>();
        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            String sql = "select * from transactions where card_number_user = '%s' order by transaction_time desc ";
            sql=String.format(sql, cardNumber);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setCard_number(resultSet.getString("card_number_user"));
                transactionDTO.setAmount(resultSet.getDouble("amount"));
                transactionDTO.setTerminal_code(resultSet.getString("terminal_code"));
                transactionDTO.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
                transactionDTO.setTransactionTime(resultSet.getTimestamp("transaction_time").toLocalDateTime());
                transactionDTOList.add(transactionDTO);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionDTOList;

    }

    public List<TransactionDTO> gettransactionToday() {
        List<TransactionDTO> transactionDTOList = new LinkedList<>();
        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
//            String sql = "select * from transactions where transaction_time > now() - interval '24 hours' ";
//            String sql = "select * from transactions where transaction_time > %s ";
            //            ResultSet resultSet = statement.executeQuery(String.format(sql, Timestamp.valueOf(LocalDate.now().atStartOfDay())));
            String sql = "SELECT * FROM   transactions u WHERE  u.transaction_time >= (LOCALTIMESTAMP - interval '1h')::date + interval '1h'";

                        ResultSet resultSet = statement.executeQuery(String.format(sql));

            while (resultSet.next()) {
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setCard_number(resultSet.getString("card_number_user"));
                transactionDTO.setAmount(resultSet.getDouble("amount"));
                transactionDTO.setTerminal_code(resultSet.getString("terminal_code"));
                transactionDTO.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
                transactionDTO.setTransactionTime(resultSet.getTimestamp("transaction_time").toLocalDateTime());
                transactionDTOList.add(transactionDTO);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionDTOList;
    }

    public List<TransactionDTO> getAllTransactions() {
        List<TransactionDTO> transactionDTOList = new LinkedList<>();
        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            String sql = "select * from transactions ";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setCard_number(resultSet.getString("card_number_user"));
                transactionDTO.setAmount(resultSet.getDouble("amount"));
                transactionDTO.setTerminal_code(resultSet.getString("terminal_code"));
                transactionDTO.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
                transactionDTO.setTransactionTime(resultSet.getTimestamp("transaction_time").toLocalDateTime());
                transactionDTOList.add(transactionDTO);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionDTOList;


    }

    public List<TransactionDTO> dailyFees(String day) {
        List<TransactionDTO> transactionDTOList = new LinkedList<>();
        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM transactions WHERE DATE_TRUNC('day', transaction_time) = '%s'::date;";
            sql=String.format(sql,day);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setCard_number(resultSet.getString("card_number_user"));
                transactionDTO.setAmount(resultSet.getDouble("amount"));
                transactionDTO.setTerminal_code(resultSet.getString("terminal_code"));
                transactionDTO.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
                transactionDTO.setTransactionTime(resultSet.getTimestamp("transaction_time").toLocalDateTime());
                transactionDTOList.add(transactionDTO);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionDTOList;
    }

    public List<TransactionDTO> interimPayments(String day1, String day2) {
        List<TransactionDTO> transactionDTOList = new LinkedList<>();
        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM transactions WHERE  transaction_time BETWEEN '2023-12-03' AND '2023-12-04'::date";
            sql=String.format(sql,day1,day2);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setCard_number(resultSet.getString("card_number_user"));
                transactionDTO.setAmount(resultSet.getDouble("amount"));
                transactionDTO.setTerminal_code(resultSet.getString("terminal_code"));
                transactionDTO.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
                transactionDTO.setTransactionTime(resultSet.getTimestamp("transaction_time").toLocalDateTime());
                transactionDTOList.add(transactionDTO);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionDTOList;

    }

    public List<TransactionDTO> transactionByTerminal(String terminalCode) {
        List<TransactionDTO> transactionDTOList = new LinkedList<>();
        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM transactions WHERE  terminal_code='%s'";
            sql=String.format(sql,terminalCode);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setCard_number(resultSet.getString("card_number_user"));
                transactionDTO.setAmount(resultSet.getDouble("amount"));
                transactionDTO.setTerminal_code(resultSet.getString("terminal_code"));
                transactionDTO.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
                transactionDTO.setTransactionTime(resultSet.getTimestamp("transaction_time").toLocalDateTime());
                transactionDTOList.add(transactionDTO);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionDTOList;
    }

    public List<TransactionDTO> transactionByCard(String cardNumber) {
        List<TransactionDTO> transactionDTOList = new LinkedList<>();
        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM transactions WHERE  card_number_user='%s'";
            sql=String.format(sql,cardNumber);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setCard_number(resultSet.getString("card_number_user"));
                transactionDTO.setAmount(resultSet.getDouble("amount"));
                transactionDTO.setTerminal_code(resultSet.getString("terminal_code"));
                transactionDTO.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
                transactionDTO.setTransactionTime(resultSet.getTimestamp("transaction_time").toLocalDateTime());
                transactionDTOList.add(transactionDTO);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionDTOList;
    }

}
