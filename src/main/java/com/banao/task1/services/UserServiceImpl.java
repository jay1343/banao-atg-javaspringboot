package com.banao.task1.services;

import com.banao.task1.domain.User;
import com.banao.task1.exceptions.EtAuthException;
import com.banao.task1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if (null != email) email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String email, String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^\\\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}\\\\b$");
        if (null != email) email = email.toLowerCase();
        if (pattern.matcher(email).matches())
            throw new EtAuthException("Invalid email format");
        Integer count = userRepository.getCountByEmail(email);
        if (count > 0)
            throw new EtAuthException("Email already in use");
        Integer userId = userRepository.create(firstName, email, password);

        return userRepository.findById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
