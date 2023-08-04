package ua.com.foxminded.courseproject.mapper

import org.bson.Document
import org.hibernate.exception.DataException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ua.com.foxminded.courseproject.dto.StudentDto
import ua.com.foxminded.courseproject.entity.Group
import ua.com.foxminded.courseproject.entity.Student
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class StudentMapper @Autowired constructor(private val groupMapper: GroupMapper) :
    Mapper<StudentDto?, Student, Document> {
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

    override fun documentToEntity(doc: Document): Student {
        val entity = Student()
        entity.id = UUID.fromString(doc.getString("_id"))
        entity.captain = doc.getBoolean("captain")
        entity.course = doc.getInteger("course")
        entity.group = doc["group"] as Group?
        entity.birthDay = doc.getDate("birthday").toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        entity.firstName = doc.getString("firstname")
        entity.lastName = doc.getString("lastname")
        return entity
    }

    override fun entityToDocument(entity: Student): Document {
        val doc = Document()
        doc["_id"] = entity.id.toString()
        doc["captain"] = entity.captain
        doc["course"] = entity.course
//        doc["group_id"] = entity.group!!.id
        doc["birthday"] = Date.from(Instant.now())
        doc["firstname"] = entity.firstName
        doc["lastname"] = entity.lastName
        println(doc)
        return doc
    }
}