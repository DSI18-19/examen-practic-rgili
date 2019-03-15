package cat.tecnocampus.loggerClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class LoggerClient {

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    private static final String URL_DELETE_USER ="http://logger/usersDelete/";
    private static final String URL_CREATE_USER ="http://logger/usersCreate";

    public static final int CREATE_COMMUNICATION_SUCCESS = 1;
    public static final int DELETE_COMMUNICATION_SUCCESS = 2;
    public static final int CREATE_COMMUNICATION_FAIL = -1;
    public static final int DELETE_COMMUNICATION_FAIL = -2;

    @HystrixCommand(fallbackMethod = "loggerDeleteConnectionLost")
    public int comunicateDeleteUser(String username){
        String consulta = URL_DELETE_USER + username;
        restTemplate.getForObject(consulta, String.class);
        return DELETE_COMMUNICATION_SUCCESS;
    }


    public int loggerDeleteConnectionLost(String username){
        return DELETE_COMMUNICATION_FAIL;
    }

    @HystrixCommand(fallbackMethod = "loggerCreateConnectionLost")
    public int comunicateCreateUser(){
        restTemplate.getForObject(URL_CREATE_USER, String.class);
        return CREATE_COMMUNICATION_SUCCESS;
    }

    public int loggerCreateConnectionLost(String username){
        return CREATE_COMMUNICATION_FAIL;
    }

}
