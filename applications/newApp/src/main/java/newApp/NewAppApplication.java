package newApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableZuulProxy
public class NewAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewAppApplication.class, args);
    }

    @RequestMapping("/one")
    public String one(){
        return "One from new application";
    }

    @RequestMapping("/two")
    public String two(){
        return "Two from new application";
    }

    @RequestMapping("/one/nested")
    public String nestedOne(){
        return "Nested One from new application";
    }
}
