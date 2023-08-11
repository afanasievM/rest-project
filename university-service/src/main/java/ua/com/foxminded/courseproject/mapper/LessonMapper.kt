package ua.com.foxminded.courseproject.mapper

import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.stereotype.Component
import ua.com.foxminded.courseproject.dto.GroupDto
import ua.com.foxminded.courseproject.dto.LessonDto
import ua.com.foxminded.courseproject.entity.*
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

@Component
class LessonMapper @Autowired constructor(
    private val classRoomMapper: ClassRoomMapper,
    private val subjectMapper: SubjectMapper,
    private val teacherMapper: TeacherMapper,
    private val groupMapper: GroupMapper
) : Mapper<LessonDto?, Lesson?, Document> {
    override fun toDto(entity: Lesson?): LessonDto? {
        if (entity == null) {
            return null
        }
        val dto = LessonDto()
        dto.id = entity.id
        dto.classRoom = classRoomMapper.toDto(entity.classRoom)
        dto.endTime = entity.endTime
        dto.number = entity.number
        dto.startTime = entity.startTime
        dto.subject = subjectMapper.toDto(entity.subject)
        dto.teacher = teacherMapper.toDto(entity.teacher)
        dto.groups = entity.groups.stream().map { entity: Group? -> groupMapper.toDto(entity) }
            .toList() as List<GroupDto>?
        return dto
    }

    override fun toEntity(dto: LessonDto?): Lesson? {
        if (dto == null) {
            return null
        }
        val entity = Lesson()
        entity.id = dto.id
        entity.classRoom = classRoomMapper.toEntity(dto.classRoom)
        entity.endTime = dto.endTime
        entity.number = dto.number
        entity.startTime = dto.startTime
        entity.subject = subjectMapper.toEntity(dto.subject)
        entity.teacher = teacherMapper.toEntity(dto.teacher)
        entity.groups = dto.groups?.stream()?.map { dto: GroupDto? -> groupMapper.toEntity(dto) }
            ?.toList() as MutableList<Group>
        return entity
    }

    override fun documentToEntity(doc: Document): Lesson? {
        val entity = Lesson()
        entity.id = UUID.fromString(doc.getString("_id"))
        entity.subject = doc.get("subject") as Subject
        entity.classRoom = doc.get("classroom") as ClassRoom
        entity.number = doc.getInteger("number")
        entity.startTime = doc.getDate("start_time").toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
        entity.endTime = doc.getDate("end_time").toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
        entity.teacher = doc.get("teacher") as Teacher
        entity.groups = doc.getList("groups", Group::class.java)
        return entity
    }

    override fun entityToDocument(entity: Lesson?): Document {
        TODO("Not yet implemented")
    }


}