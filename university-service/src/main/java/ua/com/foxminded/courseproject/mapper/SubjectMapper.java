package ua.com.foxminded.courseproject.mapper;

import org.springframework.stereotype.Component;
import ua.com.foxminded.courseproject.dto.SubjectDto;
import ua.com.foxminded.courseproject.entity.Subject;

@Component
public class SubjectMapper implements Mapper<SubjectDto, Subject> {

    @Override
    public SubjectDto toDto(Subject entity) {
        if (entity == null) {
            return null;
        }
        SubjectDto dto = new SubjectDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public Subject toEntity(SubjectDto dto) {
        if (dto == null) {
            return null;
        }
        Subject entity = new Subject();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }
}
