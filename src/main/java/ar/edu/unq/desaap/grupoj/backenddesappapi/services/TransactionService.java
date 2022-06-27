package ar.edu.unq.desaap.grupoj.backenddesappapi.services;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.*;
import ar.edu.unq.desaap.grupoj.backenddesappapi.repositories.TransactionRepository;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.exceptions.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private IntentionService intentionService;

    @Transactional
    public Transaction saveBuyTransaction(Transaction transaction)
            throws TransactionException, CurrencyNotFoundException,
            UserNotFoundException, IntentionNotFoundException,
            SameUserException, PriceIncreasedException {
        Currency currency = currencyService.find(transaction.getCurrency().getSymbol());
        User buyer = usersService.find(transaction.getBuyer().getId());
        Intention saleIntention = intentionService.find(transaction.getSaleIntention().getId());
        Intention buyIntention = mapIntention(currency, buyer, saleIntention);

        try {
            mapTransaction(transaction, currency, buyer, saleIntention, buyIntention);

            if(transaction.shouldBeCancelled()){
                transaction.setStatus(TransactionStatus.CANCELLED);
                buyIntention.setStatus(IntentionStatus.CANCELLED);
                saleIntention.setStatus(IntentionStatus.CANCELLED);
            } else {
                transaction.setStatus(TransactionStatus.TO_BE_CONFIRMED);
            }

            Transaction transactionCreated = transactionRepository.save(transaction);

            logger.info(MessageFormat.format("Transaction with id: {0} of type: {1} was created",
                    transactionCreated.getId(),
                    transactionCreated.getType()));

            if(transaction.sameUser()){
                throw new SameUserException("Same seller and buyer is not allowed");
            }

            if(transaction.priceIncreased()){
                throw new PriceIncreasedException("The price increased and the Transaction is Cancelled");
            }
            return transactionCreated;
        }
        catch (SameUserException | PriceIncreasedException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new TransactionException("Transaction could not be created");
        }
    }

    private void mapTransaction(Transaction transaction, Currency currency, User buyer, Intention saleIntention, Intention buyIntention) {
        transaction.setType(TransactionType.BUY);
        transaction.setCurrency(currency);
        transaction.setBuyer(buyer);
        transaction.setSeller(saleIntention.getIssuer());
        transaction.setSaleIntention(saleIntention);
        transaction.setBuyIntention(buyIntention);
        transaction.setPrice(Float.parseFloat(currency.getPrice()));
        transaction.setQuantity(buyIntention.getQuantity());
        transaction.setDate(new Date());
    }

    private Intention mapIntention(Currency currency, User buyer, Intention saleIntention) {
        Intention buyIntention = new Intention();
        buyIntention.setType(TransactionType.BUY);
        buyIntention.setIssuer(buyer);
        buyIntention.setCurrency(currency);
        buyIntention.setPrice(saleIntention.getPrice());
        buyIntention.setQuantity(saleIntention.getQuantity());
        buyIntention.setDate(new Date());
        buyIntention.setStatus(IntentionStatus.ACTIVE);
        return buyIntention;
    }

    @Transactional
    public List<Transaction> findAll() {
        return (List<Transaction>) this.transactionRepository.findAll();
    }

    public Transaction find(Integer id) throws TransactionNotFoundException {
        return this.transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id.toString()));
    }

    @Transactional
    public void acceptTransaction(Integer userId, Integer transactionId) throws TransactionNotFoundException, UserNotFoundException {
        User user = this.usersService.find(userId);
        Transaction transaction = find(transactionId);

        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.getSaleIntention().setStatus(IntentionStatus.COMPLETED);
        transaction.getBuyIntention().setStatus(IntentionStatus.COMPLETED);

        this.transactionRepository.save(transaction);
        logger.info(MessageFormat.format("Transaction with id: {0} was accepted", transaction.getId()));
    }

    @Transactional
    public void cancelTransaction(Integer userId, Integer transactionId) throws TransactionNotFoundException, UserNotFoundException {
        User user = this.usersService.find(userId);
        Transaction transaction = find(transactionId);

        transaction.setStatus(TransactionStatus.CANCELLED);
        transaction.getSaleIntention().setStatus(IntentionStatus.CANCELLED);
        transaction.getBuyIntention().setStatus(IntentionStatus.CANCELLED);

        this.transactionRepository.save(transaction);
    }
}