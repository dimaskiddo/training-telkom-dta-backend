package co.id.telkom.digitalent.controller;

import co.id.telkom.digitalent.response.BuilderResponse;
import co.id.telkom.digitalent.response.DataResponse;
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
        DataResponse<String> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setStatus(BuilderResponse.SUCCESS);
        dataResponse.setData("SpringBoot REST API is Running!");

        BuilderResponse.responseWriter(response, dataResponse);
    }
}
