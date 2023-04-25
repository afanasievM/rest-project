package ua.com.foxminded.courseproject.mapper

import org.springframework.stereotype.Component
import ua.com.foxminded.courseproject.dto.TeacherDto
import ua.com.foxminded.courseproject.entity.Teacher

@Component
class TeacherMapper : Mapper<TeacherDto?, Teacher?> {
    override fun toDto(entity: Teacher?): TeacherDto? {
        if (entity == null) {
            return null
        }
        val dto = TeacherDto()
        dto.id = entity.id
        dto.birthDay = entity.birthDay
        dto.firstName = entity.firstName
        dto.lastName = entity.lastName
        dto.degree = entity.degree
        dto.rank = entity.rank
        dto.salary = entity.salary
        dto.title = entity.title
        dto.firstDay = entity.firstDay
        return dto
    }

    override fun toEntity(dto: TeacherDto?): Teacher? {
        if (dto == null) {
            return null
        }
        val entity = Teacher()
        entity.id = dto.id
        entity.birthDay = dto.birthDay
        entity.firstName = dto.firstName
        entity.lastName = dto.lastName
        entity.degree = dto.degree
        entity.rank = dto.rank
        entity.salary = dto.salary
        entity.title = dto.title
        entity.firstDay = dto.firstDay
        return entity
    }
}