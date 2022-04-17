package service.impl;

import dao.UserDao;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.UserService;
import validator.ValidatorService;
import java.util.List;
import java.util.stream.Collectors;


public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ValidatorService validatorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User getUserById(final long userId) {
        LOGGER.info("getUserById method has been called with user id {}", userId);
        return userDao.getUserById(userId);
    }

    @Override
    public User getUserByEmail(final String email) {
        LOGGER.info("getUserByEmail method is being called with email {}", email);
        return userDao.getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getUsersByName(final String name,
                                     final int pageSize,
                                     final int pageNum) {
        LOGGER.info("getUsersByName method has been called");
        return userDao.getAllUsers().stream()
                .filter(user -> user.getName().contains(name))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public User createUser(final User user) {
            LOGGER.info("Calling createUser method with user {}", user);
            return userDao.createUser(user);
    }

    @Override
    public User updateUser(final User user) {
        LOGGER.info("updateUser method has been called with user {}", user);
        validatorService.validateUser(user.getId());
        return userDao.updateUser(user);
    }

    @Override
    public boolean deleteUser(final long userId) {
        LOGGER.info("deleteUser method has been called with user id {}", userId);
        validatorService.validateUser(userId);
        return userDao.deleteUser(userId);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setValidatorService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }
}
