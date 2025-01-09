package org.example.service;

import org.example.dao.UserDao;
import org.example.entity.User;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserDao userDao;

    public User register(String login, String password) {
        User user = new User(login, password);
        try {
            return userDao.save(user);
        } catch (ConstraintViolationException e) {
            throw new RuntimeException("Пользователь с таким логином уже существует", e);
        }

    }

    public void login(String login, String password) {
        Optional<User> userOp = userDao.findByUsername(login);

        if (userOp.isEmpty()) {
            throw new IllegalArgumentException(login + " не зарегистрирован");
        }

        User user = userOp.get();

        if (user.getPassword().equals(password)) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            throw new AuthenticationServiceException("Неверный пароль");
        }
    }

    public void logout() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new RuntimeException("Вы не авторизованы!");
        }
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("Вы не авторизованы!");
        }

        Optional<User> userOp = userDao.findByUsername(authentication.getName());

        if (userOp.isEmpty()) {
            throw new IllegalArgumentException("Вы не авторизованы не авторизованы");
        }

        return userOp.get();
    }

    public User getUserByLogin(String userLogin) {
        Optional<User> userOp = userDao.findByUsername(userLogin);

        if (userOp.isEmpty()) {
            throw new IllegalArgumentException(userLogin + " пользователь не найден");
        }

        return userOp.get();
    }
}
