package pojo;

import org.springframework.http.HttpStatus;

public class TogglePostRequestResponse {
    HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
