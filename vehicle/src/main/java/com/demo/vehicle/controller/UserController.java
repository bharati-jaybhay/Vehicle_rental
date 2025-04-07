package com.demo.vehicle.controller;


import com.demo.vehicle.model.User;
import com.demo.vehicle.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            System.out.println("🔍 Attempting login for: " + username);
            System.out.println("🔑 Entered Password: " + password);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("✅ Authentication successful! Redirecting...");
            return "redirect:/form";

        } catch (BadCredentialsException e) {
            System.out.println("❌ Authentication failed: Invalid username or password.");
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

//    @GetMapping("/dashboard")
//    public String showVehicles(){
//        return "dashboard";
//    }

    @GetMapping("/form")
    public String VehiclesForm(){
        return "form";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";  // Make sure register.html or register.jsp exists
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        System.out.println("Register endpoint hit!");
        System.out.println("Received User Data: " + user);

        if (result.hasErrors()) {
            System.out.println("Validation Errors: " + result.getAllErrors());
            return "register";
        }

        System.out.println("Saving user: " + user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash password
        userService.saveUser(user);

        model.addAttribute("successMessage", "Registration successful! Please login.");
        return "redirect:/form";
    }

}