package ua.com.foxminded.restClient.utils

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import ua.com.foxminded.restClient.dto.TransactionDto

@Schema(description = "Response-Object Page<StudentDto>).")
class PageableTransaction @JsonIgnore constructor(content: List<*>, pageable: Pageable, total: Long) :
    PageImpl<TransactionDto>(content as MutableList<TransactionDto>, pageable, total)
