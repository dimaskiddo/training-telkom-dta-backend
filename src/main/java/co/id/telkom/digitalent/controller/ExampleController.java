package co.id.telkom.digitalent.controller;

import co.id.telkom.digitalent.model.ExampleModel;
import co.id.telkom.digitalent.response.BuilderResponse;
import co.id.telkom.digitalent.response.DataResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value="/api/v1/example", produces={"application/json"})
@RestController
public class ExampleController {

    @GetMapping
    public void getExample(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "email", required = false) String email) throws IOException {
        if (name == null) {
            name = "";
        }

        if (email == null) {
            email = "";
        }

        ExampleModel exampleModel = new ExampleModel();
        DataResponse<ExampleModel> dataResponse = new DataResponse<>();

        exampleModel.setName(name);
        exampleModel.setEmail(email);

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setStatus(BuilderResponse.SUCCESS);
        dataResponse.setData(exampleModel);

        BuilderResponse.responseWriter(response, dataResponse);
    }

    @PostMapping
    public void postExample(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "email", required = false) String email) throws IOException {
        if (name == null) {
            name = "";
        }

        if (email == null) {
            email = "";
        }

        ExampleModel exampleModel = new ExampleModel();
        DataResponse<ExampleModel> dataResponse = new DataResponse<>();

        exampleModel.setName(name);
        exampleModel.setEmail(email);

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setStatus(BuilderResponse.SUCCESS);
        dataResponse.setData(exampleModel);

        BuilderResponse.responseWriter(response, dataResponse);
    }
}
