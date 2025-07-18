package ru.job4j.cinemajob.service.impl;

import org.springframework.stereotype.Service;
import ru.job4j.cinemajob.model.User;
import ru.job4j.cinemajob.repository.UserRepository;
import ru.job4j.cinemajob.service.UserService;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public boolean deleteById(int id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }
}
