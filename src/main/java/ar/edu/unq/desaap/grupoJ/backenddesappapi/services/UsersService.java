package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.User;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories.UserRepository;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.UserNotFoundException;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.UsersException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
@Service
public class UsersService {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User save(User newUser) throws UsersException {
        try {
            newUser.testIsValid();
            User userCreated = userRepository.save(newUser);
            logger.info(MessageFormat.format("User with id: {0} was created", newUser.getId()));
            return userCreated;
        } catch (UsersException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new UsersException("User could not be created");
        }
    }
    @Transactional
    public List<User> findAll() {
        return (List<User>) this.userRepository.findAll();
    }

    @Transactional
    public User find(Integer userId) throws UserNotFoundException {
        return this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
    }
}