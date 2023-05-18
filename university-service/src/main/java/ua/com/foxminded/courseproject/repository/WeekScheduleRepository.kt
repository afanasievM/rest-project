package ua.com.foxminded.courseproject.repository

import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import ua.com.foxminded.courseproject.entity.DaySchedule
import ua.com.foxminded.courseproject.entity.WeekSchedule
import java.util.*

interface WeekScheduleRepository : MongoRepository<WeekSchedule, UUID> {
    @Aggregation(
        pipeline = [
            "{\$match: {odd: true}}",
            "{ \$unwind: '\$days' }",
            "{ \$lookup: { from: 'days', localField: 'days.\$id', foreignField: '_id', as: 'referenced_days' } }",
            "{ \$unwind: '\$referenced_days' }",
            "{ \$replaceRoot: { newRoot: '\$referenced_days' } }",
            "{ \$match: { day_number: { \$eq: ?0 } } }",
            "{ \$limit: 1 }"
        ]
    )
    fun findDayScheduleByDayNumberFromOddWeek(number: Int): DaySchedule?

    @Aggregation(
        pipeline = [
            "{\$match: {odd: false}}",
            "{ \$unwind: '\$days' }",
            "{ \$lookup: { from: 'days', localField: 'days.\$id', foreignField: '_id', as: 'referenced_days' } }",
            "{ \$unwind: '\$referenced_days' }",
            "{ \$replaceRoot: { newRoot: '\$referenced_days' } }",
            "{ \$match: { day_number: { \$eq: ?0 } } }",
            "{ \$limit: 1 }"
        ]
    )
    fun findDayScheduleByDayNumberFromEvenWeek(number: Int): DaySchedule?



}

