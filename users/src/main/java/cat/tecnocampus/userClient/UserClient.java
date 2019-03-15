package cat.tecnocampus.userClient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserClient {

    private static RestTemplate restTemplate;

    private static final String URL_DELETE_USER ="http://localhost:8081/usersDelete/";
    private static final String URL_CREATE_USER ="http://localhost:8081/usersCreate";

    @HystrixCommand(fallbackMethod = "loggerDeleteConnectionLost")
    public int comunicateDeleteUser(String username){
        String consulta = URL_DELETE_USER + username;
        restTemplate = new RestTemplate();
        restTemplate.getForObject(consulta, String.class);
        return 2;
    }


    public int loggerDeleteConnectionLost(){
        return -2;
    }

    @HystrixCommand(fallbackMethod = "loggerCreateConnectionLost")
    public int comunicateCreateUser(){
        restTemplate = new RestTemplate();
        restTemplate.getForObject(URL_CREATE_USER, String.class);
        return 1;
    }

    public int loggerCreateConnectionLost(){
        return -1;
    }

}
