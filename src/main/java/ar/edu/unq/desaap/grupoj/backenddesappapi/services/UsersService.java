package ar.edu.unq.desaap.grupoj.backenddesappapi.services;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Transaction;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.TransactionType;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.User;
import ar.edu.unq.desaap.grupoj.backenddesappapi.repositories.UserRepository;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.UserDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.UserDetailsDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.exceptions.UserNotFoundException;
import ar.edu.unq.desaap.grupoj.backenddesappapi.exceptions.UsersException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            return new UserDetailsDTO(userCreated);
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

        return list.stream().map(UserDetailsDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public User find(Integer userId) throws UserNotFoundException {
        return this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
    }

    @Transactional
    public void saveTransaction(Transaction transaction) throws UserNotFoundException {
        if (transaction.getType() == TransactionType.SALE) {
            User seller = this.find(transaction.getSeller().getId());
            addTransactionToUserWithPoints(transaction, seller);

            User buyer = this.find(transaction.getBuyer().getId());
            addTransactionToUser(seller, buyer);
        } else {
            User buyer = this.find(transaction.getBuyer().getId());
            addTransactionToUserWithPoints(transaction, buyer);

            User seller = this.find(transaction.getSeller().getId());
            addTransactionToUser(seller, seller);
        }
    }

    @Transactional
    public void decreaseReputation(Integer userId, Integer points) throws UserNotFoundException {
        User user = this.find(userId);
        user.addReputation(points * -1);
        userRepository.save(user);
    }

    private void addTransactionToUser(User seller, User buyer) {
        seller.addTransaction();
        userRepository.save(buyer);
    }

    private void addTransactionToUserWithPoints(Transaction transaction, User user) {
        user.addTransaction();
        user.addReputation(transaction.calculatePoints());
        userRepository.save(user);
    }

    private User mapUser(UserDTO userDTO) {
        return new User(
                userDTO.name,
                userDTO.lastname,
                userDTO.email,
                userDTO.address,
                new BCryptPasswordEncoder().encode(userDTO.password),
                userDTO.cvu,
                userDTO.wallet
        );
    }

    public User findByEmail(String email) throws UserNotFoundException{
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

}