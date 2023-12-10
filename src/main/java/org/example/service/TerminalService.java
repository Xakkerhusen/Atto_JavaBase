package org.example.service;

import lombok.Setter;
import org.example.dto.ResponsDTO;
import org.example.dto.TerminalDTO;
import org.example.enums.Status;
import org.example.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Service
public class TerminalService {
    @Autowired
    private TerminalRepository terminalRepository;

    public void creatTerminal(String terminalCode, String terminalAddress) {
        ResponsDTO responsDTO = terminalRepository.creatTerminal(terminalCode, terminalAddress);
        if (responsDTO.success()) {
            System.out.println(responsDTO.message());
        } else {
            System.out.println(responsDTO.message());
        }
    }

    public List<TerminalDTO> showTerminalList() {
        List<TerminalDTO> terminalList =terminalRepository.getTerminalList();
        return terminalList;
    }


    public void updateTerminal(String terminalCode, String address) {
        ResponsDTO responsDTO = terminalRepository.updateTerminal(terminalCode, address);
        if (responsDTO.success()) {
            System.out.println(responsDTO.message());
        } else {
            System.out.println(responsDTO.message());
        }
    }

    public void changeTerminalStatusByAdmin(String terminalCode, String newTerminalStatus) {
        if (newTerminalStatus.equals("ACTIVE") || newTerminalStatus.equals("NO_ACTIVE") || newTerminalStatus.equals("BLOCKED")) {
            List<TerminalDTO> terminalList =terminalRepository.getTerminalList();
            ResponsDTO result = null;
            if (terminalList == null) {
                System.out.println("Terminal if not exist!!!");
                return;
            } else {
                for (TerminalDTO terminalDTO : terminalList) {
                    if (terminalDTO.getCode().equals(terminalCode)) {
                        result =terminalRepository.changeTerminalStatusByAdmin(terminalCode, newTerminalStatus);
                    }
                }

                if (result == null) {
                    System.out.println(" Wrong terminal code!!! ");
                } else if (result.success()) {
                    System.out.println(result.message());
                } else {
                    System.out.println(result.message());
                }
            }
        } else {
            System.out.println("Status (ACTIVE or NO_ACTIVE or BLOCKED) be written in format!!!");
        }


    }

    public void deleteTerminal(String terminalCode) {
        List<TerminalDTO> terminalList =terminalRepository.getTerminalList();
        ResponsDTO result = null;
        if (terminalList == null) {
            System.out.println("Terminal if not exist!!!");
        } else {
            for (TerminalDTO terminalDTO : terminalList) {
                if (terminalDTO.getCode().equals(terminalCode)) {
                    result =terminalRepository.deleteTerminal(terminalCode);
                }
            }

            if (result == null) {
                System.out.println(" Wrong terminal code!!! ");
            } else if (result.success()) {
                System.out.println(result.message());
            } else {
                System.out.println(result.message());
            }
        }

    }

    public boolean chackTerminalCode(String terminalCode) {
        List<TerminalDTO> terminalList =terminalRepository.getTerminalList();
        for (TerminalDTO terminalDTO : terminalList) {
            if (terminalDTO.getCode().equals(terminalCode) && terminalDTO.getStatus().equals(Status.ACTIVE)) {
                return true;
            }
        }
        return false;
    }


}
