package service;

import dao.UserDao;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserDao userDao;

    public User register(String login, String password) {
        User user = new User(login, password);
        return userDao.save(user);
    }

    public void login(String login, String password) {
        Optional<User> userOp = userDao.findByLogin(login);

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
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public User getCurrentUser() {
        Optional<User> userOp =  userDao.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        if (userOp.isEmpty()) {
            throw new IllegalArgumentException("Вы не авторизованы не авторизованы");
        }

        return userOp.get();
    }

    public User getUserByLogin(String userLogin) {
        Optional<User> userOp =  userDao.findByLogin(userLogin);

        if (userOp.isEmpty()) {
            throw new IllegalArgumentException(userLogin + " пользователь не найден");
        }

        return userOp.get();
    }
}
