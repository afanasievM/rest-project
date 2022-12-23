package ua.com.foxminded.courseproject.mapper;

import org.springframework.stereotype.Component;
import ua.com.foxminded.courseproject.dto.GroupDto;
import ua.com.foxminded.courseproject.entity.Group;

@Component
public class GroupMapper implements Mapper<GroupDto, Group> {

    @Override
    public GroupDto toDto(Group entity) {
        if (entity == null) {
            return null;
        }
        GroupDto dto = new GroupDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public Group toEntity(GroupDto dto) {
        if (dto == null) {
            return null;
        }
        Group entity = new Group();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }
}
