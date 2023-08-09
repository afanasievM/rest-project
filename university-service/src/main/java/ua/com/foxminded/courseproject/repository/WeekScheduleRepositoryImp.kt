package ua.com.foxminded.courseproject.repository

import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import ua.com.foxminded.courseproject.entity.DaySchedule
import ua.com.foxminded.courseproject.mapper.DayScheduleMapper


@Repository
class WeekScheduleRepositoryImp(
    @Autowired var template: ReactiveMongoTemplate,
    @Autowired val mapper: DayScheduleMapper
) :
    WeekScheduleRepository {
    private val COLLECTION_NAME = "weeks"

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
    override fun findDayScheduleByDayNumberFromOddWeek(number: Int): Mono<DaySchedule?> {
        val pipe = Aggregation.match(Criteria("odd").`is`("true"))
        val agregation =
            Aggregation.newAggregation(pipe).withOptions(Aggregation.newAggregationOptions().allowDiskUse(true).build())
        return template.aggregate(agregation,COLLECTION_NAME,Document::class.java)
            .doOnEach { println(it) }
            .log()
            .map { mapper.documentToEntity(it) }.toMono()
    }

    override fun findDayScheduleByDayNumberFromEvenWeek(number: Int): Mono<DaySchedule?> {
        TODO("Not yet implemented")
    }


//    override fun existsStudentByFirstNameAndLastNameAndBirthDay(
//        firstName: String,
//        lastName: String,
//        birthDay: LocalDate
//    ): Mono<Boolean> {
//        val query = Query()
//        query.addCriteria(
//            Criteria
//                .where("birthday").`is`(birthDay)
//                .and("firstname").`is`(firstName)
//                .and("lastname").`is`(lastName)
//        )
//        return template.exists(query, COLLECTION_NAME)
//    }

//    private fun studentMapper(doc: Document): Mono<Student> {
//        val dbRef = doc.get("group_id", DBRef::class.java)
//        return if (dbRef == null) {
//            Mono.just(doc)
//                .map { mapper.documentToEntity(it) }
//        } else {
//            Mono.just(doc)
//                .zipWith(template.findById(dbRef.id, Group::class.java, dbRef.collectionName))
//                .map {
//                    it.t1["group"] = it.t2
//                    it.t1.remove("group_id")
//                    return@map mapper.documentToEntity(it.t1)
//                }
//        }
//    }
}





