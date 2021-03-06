package com.example.demo.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    UserService(PasswordEncoder passwordEncoder,UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public String checkBySecureKod(String secureKod) {
        User user = userRepository.findBySecureKod(secureKod);
        if (user != null) {
            return "OK";
        }
        return "NOT_FOUND";
    }

    public AbstractMap.SimpleEntry<String, UserDTO> getUser(String secureKod) {
        User user = userRepository.findBySecureKod(secureKod.trim());

        if (user == null) {
            return new AbstractMap.SimpleEntry<>("ERROR", null);
        } else {
            return new AbstractMap.SimpleEntry<>("OK", new UserDTO(user.getEmail(), user.getFio(), user.getPoints()));
        }
    }

    public String firstUpdate(String email, String password, String fio, String birthData) throws NoSuchAlgorithmException {
        User userFromBD = userRepository.findByEmail(email);
        if (userFromBD != null) {
            return "yes";
        }
        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.add(Calendar.DAY_OF_MONTH, 7);

        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String hex = javax.xml.bind.DatatypeConverter.printHexBinary(salt.digest());

        User user = new User();
        user.setPoints(0);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        /**
         * Переделать в лонг с дейт
         * user.setRegistrationDate(currentDate.getTime());
         *         user.setMaxDate(calendarFrom.getTime());
         */

        user.setSecureKod(hex);
        user.setFio("Отредактируйте профиль");
        user.setBirthday("Отредактируйте профиль");
        user.setFio(fio);
        user.setBirthday(birthData);

        userRepository.save(user);
        return hex;
    }


    public String checkByData(String email, String password) throws NoSuchAlgorithmException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                MessageDigest salt = MessageDigest.getInstance("SHA-256");
                salt.update(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
                String hex = javax.xml.bind.DatatypeConverter.printHexBinary(salt.digest());

                user.setSecureKod(hex);
                userRepository.save(user);

                return hex;
            }
        }
        return "NOT_FOUND";
    }

    public String checkUserForLoginIn(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(password,user.getPassword())) {
                return user.getSecureKod();
            }
            return "LOCKED";
        }
        return "NOT_FOUND";
    }

    public String userUpdate(String fio,String secureKod){
        User user = userRepository.findBySecureKod(secureKod);

        if (user == null){
            return "USER_NOT_FOUND";
        }

        user.setFio(fio);

        userRepository.save(user);

        return "OK";
    }


}
