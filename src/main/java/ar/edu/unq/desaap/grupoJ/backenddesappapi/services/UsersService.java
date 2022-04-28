package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.User;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private IUserRepository userRepository;

    public User save(User newUser) {
        return userRepository.save(newUser);
    }
}
