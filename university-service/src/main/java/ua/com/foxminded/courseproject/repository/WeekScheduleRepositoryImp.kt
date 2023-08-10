package ua.com.foxminded.courseproject.repository

import com.mongodb.DBRef
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.findAll
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import ua.com.foxminded.courseproject.entity.DaySchedule
import ua.com.foxminded.courseproject.entity.Group
import ua.com.foxminded.courseproject.entity.Lesson
import ua.com.foxminded.courseproject.mapper.DayScheduleMapper
import ua.com.foxminded.courseproject.mapper.GroupMapper
import ua.com.foxminded.courseproject.mapper.LessonMapper
import ua.com.foxminded.courseproject.mapper.SubjectMapper


@Repository
class WeekScheduleRepositoryImp(
    @Autowired var template: ReactiveMongoTemplate,
    @Autowired val dayScheduleMapper: DayScheduleMapper,
    @Autowired val groupMapper: GroupMapper,
    @Autowired val lessonMapper: LessonMapper,
    @Autowired val subjectMapper: SubjectMapper
) :
    WeekScheduleRepository {
    private val COLLECTION_NAME = "weeks"

    override fun findDayScheduleByDayNumberFromOddWeek(number: Int): Mono<DaySchedule?> {
        val matchOdd = Aggregation.match(Criteria("odd").`is`(true))
        val unwindDays = Aggregation.unwind("days")
        val lookupDays = Aggregation.lookup("days", "days.\$id", "_id", "referenced_days")
        val unwindRefDays = Aggregation.unwind("referenced_days")
        val replaceRoot = Aggregation.replaceRoot("referenced_days")
        val matchDayNumber = Aggregation.match(Criteria("day_number").`is`(number))
        val limit = Aggregation.limit(1)
        val agregation =
            Aggregation.newAggregation(
                matchOdd,
                unwindDays,
                lookupDays,
                unwindRefDays,
                replaceRoot,
                matchDayNumber,
                limit
            )
        return template.aggregate(agregation, COLLECTION_NAME, Document::class.java)
            .log()
            .flatMap { dayScheduleDBRefsMapper(it) }
            .map { dayScheduleMapper.documentToEntity(it) }
            .toMono()
    }

    override fun findDayScheduleByDayNumberFromEvenWeek(number: Int): Mono<DaySchedule?> {
        TODO("Not yet implemented")
    }

    private fun dayScheduleDBRefsMapper(doc: Document): Flux<Document> {
        val dbRefs = doc.get("lessons", ArrayList<DBRef>())
        return if (dbRefs.isEmpty()) {
            Flux.just(doc)
                .map { it }
        } else {
            val q = Query.query(Criteria.where("_id").`in`(dbRefs.map { it.id }))
            Flux.just(doc)
                .zipWith(
                    template.find(q, Document::class.java, dbRefs.get(0).collectionName)
                        .flatMap { dBRefMapper(it, "subject") }
                        .flatMap { dBRefMapper(it, "classroom") }
                        .flatMap { dBRefMapper(it, "teacher") }
                        .flatMap { groupListDBRefMapper(it) }
//                        .map { lessonMapper.documentToEntity(it) }
                        .collectList()
                )
                .map {
                    it.t1["lessons"] = it.t2
                    return@map it.t1
                }
                .log()
        }
    }

    private fun groupListDBRefMapper(doc: Document): Flux<Document> {
        val dbRefs = doc.get("groups", ArrayList<DBRef>())
        return if (dbRefs.isEmpty()) {
            Flux.just(doc)
                .map { it }
        } else {
            val q = Query.query(Criteria.where("_id").`in`(dbRefs.map { it.id }))
            Flux.just(doc)
                .zipWith(template.find(q, Document::class.java, dbRefs.get(0).collectionName))
                .map {
                    it.t1["groups"] = it.t2
                    return@map it.t1
                }
        }
    }

    private fun dBRefMapper(doc: Document, fieldName: String): Mono<Document> {
        val dbRef = doc.get(fieldName, DBRef::class.java)
        return if (dbRef == null) {
            Mono.just(doc)
                .map { it }
        } else {
            Mono.just(doc)
                .zipWith(template.findById(dbRef.id, Document::class.java, dbRef.collectionName))
                .map {
                    it.t1[fieldName] = it.t2
                    return@map it.t1
                }
        }
    }


}





