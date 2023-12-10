package org.example.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class DatabaseUtil {
    @Autowired
    private JdbcTemplate jdbcTemplatee;

    public void createProfileTable() {
        String sql = "   create table  if not exists  profile(" +
                "                name varchar not null ," +
                "                surname varchar not null ," +
                "                phone varchar(13) primary key," +
                "                password varchar," +
                "                created_date timestamp default now()," +
                "                status varchar default  'ACTIVE'," +
                "                profile_role varchar" +
                ");";
       jdbcTemplatee.execute(sql);
    }

    public void createCardTable() {
        String sql = "  create table  if not exists  card(" +
                "                number varchar(16)primary key ," +
                "                exp_date date," +
                "                balance double precision," +
                "                status varchar default  'NO_ACTIVE'," +
                "                phone varchar(13) references profile (phone)," +
                "                created_date timestamp default now()" +
                ");";
        jdbcTemplatee.execute(sql);
    }

    public void createTerminalTable() {
        String sql = "   create table if not exists terminal(" +
                "        code varchar  primary key," +
                "        address varchar ,   " +
                "        status varchar default 'ACTIVE'," +
                "        created_date timestamp default now()" +
                "      );";
        jdbcTemplatee.execute(sql);
    }

    public void createTransactionTable() {
        String sql = "   create table if not exists transactions(" +
                "        card_number_user varchar(16)," +
                "        amount double precision,   " +
                "        terminal_code varchar ," +
                "        transaction_type varchar," +
                "        transaction_time timestamp default now()" +
                "      );";
        jdbcTemplatee.execute(sql);
    }
}

