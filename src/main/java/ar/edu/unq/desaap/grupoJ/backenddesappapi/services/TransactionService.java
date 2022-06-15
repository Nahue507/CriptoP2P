package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Transaction;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories.TransactionRepository;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.TransactionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
public class TransactionService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction save(Transaction transaction) throws TransactionException {
        try {
            Transaction transactionCreated = transactionRepository.save(transaction);
            logger.info(MessageFormat.format("Transaction with id: {0} was created", transactionCreated.getId()));
            return transactionCreated;
        } catch (Exception e) {
            logger.error(e);
            throw new TransactionException("Transaction could not be created");
        }
    }

    @Transactional
    public List<Transaction> findAll() {
        return (List<Transaction>) this.transactionRepository.findAll();
    }
}