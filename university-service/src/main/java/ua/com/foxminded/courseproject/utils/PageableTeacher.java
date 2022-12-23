package ua.com.foxminded.courseproject.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.courseproject.dto.TeacherDto;

import java.util.List;

@Schema(description = "Response-Object Page<TeacherDto>).")
public class PageableTeacher extends PageImpl<TeacherDto> {

    @JsonIgnore
    public PageableTeacher(List content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
