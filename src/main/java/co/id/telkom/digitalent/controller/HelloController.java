package co.id.telkom.digitalent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/v1/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/api/v1/example")
    public String getExample(@RequestParam("email") String email, @RequestParam("name") String name) {
        return "Email: " + email +  ", Name: " + name;
    }

    @PostMapping("/api/v1/example")
    public String setExample(@RequestParam("email") String email, @RequestParam("name") String name) {
        return "Email: " + email +  ", Name: " + name;
    }
}
