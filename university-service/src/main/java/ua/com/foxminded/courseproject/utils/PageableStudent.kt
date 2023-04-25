package ua.com.foxminded.courseproject.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.courseproject.dto.StudentDto;

import java.util.List;

@Schema(description = "Response-Object Page<StudentDto>).")
public class PageableStudent extends PageImpl<StudentDto> {

    @JsonIgnore
    public PageableStudent(List content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
