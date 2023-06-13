package pl.edu.pb.wi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.exceptions.RegularException;
import pl.edu.pb.wi.repository.UserRepository;
import pl.edu.pb.wi.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws RegularException {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new RegularException("User not found"));
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }
}
