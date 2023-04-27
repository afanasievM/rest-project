package ua.com.foxminded.courseproject.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ua.com.foxminded.courseproject.dto.StudentDto
import ua.com.foxminded.courseproject.entity.Student

@Component
class StudentMapper @Autowired constructor(private val groupMapper: GroupMapper) : Mapper<StudentDto?, Student?> {
    override fun toDto(entity: Student?): StudentDto? {
        if (entity == null) {
            return null
        }
        val dto = StudentDto()
        dto.id = entity.id
        dto.captain = entity.captain
        dto.course = entity.course
        dto.group = groupMapper.toDto(entity.group)
        dto.birthDay = entity.birthDay
        dto.firstName = entity.firstName
        dto.lastName = entity.lastName
        return dto
    }

    override fun toEntity(dto: StudentDto?): Student? {
        if (dto == null) {
            return null
        }
        val entity = Student()
        entity.id = dto.id
        entity.captain = dto.captain
        entity.course = dto.course
        entity.group = groupMapper.toEntity(dto.group)
        entity.birthDay = dto.birthDay
        entity.firstName = dto.firstName
        entity.lastName = dto.lastName
        return entity
    }
}