package pojo;

import net.minidev.json.JSONValue;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ToggleGetResponse {
    List<Toggle> result = new ArrayList<>();

    HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<Toggle> getResult() {
        return result;
    }

    public void setResult(List<Toggle> result) {
        this.result = result;
    }
}
