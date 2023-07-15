package com.banao.task1.resources;

import com.banao.task1.Constants;
import com.banao.task1.domain.User;
import com.banao.task1.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public ResponseEntity<String> loginUser (HttpServletRequest request,
                                             HttpServletResponse response,
                                             User model) throws IOException {
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

        String email = model.getEmail();
        String password = model.getPassword();

        User user = userService.validateUser(email, password);

        Map<String, String> token = gerateJWTToken(user);

        session.setAttribute("token", token.get("token"));
        session.setAttribute("user", user);

        String requestUrl = request.getRequestURL().toString();
        String redirectUrl = requestUrl.replace(request.getServletPath(), "") + "/home";
        if ("ADMIN".equals(user.getRole()))
            redirectUrl = requestUrl.replace(request.getServletPath(), "") + "/users";

        response.sendRedirect(redirectUrl);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser (HttpServletRequest request,
                                                HttpServletResponse response,
                                                User model) throws IOException {
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

        String firstName = model.getFirstName();
        String email = model.getEmail();
        String password = model.getPassword();

        User user = userService.registerUser(firstName, email, password);


        Map<String, String> token = gerateJWTToken(user);

        session.setAttribute("token", token.get("token"));
        session.setAttribute("user", user);

        String requestUrl = request.getRequestURL().toString();
        String redirectUrl = requestUrl.replace(request.getServletPath(), "") + "/home";
        if ("ADMIN".equals(user.getRole()))
            redirectUrl = requestUrl.replace(request.getServletPath(), "") + "/users";

        response.sendRedirect(redirectUrl);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Map<String, String> gerateJWTToken (User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("role", user.getRole())
                .compact();

        Map<String, String> map = new HashMap<>();
        map.put("token",token);

        return map;
    }
}
