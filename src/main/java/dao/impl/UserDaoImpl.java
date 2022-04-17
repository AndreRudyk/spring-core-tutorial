package dao.impl;

import dao.UserDao;
import model.User;
import repository.Repository;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;


public class UserDaoImpl implements UserDao {

    private Repository repository;

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public User getUserById(final long userId) {
        return repository.getUsers()
                .get(userId);
    }

    @Override
    public Collection<User> getAllUsers() {
        return repository.getUsers()
                .values();
    }

    @Override
    public User createUser(final User user) {
        long id = getMaxId() + 1;
        user.setId(id);
        repository.getUsers()
                .put(user.getId(), user);
        return repository.getUsers().get(id);

    }

    @Override
    public User updateUser(final User user) {
        return repository.getUsers()
                .computeIfPresent(user.getId(), (k, v) -> user);
    }

    @Override
    public boolean deleteUser(final long userId) {
        return Objects.nonNull(repository.getUsers()
                .remove(userId));
    }

    @Override
    public Long getMaxId() {
        return repository.getUsers().keySet().isEmpty()
                ? 0L
                : Collections.max(repository.getUsers().keySet());
    }
}
