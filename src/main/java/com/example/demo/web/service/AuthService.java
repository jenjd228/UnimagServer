package com.example.demo.web.service;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.confic.SessionControl;
import com.example.demo.web.model.AuthForm;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashMap;

@Service
public class AuthService {

    private final Logger logger = Logger.getLogger(AuthService.class);

    private HashMap<String, Integer> currentUsers;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    AuthService(PasswordEncoder passwordEncoder,UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        currentUsers = new HashMap<>();
    }

    public boolean auth(AuthForm authForm){
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        if (currentUsers.containsKey(sessionId)){
            return true;
        }

        User user = userRepository.findByEmail(authForm.getLogin());
        if (user!=null && passwordEncoder.matches(authForm.getPassword(),user.getPassword())){
            currentUsers.put(sessionId,user.getId());
            return true;
        }

        return false;
    }

    public boolean check() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        return currentUsers.containsKey(sessionId);
    }

    public void removeAllMembers() {
        currentUsers.clear();
        logger.info("current users size list "+currentUsers.size());
    }
}
