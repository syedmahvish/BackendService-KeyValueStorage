package com.example.Toggle.Assessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pojo.ToggleDeleteResponse;
import pojo.ToggleGetResponse;
import pojo.TogglePostRequest;
import pojo.TogglePostRequestResponse;
import service.ToggleService;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@RestController
public class ToggleController {

    ToggleService toggleService = new ToggleService();

    /**
     * This methods accepts post request
     * Extract parameters and body from request.
     * Calls service method to map them
     * @param request HttpServletRequest request for accepting parameters and body.
     * @return status : Ok for success
     *                  BAD REQUEST for unsuccessful
     */
    @PostMapping("/**")
    public ResponseEntity<TogglePostRequestResponse>  postToggleKeyValuePair(HttpServletRequest request) {
        //check if given url is valid i.e key is valid
        //valid key has only letters, numbers, and slashes
        String requestParameter = request.getRequestURI();
        if(requestParameter.equals("/")){
            requestParameter = "";
        }else {
            requestParameter = requestParameter.substring(1);
        }
        String body = getValue(request);
        TogglePostRequestResponse togglePostRequestResponse = toggleService.validPostKey(requestParameter);
        if(togglePostRequestResponse.getStatus() == HttpStatus.BAD_REQUEST || body == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        TogglePostRequest togglePostRequest = new TogglePostRequest(requestParameter, body);
        togglePostRequestResponse = toggleService.mapToggleRequestforKey(togglePostRequest);
        return new ResponseEntity<>(togglePostRequestResponse, togglePostRequestResponse.getStatus());
    }

    /**
     * This methods accepts parameter to match from map key.
     * @param request HttpServletRequest request for accepting parameters.
     * @return Json Array of key-value pair for given parameter.
     * If parameter is invalid returns BAD REQUEST
     */
    @GetMapping("/**")
    public ResponseEntity<ToggleGetResponse> getValuesForKey(HttpServletRequest request){
        String requestParameter = request.getRequestURI();
        if(requestParameter.equals("/")){
            requestParameter = "";
        }else{
            requestParameter = requestParameter.substring(1);
        }

        HttpStatus status = toggleService.validGetKey(requestParameter);

        if(status == HttpStatus.BAD_REQUEST){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ToggleGetResponse response = toggleService.getResponsesForKey(requestParameter);
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * This method deletes key from map thats matches given parameter
     * @param request HttpServletRequest request for accepting parameters.
     * @return Status as : OK if valid parameters for delete
     *                     If parameter is invalid returns BAD REQUEST
     */
    @DeleteMapping("/**")
    public ResponseEntity<ToggleDeleteResponse> deleteValueForKey(HttpServletRequest request){
        String requestParameter = request.getRequestURI();
        if(requestParameter.equals("/")){
            requestParameter = "";
        }else{
            requestParameter = requestParameter.substring(1);
        }

        //check if given url is valid i.e key is valid
        //valid key has only letters, numbers, and slashes
        HttpStatus status = toggleService.validGetKey(requestParameter);
        ToggleDeleteResponse toggleDeleteResponse = new ToggleDeleteResponse();

        if(status == HttpStatus.BAD_REQUEST){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        toggleDeleteResponse = toggleService.deleteEntriesforKey(requestParameter);
        return new ResponseEntity<>(toggleDeleteResponse, HttpStatus.OK);
    }

    private String getValue(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        if (map == null) {
            return null;
        }
        Set<String> bodySet = map.keySet();
        return bodySet.stream().findFirst().orElse(null);
    }
}
