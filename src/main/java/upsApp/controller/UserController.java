package upsApp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/hello")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public String helloWorld() {
        return "Hello World";
    }
}
