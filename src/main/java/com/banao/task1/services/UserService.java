package com.banao.task1.services;

import com.banao.task1.domain.User;
import com.banao.task1.exceptions.EtAuthException;

import java.util.List;

public interface UserService {
    User validateUser (String email, String password) throws EtAuthException;
    User registerUser (String firstName, String email, String password) throws EtAuthException;
    List<User> getAllUsers ();
}
