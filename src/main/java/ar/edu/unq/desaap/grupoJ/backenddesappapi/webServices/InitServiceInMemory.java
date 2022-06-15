package ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.User;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.CurrencyService;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.CurrencyNotFoundException;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.UsersException;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.UsersService;
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
    public void initialize() throws UsersException, CurrencyNotFoundException {
        if (className.equals("org.h2.Driver")) {
            logger.info("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() throws UsersException, CurrencyNotFoundException {
        createCurrencies();
        createUsers();
    }

    private void createUsers() throws UsersException, CurrencyNotFoundException {
        Currency alice = currencyService.find("ALICEUSDT");
        Currency matic = currencyService.find("MATICUSDT");

        User user1 = new User("Nahuel", "Gomez", "nahuelgomez@abc.com", "calle 1123" , "Martin123#","1234567890123456789012","12345678" );
        user1.addCurrency(alice, 1000);
        usersService.save(user1);

        User user2 = new User("User", "Test", "usertest@abc.com", "calle test" , "Test1234#","1234567899923456789012","87654321" );
        user2.addCurrency(alice, 2000);
        user2.addCurrency(matic, 500);
        usersService.save(user2);
    }

    private void createCurrencies() {
        List<String> symbols = currencyService.getAllCurrencySymbols();

        for(String crypto : symbols){
            currencyService.save(new Currency(crypto,"0"));
        }
    }
}
