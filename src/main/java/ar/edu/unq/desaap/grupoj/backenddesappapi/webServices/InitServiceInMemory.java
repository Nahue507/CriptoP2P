package ar.edu.unq.desaap.grupoj.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.CurrencyService;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.UserDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.exceptions.CurrencyNotFoundException;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.exceptions.UsersException;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.UsersService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional
public class InitServiceInMemory {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${spring.datasource.driverClassName:NONE}")
    private String className;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CurrencyService currencyService;

    @PostConstruct
    public void initialize() throws UsersException {
        if (className.equals("org.h2.Driver")) {
            logger.info("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() throws UsersException {
        createCurrencies();
        createUsers();
    }

    private void createUsers() throws UsersException {
        UserDTO user1 = new UserDTO("Nahuel", "Gomez", "nahuelgomez@abc.com", "calle 1123" , "Martin123#","1234567890123456789012","12345678" );
        usersService.save(user1);

        UserDTO user2 = new UserDTO("User", "Test", "usertest@abc.com", "calle test" , "Test1234#","1234567899923456789012","87654321" );
        usersService.save(user2);
    }

    private void createCurrencies() {
        List<String> symbols = currencyService.getAllCurrencySymbols();

        for(String crypto : symbols){
            currencyService.save(new Currency(crypto,"0"));
        }
    }
}
