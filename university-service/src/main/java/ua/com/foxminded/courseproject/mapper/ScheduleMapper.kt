package ua.com.foxminded.courseproject.mapper

import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ua.com.foxminded.courseproject.dto.ScheduleDto
import ua.com.foxminded.courseproject.dto.WeekScheduleDto
import ua.com.foxminded.courseproject.entity.Schedule
import ua.com.foxminded.courseproject.entity.WeekSchedule

@Component
class ScheduleMapper @Autowired constructor(private val weekScheduleMapper: WeekScheduleMapper) :
    Mapper<ScheduleDto?, Schedule?, Document> {
    override fun toDto(entity: Schedule?): ScheduleDto? {
        if (entity == null) {
            return null
        }
        val dto = ScheduleDto()
        dto.id = entity.id
        dto.endDate = entity.endDate
        dto.startDate = entity.startDate
        dto.weeks = entity.weeks.stream().map { entity: WeekSchedule? -> weekScheduleMapper.toDto(entity) }
            .toList() as List<WeekScheduleDto>?
        return dto
    }

    override fun toEntity(dto: ScheduleDto?): Schedule? {
        if (dto == null) {
            return null
        }
        val entity = Schedule()
        entity.id = dto.id
        entity.endDate = dto.endDate
        entity.endDate = dto.startDate
        entity.weeks = dto.weeks?.stream()?.map { dto: WeekScheduleDto? -> weekScheduleMapper.toEntity(dto) }
            ?.toList() as MutableList<WeekSchedule>
        return entity
    }


    override fun documentToEntity(doc: Document): Schedule? {
        TODO("Not yet implemented")
    }

    override fun entityToDocument(entity: Schedule?): Document {
        TODO("Not yet implemented")
    }


}