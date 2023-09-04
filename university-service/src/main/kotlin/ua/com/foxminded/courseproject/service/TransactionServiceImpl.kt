package ua.com.foxminded.courseproject.service

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Optional
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.util.UriComponentsBuilder
import ua.com.foxminded.courseproject.dto.TransactionDto
import ua.com.foxminded.courseproject.utils.PaginatedResponse

@Service
class TransactionServiceImpl(private val restTemplate: RestTemplate) : TransactionService {
    @Value("\${spring.mvc.format.date-time}")
    lateinit var dateTimeFormat: String

    @Value("\${university.service.URL}")
    private lateinit var url: String
    

    override fun getTransactions(
        id: String,
        currency: String,
        startDate: LocalDateTime?,
        endDate: LocalDateTime,
        pageable: Pageable
    ): Page<TransactionDto> {
        val url = getURL(id, currency, startDate, endDate, pageable)
        val response: ResponseEntity<PaginatedResponse<TransactionDto>> =
            restTemplate.exchange<PaginatedResponse<TransactionDto>>(url,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<PaginatedResponse<TransactionDto?>?>() {})
        return response.body
    }

    private fun getURL(
        id: String,
        currency: String,
        startDate: LocalDateTime?,
        endDate: LocalDateTime,
        pageable: Pageable
    ): String {
        val uriComponents = UriComponentsBuilder.fromUriString(url)
            .pathSegment(id)
            .pathSegment(currency)
            .queryParamIfPresent(
                "startdate",
                Optional.ofNullable(startDate?.format(DateTimeFormatter.ofPattern(dateTimeFormat)))
            )
            .queryParam("enddate", endDate.format(DateTimeFormatter.ofPattern(dateTimeFormat)))
            .queryParam("size", pageable.pageSize)
            .queryParam("page", pageable.pageNumber)
            .build()
        return uriComponents.toString()
    }
    
}
