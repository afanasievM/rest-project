package ua.com.foxminded.courseproject.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.courseproject.dto.StudentDto;
import ua.com.foxminded.courseproject.entity.Student;

@Component
public class StudentMapper implements Mapper<StudentDto, Student> {

    private GroupMapper groupMapper;

    @Autowired
    public StudentMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    @Override
    public StudentDto toDto(Student entity) {
        if (entity == null) {
            return null;
        }
        StudentDto dto = new StudentDto();
        dto.setId(entity.getId());
        dto.setCaptain(entity.getCaptain());
        dto.setCourse(entity.getCourse());
        dto.setGroup(groupMapper.toDto(entity.getGroup()));
        dto.setBirthDay(entity.getBirthDay());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        return dto;
    }

    @Override
    public Student toEntity(StudentDto dto) {
        if (dto == null) {
            return null;
        }
        Student entity = new Student();
        entity.setId(dto.getId());
        entity.setCaptain(dto.getCaptain());
        entity.setCourse(dto.getCourse());
        entity.setGroup(groupMapper.toEntity(dto.getGroup()));
        entity.setBirthDay(dto.getBirthDay());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        return entity;
    }
}
