package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.User;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories.IUserRepository;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.Exceptions.UsersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private IUserRepository userRepository;

    public User save(User newUser) throws UsersException {
        if(!newUser.isValidName()){
            throw new UsersException("Invalid Name length");
        }
        if(!newUser.isValidLastName()){
            throw new UsersException("Invalid lastname length");
        }
        if(!newUser.isValidEmail()){
            throw new UsersException("Invalid Email");
        }
        if(!newUser.isValidPassword()){
            throw new UsersException("Invalid Password");
        }
        if(!newUser.isValidAddress()){
            throw new UsersException("Invalid Address length");
        }
        if(!newUser.isValidWallet()){
            throw new UsersException("Invalid Wallet length");
        }
        if(!newUser.isValidCVU()){
            throw new UsersException("Invalid CVU length");
        }
        return userRepository.save(newUser);
    }
}
