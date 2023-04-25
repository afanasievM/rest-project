package ua.com.foxminded.courseproject.mapper

import org.springframework.stereotype.Component
import ua.com.foxminded.courseproject.dto.ClassRoomDto
import ua.com.foxminded.courseproject.entity.ClassRoom

@Component
class ClassRoomMapper : Mapper<ClassRoomDto?, ClassRoom?> {
    override fun toDto(entity: ClassRoom?): ClassRoomDto? {
        if (entity == null) {
            return null
        }
        val dto = ClassRoomDto()
        dto.id = entity.id
        dto.number = entity.number
        return dto
    }

    override fun toEntity(dto: ClassRoomDto?): ClassRoom? {
        if (dto == null) {
            return null
        }
        val entity = ClassRoom()
        entity.id = dto.id
        entity.number = dto.number
        return entity
    }
}