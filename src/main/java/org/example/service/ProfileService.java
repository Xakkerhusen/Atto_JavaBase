package org.example.service;

import lombok.Setter;
import org.example.controller.Appl;
import org.example.dto.ProfileDTO;
import org.example.enums.Status;
import org.example.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Setter
@Service
public class ProfileService {
//    ProfileRepository profileRepository=new ProfileRepository();

    private  ProfileRepository profileRepository;

//ProfileRepository profileRepository;//spring da tajriba oxshamadi

    public ProfileDTO login(ProfileDTO profileDTO) {
        ProfileDTO profile = /*Appl.applicationContext.getBean("profileRepository", ProfileRepository.class)*/profileRepository.login(profileDTO);
        return profile;
    }

    public boolean registration(ProfileDTO profile) {

        boolean result = /*Appl.applicationContext.getBean("profileRepository", ProfileRepository.class)*/profileRepository.registration(profile);
        return result;
    }


    public void showProfileList() {
        List<ProfileDTO> profiles = /*Appl.applicationContext.getBean("profileRepository", ProfileRepository.class)*/profileRepository.getProfileList();
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
