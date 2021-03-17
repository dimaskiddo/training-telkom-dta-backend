package co.id.telkom.digitalent.response;

import org.springframework.http.HttpStatus;

public class BaseResponse {

    public String status;
    public HttpStatus message;

    public HttpStatus getMessage() {
        return message;
    }

    public void setMessage(HttpStatus message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
