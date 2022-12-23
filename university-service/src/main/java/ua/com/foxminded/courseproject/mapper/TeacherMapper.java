package ua.com.foxminded.courseproject.mapper;

import org.springframework.stereotype.Component;
import ua.com.foxminded.courseproject.dto.TeacherDto;
import ua.com.foxminded.courseproject.entity.Teacher;

@Component
public class TeacherMapper implements Mapper<TeacherDto, Teacher> {

    @Override
    public TeacherDto toDto(Teacher entity) {
        if (entity == null) {
            return null;
        }
        TeacherDto dto = new TeacherDto();
        dto.setId(entity.getId());
        dto.setBirthDay(entity.getBirthDay());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDegree(entity.getDegree());
        dto.setRank(entity.getRank());
        dto.setSalary(entity.getSalary());
        dto.setTitle(entity.getTitle());
        dto.setFirstDay(entity.getFirstDay());
        return dto;
    }

    @Override
    public Teacher toEntity(TeacherDto dto) {
        if (dto == null) {
            return null;
        }
        Teacher entity = new Teacher();
        entity.setId(dto.getId());
        entity.setBirthDay(dto.getBirthDay());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDegree(dto.getDegree());
        entity.setRank(dto.getRank());
        entity.setSalary(dto.getSalary());
        entity.setTitle(dto.getTitle());
        entity.setFirstDay(dto.getFirstDay());
        return entity;
    }
}
