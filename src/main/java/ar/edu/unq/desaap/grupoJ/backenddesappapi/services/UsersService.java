package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.User;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories.IUserRepository;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.Exceptions.UsersException;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private IUserRepository userRepository;

    public User save(User newUser) throws UsersException {
        if(!newUser.isValidCVU()){
            throw new UsersException("Invalid CVU length");
        }
        return userRepository.save(newUser);
    }
}
