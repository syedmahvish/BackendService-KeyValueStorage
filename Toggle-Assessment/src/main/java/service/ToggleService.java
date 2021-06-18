package service;

import net.minidev.json.JSONArray;
import org.springframework.http.HttpStatus;
import pojo.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ToggleService {
    TogglePostRequest togglePostRequest;
    TogglePostRequestResponse togglePostRequestResponse;
    Map<String, String> map = new ConcurrentHashMap<>();

    public TogglePostRequestResponse validPostKey(String key){
        togglePostRequestResponse = new TogglePostRequestResponse();

        if(key == null || key.trim().isEmpty() || !(key.matches("^([\\w-/])+$"))){
            togglePostRequestResponse.setStatus(HttpStatus.BAD_REQUEST);
        }else{
            togglePostRequestResponse.setStatus(HttpStatus.OK);
        }

        return togglePostRequestResponse;
    }

    public HttpStatus validGetKey(String key){
        if(key == null || key.trim().isEmpty()){
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    public TogglePostRequestResponse mapToggleRequestforKey(TogglePostRequest postRequest){
        this.togglePostRequest = postRequest;
        String validRequestKey = generateValidKey(postRequest.getKey());

        //if key already present, just override it.
        map.put(validRequestKey, postRequest.getBody());

        togglePostRequestResponse.setStatus(HttpStatus.OK);
        return togglePostRequestResponse;
    }

    public ToggleGetResponse getResponsesForKey(String postRequest){
        String currentRequest = postRequest;
        currentRequest = currentRequest.replace("*", ".*");

        List<Toggle> list = new ArrayList<>();

        ToggleGetResponse response = new ToggleGetResponse();

        for(Map.Entry entry : map.entrySet()){
            String str = (String) entry.getKey();
            if(str.matches(currentRequest)){
                Toggle toggle = new Toggle();
                toggle.setKey(str);
                toggle.setValue((String)entry.getValue());
                list.add(toggle);
            }
        }

        if(list.size() == 0){
            response.setResult(Collections.emptyList());
            response.setStatus(HttpStatus.NOT_FOUND);
            return response;
        }

        response.setResult(list);
        response.setStatus(HttpStatus.OK);

        return response;
    }

    public ToggleDeleteResponse deleteEntriesforKey(String deleteParameter){

        ToggleDeleteResponse toggleDeleteResponse = new ToggleDeleteResponse();

        if(map.isEmpty() || map.size() == 0){
            toggleDeleteResponse.setStatus(HttpStatus.BAD_REQUEST);
            return toggleDeleteResponse;
        }

        String  currentDeleteRequest = deleteParameter;
        currentDeleteRequest = currentDeleteRequest.replace("*", ".*");

        ToggleGetResponse response = new ToggleGetResponse();

        for(Map.Entry entry : map.entrySet()){
            String str = (String) entry.getKey();
            if(str.matches(currentDeleteRequest)){
                map.remove(str);
            }
        }

        toggleDeleteResponse.setStatus(HttpStatus.OK);
        return toggleDeleteResponse;
    }

    private String generateValidKey(String keyString){
        char c = keyString.charAt(keyString.length()-1);
        if(c == '/'){
            keyString =  keyString.substring(0,keyString.length()-1);
        }
        return keyString;
    }
}
