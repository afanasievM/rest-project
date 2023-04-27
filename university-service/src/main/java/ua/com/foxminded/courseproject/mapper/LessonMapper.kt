package ua.com.foxminded.courseproject.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ua.com.foxminded.courseproject.dto.GroupDto
import ua.com.foxminded.courseproject.dto.LessonDto
import ua.com.foxminded.courseproject.entity.Group
import ua.com.foxminded.courseproject.entity.Lesson

@Component
class LessonMapper @Autowired constructor(
    private val classRoomMapper: ClassRoomMapper,
    private val subjectMapper: SubjectMapper,
    private val teacherMapper: TeacherMapper,
    private val groupMapper: GroupMapper
) : Mapper<LessonDto?, Lesson?> {
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
}