package cat.tecnocampus.userClient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserClient {

    private static RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "loggerDeleteConnectionLost")
    public boolean comunicateDeleteUser(String username){
        boolean result = false;

        return result;
    }

    @HystrixCommand(fallbackMethod = "loggerCreateConnectionLost")
    public boolean comunicateCreateUser(String username){
        boolean result = false;

        return result;
    }

    public int loggerCreateConnectionLost(){
        return -1;
    }

    public int loggerDeleteConnectionLost(){
        return -2;
    }
}
