package com.banao.task1.repositories;

import com.banao.task1.domain.User;
import com.banao.task1.exceptions.EtAuthException;

import java.util.List;

public interface UserRepository {
    Integer create (String firstName, String email, String password) throws EtAuthException;
    User findByEmailAndPassword (String email, String password) throws EtAuthException;
    Integer getCountByEmail (String email);
    User findById (Integer userId);

    List<User> getAllUsers();
}
