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
import java.util.UUID


@Repository
class WeekScheduleRepositoryImp(
    @Autowired var template: ReactiveMongoTemplate,
    @Autowired val dayScheduleMapper: DayScheduleMapper,
    @Autowired val lessonRepository: LessonRepository
) :
    WeekScheduleRepository {
    private val COLLECTION_NAME = "weeks"

    override fun findDayScheduleByDayNumberAndOddWeek(number: Int, odd: Boolean): Mono<DaySchedule?> {
        val matchOdd = Aggregation.match(Criteria("odd").`is`(odd))
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
            .flatMap { dayScheduleDBRefsMapper(it) }
            .map { dayScheduleMapper.documentToEntity(it) }
            .toMono()
    }

    private fun dayScheduleDBRefsMapper(doc: Document): Flux<Document> {
        val dbRefs = doc.get("lessons", ArrayList<DBRef>())
        return if (dbRefs.isEmpty()) {
            Flux.just(doc)
                .map { it }
        } else {
            Flux.just(doc)
                .zipWith(
                    lessonRepository.findAllById(dbRefs.map { UUID.fromString(it.id.toString()) }.toMutableList())
                        .collectList()
                )
                .map {
                    it.t1["lessons"] = it.t2
                    return@map it.t1
                }
        }
    }


}





