package ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.User;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.Exceptions.UsersException;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.UsersService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Transactional
public class InitServiceInMemory {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${spring.datasource.driverClassName:NONE}")
    private String className;

    @Autowired
    private UsersService usersService;

    @PostConstruct
    public void initialize() throws UsersException {
        if (className.equals("org.h2.Driver")) {
            logger.info("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() throws UsersException {
        User user1 = new User("Nahuel", "Gomez", "nahuelgomez@abc.com", "calle 1123" , "Martin123#","1234567890123456789012","12345678" );
        usersService.save(user1);
        User user2 = new User("User", "Test", "usertest@abc.com", "calle test" , "Test1234#","1234567899923456789012","87654321" );
        usersService.save(user2);
    }
}
