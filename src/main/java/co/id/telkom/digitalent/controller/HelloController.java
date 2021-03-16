package co.id.telkom.digitalent.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping(value="/api/v1", produces={"application/json"})
@RestController
public class HelloController {

    @GetMapping
    public String index() {
        return "SpringBoot REST API is Running!";
    }

    @GetMapping("/example")
    public String getExample(@RequestParam("email") String email,
                             @RequestParam("name") String name) {
        return "Email: " + email +  ", Name: " + name;
    }

    @PostMapping("/example")
    public String postExample(@RequestParam("email") String email,
                              @RequestParam("name") String name) {
        return "Email: " + email +  ", Name: " + name;
    }
}
