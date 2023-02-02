package accenture.challenge.service;

import accenture.challenge.dto.ApiCepDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ApiCepService {

    @Autowired
    private RestTemplate template = new RestTemplate();

    public String findCEP(String cep){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        return template.exchange("http://cep.la/api/".concat(cep), HttpMethod.GET, entity, String.class).getBody();
    }
}
