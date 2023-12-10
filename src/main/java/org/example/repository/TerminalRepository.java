package org.example.repository;

import lombok.Setter;
import org.example.dto.ResponsDTO;
import org.example.dto.TerminalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Setter
@Repository
public class TerminalRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public ResponsDTO creatTerminal(String terminalCode, String terminalAddress) {
        int res = 0;
        String sql = "insert into terminal(code,address) values (?,?)";
        res=jdbcTemplate.update(sql, terminalCode, terminalAddress);
        if (res != 0) {
            return new ResponsDTO("Terminal created ", true);
        }
        return new ResponsDTO("There is an error in the information", false);
    }
    public List<TerminalDTO> getTerminalList() {
        List<TerminalDTO> terminalList;
        String sql = "select * from terminal";
        terminalList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TerminalDTO.class));
        return terminalList;
    }
    public ResponsDTO updateTerminal(String terminalCode, String address) {
        int res;
        String sql = "update terminal set address=? where code=? ";
        res = jdbcTemplate.update(sql, address, terminalCode);
        if (res != 0) {
            return new ResponsDTO("Terminal updated ", true);
        } else {
            return new ResponsDTO("There is an error in the information ", false);
        }
    }
    public ResponsDTO changeTerminalStatusByAdmin(String terminalCode, String newTerminalStatus) {
        int res = 0;
        String sql = "update terminal set status=? where code=? ";
        jdbcTemplate.update(sql, newTerminalStatus, terminalCode);
        if (res != 0) {
            return new ResponsDTO("Terminal status changed👌👌👌 ", true);
        } else {
            return new ResponsDTO("There is an error in the information ", false);
        }
    }
    public ResponsDTO deleteTerminal(String terminalCode) {
        int res = 0;
        String sql = "delete from terminal where code=?";
        res=jdbcTemplate.update(sql,terminalCode);
        if (res != 0) {
            return new ResponsDTO("Terminal deleted 👌👌👌 ", true);
        } else {
            return new ResponsDTO("There is an error in the information ", false);
        }
    }


}
