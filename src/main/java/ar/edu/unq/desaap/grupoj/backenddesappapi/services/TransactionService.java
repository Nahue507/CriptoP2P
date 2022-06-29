package ar.edu.unq.desaap.grupoj.backenddesappapi.services;

import ar.edu.unq.desaap.grupoj.backenddesappapi.exceptions.*;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.*;
import ar.edu.unq.desaap.grupoj.backenddesappapi.repositories.TransactionRepository;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.TransactionBuyDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.TransactionDetailsDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private QuotationService quotationService;

    @Transactional
    public TransactionDetailsDTO saveBuyTransaction(TransactionBuyDTO transactionDTO)
            throws TransactionException, CurrencyNotFoundException,
            UserNotFoundException, IntentionNotFoundException,
            SameUserException, PriceIncreasedException {
        User buyer = usersService.find(transactionDTO.buyerId);
        Intention saleIntention = intentionService.find(transactionDTO.saleIntentionId);
        Currency currency = currencyService.find(saleIntention.getCurrency().getSymbol());
        Intention buyIntention = mapIntention(currency, buyer, saleIntention);

        try {
            Transaction transaction = mapBuyTransaction(currency, buyer, saleIntention, buyIntention);

            if (transaction.shouldBeCancelled()) {
                transaction.setStatus(TransactionStatus.CANCELLED);
                buyIntention.setStatus(IntentionStatus.CANCELLED);
                saleIntention.setStatus(IntentionStatus.CANCELLED);
            } else {
                transaction.setStatus(TransactionStatus.TO_BE_CONFIRMED);
                buyIntention.setStatus(IntentionStatus.IN_PROCESS);
                saleIntention.setStatus(IntentionStatus.IN_PROCESS);
            }

            Transaction transactionCreated = transactionRepository.save(transaction);

            logger.info(MessageFormat.format("Transaction with id: {0} of type: {1} was created",
                    transactionCreated.getId(),
                    transactionCreated.getType()));

            if (transaction.sameUser()) {
                throw new SameUserException("Same seller and buyer is not allowed");
            }

            if (transaction.priceIncreased()) {
                throw new PriceIncreasedException("The price increased and the Transaction is Cancelled");
            }

            return new TransactionDetailsDTO(transactionCreated);
        } catch (SameUserException | PriceIncreasedException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new TransactionException("Transaction could not be created");
        }
    }

    public List<TransactionDetailsDTO> findAll() {
        List<Transaction> list = (List<Transaction>) this.transactionRepository.findAll();

        return list.stream().map(TransactionDetailsDTO::new)
                .collect(Collectors.toList());
    }

    public Transaction find(Integer id) throws TransactionNotFoundException {
        return this.transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id.toString()));
    }

    @Transactional
    public void acceptTransaction(Integer userId, Integer transactionId) throws TransactionNotFoundException, UserNotFoundException, TransactionProcessException {
        Transaction transaction = find(transactionId);

        transaction.checkUserCanProcess(userId);
        transaction.checkCanBeProcessed();

        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.getSaleIntention().setStatus(IntentionStatus.COMPLETED);
        transaction.getBuyIntention().setStatus(IntentionStatus.COMPLETED);
        transaction.setDateProcessed(new Date());

        String dollarQuotation = quotationService.getDollarQuotation();

        transaction.setTotalARS(quotationService.convert(transaction.getTotalUSD(), dollarQuotation));

        this.transactionRepository.save(transaction);
        logger.info(MessageFormat.format("Transaction with id: {0} was accepted", transaction.getId()));

        this.usersService.saveTransaction(transaction);
    }

    @Transactional
    public void cancelTransaction(Integer userId, Integer transactionId) throws TransactionNotFoundException, UserNotFoundException, TransactionProcessException {
        Transaction transaction = find(transactionId);

        transaction.checkUserCanProcess(userId);

        transaction.setStatus(TransactionStatus.CANCELLED);
        transaction.getSaleIntention().setStatus(IntentionStatus.CANCELLED);
        transaction.getBuyIntention().setStatus(IntentionStatus.CANCELLED);

        this.transactionRepository.save(transaction);
        logger.info(MessageFormat.format("Transaction with id: {0} was cancelled", transaction.getId()));

        this.usersService.decreaseReputation(userId, 20);
    }

    private Transaction mapBuyTransaction(Currency currency, User buyer, Intention saleIntention, Intention buyIntention) {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.BUY);
        transaction.setCurrency(currency);
        transaction.setBuyer(buyer);
        transaction.setSeller(saleIntention.getIssuer());
        transaction.setSaleIntention(saleIntention);
        transaction.setBuyIntention(buyIntention);
        transaction.setPriceUSD(Float.parseFloat(currency.getPrice()));
        transaction.setQuantity(buyIntention.getQuantity());
        transaction.setDateCreated(new Date());
        transaction.setTotalUSD(Float.parseFloat(currency.getPrice()) * buyIntention.getQuantity());
        return transaction;
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
}