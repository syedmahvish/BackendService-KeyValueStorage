package pojo;

import org.springframework.http.HttpStatus;

import java.net.URL;

public class TogglePostRequest {

    String key;
    String body;
    HttpStatus status;

    public TogglePostRequest(String key, String body){
        this.key = key;
        this.body = body;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }



}
