package ua.com.foxminded.courseproject.repository

import org.springframework.data.mongodb.core.aggregation.DateOperators.Week
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import ua.com.foxminded.courseproject.entity.DaySchedule
import java.util.*

interface DayScheduleRepository : MongoRepository<DaySchedule, UUID> {
    @Aggregation(
        pipeline = [
            "{\$lookup:{from: 'weeks' , localField: 'weekId', foreignField: '_id', as: 'weekInfo'}}",
            " { \$unwind: '\$weekInfo' }",
            "{ \$match: { odd: true } }",
            " { \$unwind: '\$days' }",
            " { \$match: { day_number: 1 } }"

        ]
    )
    fun findDayScheduleByDayNumberFromOddWeek(number: Int): DaySchedule?

    @Query("{'odd':false}")
    fun findDayScheduleByDayNumberFromEvenWeek(number: Int): DaySchedule?

    @Aggregation(
        pipeline = [
            "{\$lookup: {from: 'weeks', localField: 'week', foreignField: '_id', as: 'week'}}",
            "{\$unwind: {path: '\$week', preserveNullAndEmptyArrays: true}}",
            "{\$match: {week: {odd: true}}}",
            "{\$project: {_id: 0, day: '\$day'}}"
//        "{ \$match: { day_number: 1 } }",
//        "{ \$limit: 1 }"

        ]
    )
    fun findDaysWithOddWeek(): List<DaySchedule>

    @Aggregation(
        pipeline = [
            "{\$lookup: {from: 'weeks', localField: 'week', foreignField: '_id', as: 'week'}}",
            "{\$unwind: '\$week'}",
            "{\$unwind: '\$days'}"
//            "{\$match: {'week.odd': true}}"
//        "{ \$match: { day_number: 1 } }",
//        "{ \$limit: 1 }"

        ]
    )
    fun findAllWeeks(): List<Week>
}

