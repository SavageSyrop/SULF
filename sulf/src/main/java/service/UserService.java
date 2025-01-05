package service;

import dao.UserDao;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    private UserDao userDao;

    public User register(String login, String password) {
        User user = new User(login, password);
        return userDao.save(user);
    }

    public void login(String login, String password) {
        User user = userDao.findByLogin(login);
        if (user.getPassword().equals(password)) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            throw new AuthenticationServiceException("Неверный логин или пароль");
        }
    }

    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public User getCurrentUser() {
        return userDao.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public User getUserByLogin(String userLogin) {
        return userDao.findByLogin(userLogin);
    }
}
