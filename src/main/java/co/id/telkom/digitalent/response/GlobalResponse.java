package co.id.telkom.digitalent.response;

import org.springframework.http.HttpStatus;

public class GlobalResponse<T> {

    private String status;
    private HttpStatus message;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HttpStatus getMessage() {
        return message;
    }

    public void setMessage(HttpStatus message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
