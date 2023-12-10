package org.example.service;

import lombok.Setter;
import org.example.dto.ProfileDTO;
import org.example.enums.ProfileRole;
import org.example.enums.Status;
import org.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Setter
@Service
public class ProfileService {
@Autowired
    private  ProfileRepository profileRepository;

        public List<ProfileDTO> login(String phoneNumber, String password) {
        List<ProfileDTO> result = profileRepository.login(phoneNumber, password);
        return result;
    }

    public boolean registration(String name, String surname, String phone, String password, ProfileRole profileRole) {

        boolean result = profileRepository.registration(name,surname,phone,password,profileRole);
        return result;
    }


    public void showProfileList() {
        List<ProfileDTO> profiles = profileRepository.getProfileList();
        if (profiles != null) {
            for (ProfileDTO profile : profiles) {
                if (profile.getStatus().equals(Status.ACTIVE)) {
                    System.out.println(profile);
                }else if (profile.getStatus().equals(Status.NO_ACTIVE)) {
                    System.out.println(profile);
                }else if (profile.getStatus().equals(Status.BLOCKED)) {
                    System.out.println(profile);
                }
            }
        }else {
            System.out.println("We have not any profiles");
        }
    }



}
