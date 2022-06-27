package ar.edu.unq.desaap.grupoj.backenddesappapi.services;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.*;
import ar.edu.unq.desaap.grupoj.backenddesappapi.repositories.IntentionRepository;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.IntentionDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.IntentionDetailsDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.exceptions.IntentionException;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.exceptions.IntentionNotFoundException;
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
public class IntentionService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private IntentionRepository intentionRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CurrencyService currencyService;

    @Transactional
    public IntentionDetailsDTO save(IntentionDTO intentionDTO) throws IntentionException {
        try {
            Currency currency = currencyService.find(intentionDTO.currencySymbol);

            Intention intention = new Intention(
                    intentionDTO.type,
                    usersService.find(intentionDTO.issuerId),
                    currency,
                    Float.parseFloat(currency.getPrice()),
                    intentionDTO.quantity,
                    new Date(),
                    IntentionStatus.ACTIVE
            );

            Intention intentionCreated = intentionRepository.save(intention);
            logger.info(MessageFormat.format("Intention with id: {0} with type: {1} was created",
                    intentionCreated.getId(),
                    intention.getType())
            );
            return getIntentionDetailsDTO(intentionCreated);
        } catch (Exception e) {
            logger.error(e);
            throw new IntentionException("Intention could not be created");
        }
    }

    @Transactional
    public List<IntentionDetailsDTO> findAllWithStatus(TransactionType type, IntentionStatus status) {
        List<Intention> list = this.intentionRepository.findAllWithTypeAndStatus(type, status);

        return list.stream().map(x -> getIntentionDetailsDTO(x))
                .collect(Collectors.toList());
    }

    private IntentionDetailsDTO getIntentionDetailsDTO(Intention x) {
        return new IntentionDetailsDTO(
                x.getType(),
                x.getIssuer().getEmail(),
                x.getCurrency().getSymbol(),
                x.getPrice(),
                x.getQuantity(),
                x.getDate(),
                x.getStatus());
    }

    public Intention find(Integer id) throws IntentionNotFoundException {
        return this.intentionRepository.findById(id).orElseThrow(() -> new IntentionNotFoundException(id.toString()));
    }
}