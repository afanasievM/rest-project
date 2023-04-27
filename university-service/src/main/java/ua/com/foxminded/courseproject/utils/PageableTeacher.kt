package ua.com.foxminded.courseproject.utils

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import ua.com.foxminded.courseproject.dto.TeacherDto

@Schema(description = "Response-Object Page<TeacherDto>).")
class PageableTeacher @JsonIgnore constructor(content: List<*>,
                                              pageable: Pageable,
                                              total: Long) : PageImpl<TeacherDto>(content as MutableList<TeacherDto>, pageable, total)
