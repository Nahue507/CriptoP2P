package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.User;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories.UserRepository;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.dtos.UserDTO;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.dtos.UserDetailsDTO;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.UserNotFoundException;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.UsersException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDetailsDTO save(UserDTO userDTO) throws UsersException {
        try {
            User user = mapUser(userDTO);
            user.testIsValid();
            User userCreated = userRepository.save(user);
            logger.info(MessageFormat.format("User with id: {0} was created", user.getId()));
            return mapUserDetailsDTO(userCreated);
        } catch (UsersException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new UsersException("User could not be created");
        }
    }

    @Transactional
    public List<UserDetailsDTO> findAll() {
        List<User> list = (List<User>) this.userRepository.findAll();

        return list.stream().map(x -> mapUserDetailsDTO(x))
                .collect(Collectors.toList());
    }

    private User mapUser(UserDTO userDTO) {
        return new User(
                userDTO.name,
                userDTO.lastname,
                userDTO.email,
                userDTO.address,
                userDTO.password,
                userDTO.cvu,
                userDTO.wallet
        );
    }

    private UserDetailsDTO mapUserDetailsDTO(User x) {
        return new UserDetailsDTO(
                x.getId(),
                x.getName(),
                x.getLastname(),
                x.getEmail(),
                x.getAddress(),
                x.getCvu(),
                x.getWallet(),
                x.getReputation());
    }

    @Transactional
    public User find(Integer userId) throws UserNotFoundException {
        return this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
    }
}