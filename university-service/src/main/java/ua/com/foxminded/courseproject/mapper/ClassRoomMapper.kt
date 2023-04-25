package ua.com.foxminded.courseproject.mapper;

import org.springframework.stereotype.Component;
import ua.com.foxminded.courseproject.dto.ClassRoomDto;
import ua.com.foxminded.courseproject.entity.ClassRoom;

@Component
public class ClassRoomMapper implements Mapper<ClassRoomDto, ClassRoom> {

    @Override
    public ClassRoomDto toDto(ClassRoom entity) {
        if (entity == null) {
            return null;
        }
        ClassRoomDto dto = new ClassRoomDto();
        dto.setId(entity.getId());
        dto.setNumber(entity.getNumber());
        return dto;
    }

    @Override
    public ClassRoom toEntity(ClassRoomDto dto) {
        if (dto == null) {
            return null;
        }
        ClassRoom entity = new ClassRoom();
        entity.setId(dto.getId());
        entity.setNumber(dto.getNumber());
        return entity;
    }
}
