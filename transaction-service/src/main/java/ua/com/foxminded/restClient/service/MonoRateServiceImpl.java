package ua.com.foxminded.restClient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.com.foxminded.restClient.dto.MonoRate;
import ua.com.foxminded.restClient.dto.Rate;

import java.util.Arrays;
import java.util.List;

@Service
public class MonoRateServiceImpl implements RateService {
    @Value("${mono.url}")
    private String URL;

    private RestTemplate restTemplate;

    @Autowired
    public MonoRateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Rate> getRates() {
        ResponseEntity<MonoRate[]> response = restTemplate.getForEntity(URL, MonoRate[].class);
        MonoRate[] rates = response.getBody();
        return Arrays.stream((Rate[]) rates).toList();
    }


}
