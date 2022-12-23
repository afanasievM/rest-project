package ua.com.foxminded.courseproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ua.com.foxminded.courseproject.dto.TransactionDto;
import ua.com.foxminded.courseproject.utils.PaginatedResponse;


import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class TransactionServiceImpl implements TransactionService {
    @Value("${university.service.URL}")
    private String URL;

    @Value("${spring.mvc.format.date-time}")
    private String DATE_TIME_FORMAT;
    private RestTemplate restTemplate;

    @Autowired
    public TransactionServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Page<TransactionDto> getTransactions(String id, String currency, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        String url = getURL(id, currency, startDate, endDate, pageable);
        ResponseEntity<PaginatedResponse<TransactionDto>> response =
                restTemplate.exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });
        return response.getBody();
    }

    private String getURL(String id, String currency, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(URL)
                .pathSegment(String.valueOf(id))
                .pathSegment(String.valueOf(currency))
                .queryParamIfPresent("startdate", Optional.ofNullable(startDate)
                        .stream().map(d -> d.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))).findAny())
                .queryParamIfPresent("enddate", Optional.ofNullable(endDate)
                        .stream().map(d -> d.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))).findAny())
                .queryParamIfPresent("size", Optional.of(pageable.getPageSize()))
                .queryParamIfPresent("page", Optional.of(pageable.getPageNumber()))
                .build();
        return uriComponents.toString();
    }
}

