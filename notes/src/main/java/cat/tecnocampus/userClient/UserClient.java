package cat.tecnocampus.userClient;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
@Configuration
@RibbonClient(name = "users", configuration = UsersConfiguration.class)
public class UserClient {
    public static final int USER_EXISTS = 1, USER_DOES_NOT_EXIST = 2, CLIENT_DOWN = 3;

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "userConnectionLost")
    public int userExists(String username){

        //see that it calls users directly without going through the reverse proxy service
        int result = restTemplate.getForObject("http://users/userExists/" + username, Boolean.class)? USER_EXISTS : USER_DOES_NOT_EXIST;

        return result;
    }

    public int userConnectionLost(String username){
        return CLIENT_DOWN;
    }

}

class UsersConfiguration {
    @Autowired
    IClientConfig ribbonClientConfig;

    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl();
    }

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new AvailabilityFilteringRule();
    }
}

