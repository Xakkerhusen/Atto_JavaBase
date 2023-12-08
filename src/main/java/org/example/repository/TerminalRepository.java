package org.example.repository;

import lombok.Setter;
import org.example.db.DatabaseUtil;
import org.example.dto.ResponsDTO;
import org.example.dto.TerminalDTO;
import org.example.enums.Status;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
@Setter
@Repository
public class TerminalRepository {
    public ResponsDTO creatTerminal(TerminalDTO terminal) {

        Connection connection = DatabaseUtil.getConnection();
        int res = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into terminal(code,address) values (?,?)");

            preparedStatement.setString(1, terminal.getCode());
            preparedStatement.setString(2, terminal.getAddress());

            res = preparedStatement.executeUpdate();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (res != 0) {
            return new ResponsDTO("Terminal created ", true);
        }


        return new ResponsDTO("There is an error in the information", false);
    }

    public List<TerminalDTO> getTerminalList() {

        List<TerminalDTO> terminalList = new LinkedList<>();
        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            String sql = "select * from terminal";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                TerminalDTO terminal = new TerminalDTO();
                terminal.setCode(resultSet.getString("code"));
                terminal.setAddress(resultSet.getString("address"));
                terminal.setStatus(Status.valueOf(resultSet.getString("status")));
                terminal.setCreatedDate(resultSet.getDate("created_date").toLocalDate());
                terminalList.add(terminal);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terminalList;
    }

    public ResponsDTO updateTerminal(TerminalDTO terminal,String address) {
        int res = 0;
        try {
            Connection connection = DatabaseUtil.getConnection();
            String sql = "update terminal set address=? where code=? " ;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, address);
            preparedStatement.setString(2, terminal.getCode());
            res = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(res != 0){
            return new ResponsDTO("Terminal updated ", true);
        }else{
            return new ResponsDTO("There is an error in the information ", false);
        }
    }


    public ResponsDTO changeTerminalStatusByAdmin(String terminalCode, String newTerminalStatus) {
        int res = 0;
        try {
            Connection connection = DatabaseUtil.getConnection();
            String sql = "update terminal set status=? where code=? " ;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newTerminalStatus);
            preparedStatement.setString(2, terminalCode);
            res = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(res != 0){
            return new ResponsDTO("Terminal status changed👌👌👌 ", true);
        }else{
            return new ResponsDTO("There is an error in the information ", false);
        }


    }

    public ResponsDTO deleteTerminal(String terminalCode) {
        int res = 0;
        try {
            Connection connection = DatabaseUtil.getConnection();
            String sql = "update terminal set address=?, status='BLOCKED' where code=? " ;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, terminalCode);
            res = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(res != 0){
            return new ResponsDTO("Terminal deleted 👌👌👌 ", true);
        }else{
            return new ResponsDTO("There is an error in the information ", false);
        }
    }


}
