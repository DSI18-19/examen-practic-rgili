package cat.tecnocampus.edgezuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class EdgezuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgezuulApplication.class, args);
    }

}

