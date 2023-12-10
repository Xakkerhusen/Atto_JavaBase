package org.example.dto;

import lombok.*;
import org.example.enums.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@ToString
//@Component
public class TerminalDTO {
//    code,address,status,created_date
    private String code;
    private String address;
    private Status status;
    private LocalDate createdDate;

}
