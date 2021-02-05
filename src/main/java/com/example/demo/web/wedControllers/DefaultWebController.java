package com.example.demo.web.wedControllers;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.web.model.AuthForm;
import com.example.demo.web.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Controller
public class DefaultWebController {

    private final UserRepository userRepository;

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    DefaultWebController(PasswordEncoder passwordEncoder, AuthService authService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostConstruct()
    public void moderInit() {
        User user = new User();
        user.setEmail("main_admin");
        user.setPassword(passwordEncoder.encode("195612195512"));
        userRepository.save(user);
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String index() {
        if (!authService.check()){
            return "redirect:/login";
        }
        return "admin";
    }

    @GetMapping("/mainStatistic")
    public String mainStatistic() {
        if (!authService.check()){
            return "redirect:/login";
        }
        return "frameGetMainStatistic";
    }

    @GetMapping("/productStatistic")
    public String productStatistic() {
        if (!authService.check()){
            return "redirect:/login";
        }
        return "frameGetProductStatistic";
    }

    @GetMapping("/partnerProgramStatistic")
    public String partnerProgramStatistic() {
        if (!authService.check()){
            return "redirect:/login";
        }
        return "frameGetPartnerProgramStatistic";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("authForm", new AuthForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("authForm") AuthForm authForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        if (!authService.auth(authForm)){
            redirectAttributes.addFlashAttribute("errorPasswordOrLogin","Неправильный логин или пароль");
            return "redirect:/login";
        }

        return "admin";
    }

}
