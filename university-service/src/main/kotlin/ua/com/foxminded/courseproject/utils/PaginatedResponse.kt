package ua.com.foxminded.courseproject.utils

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

@Suppress("LongParameterList", "UnusedPrivateProperty")
class PaginatedResponse<T> : PageImpl<T> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    constructor(
        @JsonProperty("content") content: List<T>,
        @JsonProperty("number") number: Int,
        @JsonProperty("size") size: Int,
        @JsonProperty("totalElements") totalElements: Long,
        @JsonProperty("pageable") pageable: JsonNode,
        @JsonProperty("last") last: Boolean,
        @JsonProperty("totalPages") totalPages: Int,
        @JsonProperty("sort") sort: JsonNode,
        @JsonProperty("first") first: Boolean,
        @JsonProperty("empty") empty: Boolean
    ) : super(content, PageRequest.of(number, size), totalElements)

    constructor(content: List<T>, pageable: Pageable, total: Long) : super(content, pageable, total)
    constructor(content: List<T>) : super(content)
    constructor() : super(emptyList<T>())
}
