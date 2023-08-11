package ua.com.foxminded.courseproject.repository

import com.mongodb.DBRef
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Lesson
import ua.com.foxminded.courseproject.entity.Subject
import ua.com.foxminded.courseproject.mapper.LessonMapper
import java.util.*

@Repository
class LessonRepositoryImp(
    @Autowired var template: ReactiveMongoTemplate,
    @Autowired val mapper: LessonMapper,
    @Autowired val subjectRepository: SubjectRepository,
    @Autowired val classroomRepository: ClassroomRepository,
    @Autowired val groupRepository: GroupRepository,
    @Autowired val teacherRepository: TeacherRepository
) :
    LessonRepository {
    private val COLLECTION_NAME = "lessons"
    override fun findById(id: UUID): Mono<Lesson> {
        return template
            .findById(id.toString(), Document::class.java, COLLECTION_NAME)
            .flatMap { dBRefMapper(it, subjectRepository, "subject") }
            .flatMap { dBRefMapper(it, classroomRepository, "classroom") }
            .flatMap { dBRefMapper(it, teacherRepository, "teacher") }
            .flatMap { listDBRefMapper(it, groupRepository, "groups") }
            .map { mapper.documentToEntity(it) }
    }

    override fun findAllById(ids: MutableIterable<UUID>): Flux<Lesson> {
        val q = Query.query(Criteria.where("_id").`in`(ids.map { it.toString() }))
        return template.find(q, Document::class.java, COLLECTION_NAME)
            .flatMap { dBRefMapper(it, subjectRepository, "subject") }
            .flatMap { dBRefMapper(it, classroomRepository, "classroom") }
            .flatMap { dBRefMapper(it, teacherRepository, "teacher") }
            .flatMap { listDBRefMapper(it, groupRepository, "groups") }
            .map { mapper.documentToEntity(it) }
    }

    private fun <T> dBRefMapper(
        doc: Document,
        repository: ReactiveSortingRepository<T, UUID>,
        fieldName: String
    ): Mono<Document> {
        val dbRef = doc.get(fieldName, DBRef::class.java)
        return if (dbRef == null) {
            Mono.just(doc)
                .map { it }
        } else {
            Mono.just(doc)
                .zipWith(repository.findById(UUID.fromString(dbRef.id.toString())))
                .map {
                    it.t1[fieldName] = it.t2
                    return@map it.t1
                }
        }
    }

    private fun <T> listDBRefMapper(
        doc: Document,
        repository: ReactiveSortingRepository<T, UUID>,
        fieldName: String
    ): Mono<Document> {
        val dbRefs = doc.get(fieldName, ArrayList<DBRef>())
        return if (dbRefs.isEmpty()) {
            Mono.just(doc)
                .map { it }
        } else {
            Mono.just(doc)
                .zipWith(repository.findAllById(dbRefs.map { UUID.fromString(it.id.toString()) }).collectList())
                .map {
                    it.t1["groups"] = it.t2
                    return@map it.t1
                }
        }
    }


}







