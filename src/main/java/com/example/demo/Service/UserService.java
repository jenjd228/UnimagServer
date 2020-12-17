package com.example.demo.Service;

import com.example.demo.DTO.OrdersDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Model.Orders;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private ModelMapper modelMapper2;

    @Autowired
    private UserRepository userRepository;

    public String checkBySecureKod(String secureKod) {
        User user = userRepository.findBySecureKod(secureKod);
        if (user!=null){
            return "OK";
        }
        return "NOT_FOUND";
    }

    public Object[] getOrdersList(String secureKod) {
        User user = userRepository.findBySecureKod(secureKod);
        if (user!=null){
            return Objects.requireNonNull(user.getOrdersList()).stream().map(this::convertToDto).toArray();
        }
        return new Object[0];
    }

    private OrdersDTO convertToDto(Orders order) {
        return Objects.isNull(order) ? null : modelMapper2.map(order,OrdersDTO.class);
        /**
         *
         * Настроить дату обьекта ордер и настроить маппинг его списка
         */
    }

    public AbstractMap.SimpleEntry<String, UserDTO> getUser(String secureKod){
        User user = userRepository.findBySecureKod(secureKod.trim());

        if (user==null){
            return new AbstractMap.SimpleEntry<>("ERROR", null);
        }else {
            return new AbstractMap.SimpleEntry<>("OK", new UserDTO(user.getEmail(),user.getFio(),user.getPoints()));
        }
    }

    public String firstUpdate(String email,String password) throws NoSuchAlgorithmException {
        User userFromBD = userRepository.findByEmail(email);
        if (userFromBD!=null){
            return "yes";
        }
        Calendar currentDate = Calendar.getInstance();
        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.add(Calendar.DAY_OF_MONTH,7);

        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String hex = javax.xml.bind.DatatypeConverter.printHexBinary(salt.digest());

        User user = new User();
        user.setPoints(0);
        user.setEmail(email);
        user.setPassword(password);
        user.setRegistrationDate(currentDate.getTime());
        user.setMaxDate(calendarFrom.getTime());
        user.setSecureKod(hex);
        user.setFio("Отредактируйте профиль");
        user.setBirthday("Отредактируйте профиль");

        userRepository.save(user);
        return hex;
    }

    public String userUpdate(String email,String fio,String birthData) {
        User user = userRepository.findByEmail(email);
        if (user!=null){
            user.setFio(fio);
            user.setBirthday(birthData);
            userRepository.save(user);
            return "OK";
        }
        return "USER_NOT_FOUND";
    }

    public String checkByData(String email,String password) throws NoSuchAlgorithmException {
        User user = userRepository.findByEmail(email);
        if (user!=null){
            if (user.getPassword().equals(password)){
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

    public String checkUserForLoginIn(String email,String password) {
        User user = userRepository.findByEmail(email);
        if (user!=null){
            if (user.getPassword().equals(password)){
                return user.getSecureKod();
            }
            return "LOCKED";
        }
        return "NOT_FOUND";
    }


}
