package ua.com.foxminded.courseproject.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ua.com.foxminded.courseproject.dto.DayScheduleDto
import ua.com.foxminded.courseproject.dto.WeekScheduleDto
import ua.com.foxminded.courseproject.entity.DaySchedule
import ua.com.foxminded.courseproject.entity.WeekSchedule

@Component
class WeekScheduleMapper @Autowired constructor(private val dayScheduleMapper: DayScheduleMapper) :
    Mapper<WeekScheduleDto?, WeekSchedule?> {
    override fun toDto(entity: WeekSchedule?): WeekScheduleDto? {
        if (entity == null) {
            return null
        }
        val dto = WeekScheduleDto()
        dto.id = entity.id
        dto.daysSchedule = entity.daysSchedule.stream().map { entity: DaySchedule? -> dayScheduleMapper.toDto(entity) }
            .toList()
        dto.isOdd = entity.isOdd
        return dto
    }

    override fun toEntity(dto: WeekScheduleDto?): WeekSchedule? {
        if (dto == null) {
            return null
        }
        val entity = WeekSchedule()
        entity.id = dto.id
        entity.daysSchedule = dto.daysSchedule.stream().map { dto: DayScheduleDto? -> dayScheduleMapper.toEntity(dto) }
            .toList()
        entity.isOdd = dto.isOdd
        return entity
    }
}