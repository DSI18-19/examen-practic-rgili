package cat.tecnocampus.loggerClient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class LoggerClient {

    private static RestTemplate restTemplate;

    private static final String URL_DELETE_USER ="http://localhost:8081/usersDelete/";
    private static final String URL_CREATE_USER ="http://localhost:8081/usersCreate";

    public static final int CREATE_COMMUNICATION_SUCCESS = 1;
    public static final int DELETE_COMMUNICATION_SUCCESS = 2;
    public static final int CREATE_COMMUNICATION_FAIL = -1;
    public static final int DELETE_COMMUNICATION_FAIL = -2;

    @HystrixCommand(fallbackMethod = "loggerDeleteConnectionLost")
    public int comunicateDeleteUser(String username){
        String consulta = URL_DELETE_USER + username;
        restTemplate = new RestTemplate();
        restTemplate.getForObject(consulta, String.class);
        return DELETE_COMMUNICATION_SUCCESS;
    }


    public int loggerDeleteConnectionLost(){
        return DELETE_COMMUNICATION_FAIL;
    }

    @HystrixCommand(fallbackMethod = "loggerCreateConnectionLost")
    public int comunicateCreateUser(){
        restTemplate = new RestTemplate();
        restTemplate.getForObject(URL_CREATE_USER, String.class);
        return CREATE_COMMUNICATION_SUCCESS;
    }

    public int loggerCreateConnectionLost(){
        return CREATE_COMMUNICATION_FAIL;
    }

}
