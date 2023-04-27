package ua.com.foxminded.restClient.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.restClient.dto.TransactionDto;

import java.util.List;

@Schema(description = "Response-Object Page<StudentDto>).")
public class PageableTransaction extends PageImpl<TransactionDto> {

    @JsonIgnore
    public PageableTransaction(List content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
