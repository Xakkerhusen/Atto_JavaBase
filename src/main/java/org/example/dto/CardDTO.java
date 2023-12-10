package org.example.dto;

import lombok.*;
import org.example.enums.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@ToString
//@Component
public class CardDTO {
    private String number;
    private LocalDate exp_date;
    private double balance;
    private Status status;
    private String phone;
    private LocalDateTime created_date;

}
