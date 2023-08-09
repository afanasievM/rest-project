package ua.com.foxminded.courseproject.repository

import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.DaySchedule

interface WeekScheduleRepository{
//    @Aggregation(
//        pipeline = [
//            "{\$match: {odd: true}}",
//            "{ \$unwind: '\$days' }",
//            "{ \$lookup: { from: 'days', localField: 'days.\$id', foreignField: '_id', as: 'referenced_days' } }",
//            "{ \$unwind: '\$referenced_days' }",
//            "{ \$replaceRoot: { newRoot: '\$referenced_days' } }",
//            "{ \$match: { day_number: { \$eq: ?0 } } }",
//            "{ \$limit: 1 }"
//        ]
//    )
    fun findDayScheduleByDayNumberFromOddWeek(number: Int): Mono<DaySchedule?>

//    @Aggregation(
//        pipeline = [
//            "{\$match: {odd: false}}",
//            "{ \$unwind: '\$days' }",
//            "{ \$lookup: { from: 'days', localField: 'days.\$id', foreignField: '_id', as: 'referenced_days' } }",
//            "{ \$unwind: '\$referenced_days' }",
//            "{ \$replaceRoot: { newRoot: '\$referenced_days' } }",
//            "{ \$match: { day_number: { \$eq: ?0 } } }",
//            "{ \$limit: 1 }"
//        ]
//    )
    fun findDayScheduleByDayNumberFromEvenWeek(number: Int): Mono<DaySchedule?>

}

