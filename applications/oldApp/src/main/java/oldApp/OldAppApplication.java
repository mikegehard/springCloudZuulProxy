package oldApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class OldAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(OldAppApplication.class, args);
    }

    @RequestMapping("/one")
    public String one(){
        return "One from old application";
    }

    @RequestMapping("/one/nested")
    public String nestedOne(){
        return "Nested One from old application";
    }

    @RequestMapping("/two")
    public String two(){
        return "Two from old application";
    }

    @RequestMapping("/three")
    public String three(){
        return "Three from old application";
    }
}
