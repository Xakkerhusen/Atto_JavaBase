package org.example.repository;

import lombok.Setter;
import org.example.dto.ProfileDTO;
import org.example.enums.ProfileRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Setter
@Repository
public class ProfileRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ProfileDTO> login(String phoneNumber, String password) {
        List<ProfileDTO> res;
        String sql = "select * from profile where phone=? and password=?";
        res=jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class),phoneNumber,password);
        return res;
    }


    public boolean registration(String name, String surname, String phone, String password, ProfileRole profileRole) {
        int res;
        String sql = "insert into profile(name,surname,phone,password,profile_role) values (?,?,?,?,?)";
        res = jdbcTemplate.update(sql, name, surname, phone, password, profileRole.name());
        return res != 0;
    }

    public List<ProfileDTO> getProfileList() {
        List<ProfileDTO> profiles;
        String sql = "select * from profile";
        profiles = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class));
        return profiles;
    }


}
