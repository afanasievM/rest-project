package ua.com.foxminded.courseproject.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ua.com.foxminded.courseproject.dto.DayScheduleDto
import ua.com.foxminded.courseproject.dto.LessonDto
import ua.com.foxminded.courseproject.entity.DaySchedule
import ua.com.foxminded.courseproject.entity.Lesson

@Component
class DayScheduleMapper @Autowired constructor(private val lessonMapper: LessonMapper) :
    Mapper<DayScheduleDto?, DaySchedule?> {
    override fun toDto(entity: DaySchedule?): DayScheduleDto? {
        if (entity == null) {
            return null
        }
        val dto = DayScheduleDto()
        dto.id = entity.id
        dto.dayNumber = entity.dayNumber
        dto.lessons = entity.lessons?.stream()?.map { entity: Lesson? -> lessonMapper.toDto(entity) }?.toList()
        return dto
    }

    override fun toEntity(dto: DayScheduleDto?): DaySchedule? {
        if (dto == null) {
            return null
        }
        val entity = DaySchedule()
        entity.id = dto.id
        entity.dayNumber = dto.dayNumber
        entity.lessons = dto.lessons.stream().map { dto: LessonDto? -> lessonMapper.toEntity(dto) }.toList() as MutableList<Lesson>
        return entity
    }
}