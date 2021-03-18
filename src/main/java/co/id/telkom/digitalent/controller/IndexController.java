package co.id.telkom.digitalent.controller;

import co.id.telkom.digitalent.response.WriteResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value="/", produces={"application/json"})
@RestController
public class IndexController {

    @GetMapping
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WriteResponse.responseSuccessOK(response,"SpringBoot REST is running");
    }
}
