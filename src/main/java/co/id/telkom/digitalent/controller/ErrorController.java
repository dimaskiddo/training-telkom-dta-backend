package co.id.telkom.digitalent.controller;

import co.id.telkom.digitalent.response.WriteResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(produces={"application/json"})
@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public void errorHandler(HttpServletRequest request, HttpServletResponse response) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer errorCode = Integer.valueOf(status.toString());

            switch (errorCode) {
                case 400:
                    WriteResponse.responseBadRequest(response, "");
                    break;

                case 401:
                    WriteResponse.responseUnauthorized(response, "");
                    break;

                case 403:
                    WriteResponse.responseForbidden(response, "");
                    break;

                case 404:
                    WriteResponse.responseNotFound(response, "");
                    break;

                case 500:
                    WriteResponse.responseInternalServerError(response, "");
                    break;

                case 502:
                    WriteResponse.responseBadGateway(response, "");
                    break;

                default:
                    WriteResponse.responseInternalServerError(response, "UNKNOWN ERROR");
            }
        } else {
            WriteResponse.responseSuccessOK(response, "NO ERROR FOUND");
        }
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
