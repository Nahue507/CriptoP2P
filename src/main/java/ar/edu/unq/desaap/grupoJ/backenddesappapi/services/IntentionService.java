package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Intention;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.IntentionStatus;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories.IntentionRepository;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.IntentionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Service
public class IntentionService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private IntentionRepository intentionRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CurrencyService currencyService;

    @Transactional
    public Intention save(Intention intention) throws IntentionException {
        try {
            Currency currency = currencyService.find(intention.getCurrency().getSymbol());

            intention.setIssuer(usersService.find(intention.getIssuer().getId()));
            intention.setCurrency(currency);
            intention.setPrice(Float.parseFloat(currency.getPrice()));
            intention.setDate(new Date());
            intention.setStatus(IntentionStatus.Active);

            Intention intentionCreated = intentionRepository.save(intention);
            logger.info(MessageFormat.format("Intention with id: {0} with type: {1} was created",
                    intentionCreated.getId(),
                    intention.getType())
            );
            return intentionCreated;
        } catch (Exception e) {
            logger.error(e);
            throw new IntentionException("Intention could not be created");
        }
    }

    @Transactional
    public List<Intention> findAll() {
        return (List<Intention>) this.intentionRepository.findAll();
    }
}