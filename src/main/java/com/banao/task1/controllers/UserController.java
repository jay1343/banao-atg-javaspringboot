package com.banao.task1.controllers;

import com.banao.task1.Constants;
import com.banao.task1.domain.User;
import com.banao.task1.exceptions.EtAuthException;
import com.banao.task1.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers (HttpServletRequest request,
                             HttpServletResponse response,
                             Model model) {
        HttpSession session = request.getSession();

        if (session != null) {
            Object existingAttribute = session.getAttribute("token");
            if (existingAttribute != null) {
                if (!validateToken(existingAttribute.toString()))
                    throw new EtAuthException("user is not authorized to access this URL.");
            } else {
                throw new EtAuthException("user is not authorized to access this URL.");
            }
        }
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
    @GetMapping("/register")
    public String registerUser (Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }
    @GetMapping("/login")
    public String loginUser (HttpServletRequest request,
                             HttpServletResponse response,
                             Model model) {
        HttpSession session = request.getSession();

        if (session != null) {
            Object tokenAttribute = session.getAttribute("token");
            if (tokenAttribute != null) {
                session.removeAttribute("token");
            }
            Object userAttribute = session.getAttribute("user");
            if (userAttribute != null) {
                session.removeAttribute("user");
            }
        }

        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }
    @GetMapping("/home")
    public String home (HttpServletRequest request,
                        Model model) {
        HttpSession session = request.getSession();

        if (session != null) {
            Object existingAttribute = session.getAttribute("token");
            if (existingAttribute != null) {
                if (!validateToken(existingAttribute.toString()))
                    throw new EtAuthException("user is not authorized to access this URL.");
            } else {
                throw new EtAuthException("user is not authorized to access this URL.");
            }
        }
        User user = (User) session.getAttribute("user");

        model.addAttribute("user", user);
        return "home";
    }
    private static boolean validateToken(String token) {
        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(Constants.API_SECRET_KEY)
                .build()
                .parseClaimsJws(token);
        return true;
    }

}
