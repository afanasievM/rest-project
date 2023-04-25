package ua.com.foxminded.courseproject.utils

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import ua.com.foxminded.courseproject.dto.StudentDto

@Schema(description = "Response-Object Page<StudentDto>).")
class PageableStudent @JsonIgnore constructor(content: List<*>?,
                                              pageable: Pageable?,
                                              total: Long) : PageImpl<StudentDto?>(content as MutableList<StudentDto?>, pageable, total)
