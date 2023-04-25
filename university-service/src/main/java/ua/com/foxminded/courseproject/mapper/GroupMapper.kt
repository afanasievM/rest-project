package ua.com.foxminded.courseproject.mapper

import org.springframework.stereotype.Component
import ua.com.foxminded.courseproject.dto.GroupDto
import ua.com.foxminded.courseproject.entity.Group

@Component
class GroupMapper : Mapper<GroupDto?, Group?> {
    override fun toDto(entity: Group?): GroupDto? {
        if (entity == null) {
            return null
        }
        val dto = GroupDto()
        dto.id = entity.id
        dto.name = entity.name
        return dto
    }

    override fun toEntity(dto: GroupDto?): Group? {
        if (dto == null) {
            return null
        }
        val entity = Group()
        entity.id = dto.id
        entity.name = dto.name
        return entity
    }
}