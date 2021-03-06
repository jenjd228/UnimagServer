package com.example.demo.Service;

import com.example.demo.Model.User;
import com.example.demo.Model.UserPartner;
import com.example.demo.Repository.UserPartnerRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartnerService {

    private final UserPartnerRepository userPartnerRepository;

    private final UserRepository userRepository;

    PartnerService(UserPartnerRepository userPartnerRepository, UserRepository userRepository) {
        this.userPartnerRepository = userPartnerRepository;
        this.userRepository = userRepository;
    }

    public boolean isSubscribe(String secureKod) {
        User user = userRepository.findBySecureKod(secureKod);
        if (user != null) {
            List<UserPartner> list = userPartnerRepository.findAllByUserId(user.getId());
            for (UserPartner userPartner : list) {
                if (userPartner.getUserId().equals(user.getId()) && userPartner.getSubscriptionEndDate().isAfter(LocalDateTime.now())) {
                    return true;
                }
            }
        }
        return false;
    }
}
