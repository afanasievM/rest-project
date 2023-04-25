package ua.com.foxminded.courseproject.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.courseproject.dto.LessonDto;
import ua.com.foxminded.courseproject.entity.Lesson;

@Component
public class LessonMapper implements Mapper<LessonDto, Lesson> {

    private ClassRoomMapper classRoomMapper;
    private SubjectMapper subjectMapper;
    private TeacherMapper teacherMapper;
    private GroupMapper groupMapper;

    @Autowired
    public LessonMapper(ClassRoomMapper classRoomMapper, SubjectMapper subjectMapper, TeacherMapper teacherMapper, GroupMapper groupMapper) {
        this.classRoomMapper = classRoomMapper;
        this.subjectMapper = subjectMapper;
        this.teacherMapper = teacherMapper;
        this.groupMapper = groupMapper;
    }

    @Override
    public LessonDto toDto(Lesson entity) {
        if (entity == null) {
            return null;
        }
        LessonDto dto = new LessonDto();
        dto.setId(entity.getId());
        dto.setClassRoom(classRoomMapper.toDto(entity.getClassRoom()));
        dto.setEndTime(entity.getEndTime());
        dto.setNumber(entity.getNumber());
        dto.setStartTime(entity.getStartTime());
        dto.setSubject(subjectMapper.toDto(entity.getSubject()));
        dto.setTeacher(teacherMapper.toDto(entity.getTeacher()));
        dto.setGroups(entity.getGroups().stream().map(groupMapper::toDto).toList());
        return dto;
    }

    @Override
    public Lesson toEntity(LessonDto dto) {
        if (dto == null) {
            return null;
        }
        Lesson entity = new Lesson();
        entity.setId(dto.getId());
        entity.setClassRoom(classRoomMapper.toEntity(dto.getClassRoom()));
        entity.setEndTime(dto.getEndTime());
        entity.setNumber(dto.getNumber());
        entity.setStartTime(dto.getStartTime());
        entity.setSubject(subjectMapper.toEntity(dto.getSubject()));
        entity.setTeacher(teacherMapper.toEntity(dto.getTeacher()));
        entity.setGroups(dto.getGroups().stream().map(groupMapper::toEntity).toList());
        return entity;
    }
}
