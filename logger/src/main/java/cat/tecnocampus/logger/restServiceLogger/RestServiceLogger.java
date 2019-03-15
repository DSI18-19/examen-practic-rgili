package cat.tecnocampus.logger.restServiceLogger;

import cat.tecnocampus.logger.LoggerApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestServiceLogger {
    //TODO: el rest service que ha d'escriure a pantalla els missatges síncrons enviats pel servei d'usuari

    private Logger log = LogManager.getLogger(LoggerApplication.class);


    @GetMapping("usersDelete/{username}")
    public String usersDelete(@PathVariable String username){
        log.info("usersDelete "+username);
        return "received message";
    }

    @GetMapping("usersCreate")
    public String usersCreate(){
        log.info("usersCreate ");
        return "received message";
    }

}
