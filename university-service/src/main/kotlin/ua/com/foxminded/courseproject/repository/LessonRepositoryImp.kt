package ua.com.foxminded.courseproject.repository

import com.mongodb.DBRef
import java.util.UUID
import org.bson.Document
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Group
import ua.com.foxminded.courseproject.entity.Lesson
import ua.com.foxminded.courseproject.mapper.LessonMapper

@Repository
class LessonRepositoryImp(
    var template: ReactiveMongoTemplate,
    val mapper: LessonMapper,
    val subjectRepository: SubjectRepository,
    val classroomRepository: ClassroomRepository,
    val groupRepository: GroupRepository,
    val teacherRepository: TeacherRepository
) : LessonRepository {

    override fun findById(id: UUID): Mono<Lesson> {
        return template
            .findById(id.toString(), Document::class.java, Companion.COLLECTION_NAME)
            .flatMap { dBRefMapper(it, subjectRepository, "subject") }
            .flatMap { dBRefMapper(it, classroomRepository, "classroom") }
            .flatMap { dBRefMapper(it, teacherRepository, "teacher") }
            .flatMap { listDBRefMapper(it, groupRepository, "groups") }
            .map { mapper.documentToEntity(it) }
    }

    override fun findAllById(ids: MutableIterable<UUID>): Flux<Lesson> {
        val q = Query.query(Criteria.where("_id").`in`(ids.map { it.toString() }))
        return template.find(q, Document::class.java, Companion.COLLECTION_NAME)
            .flatMap { dBRefMapper(it, subjectRepository, "subject") }
            .flatMap { dBRefMapper(it, classroomRepository, "classroom") }
            .flatMap { dBRefMapper(it, teacherRepository, "teacher") }
            .flatMap { listDBRefMapper(it, groupRepository, "groups") }
            .map { mapper.documentToEntity(it) }

    }

    private fun <T> dBRefMapper(
        doc: Document,
        repository: ReactiveCrudRepository<T, UUID>,
        fieldName: String
    ): Mono<Document> {
        val dbRef = doc.get(fieldName, DBRef::class.java)
        return if (dbRef.id == "null") {
            Mono.just(doc)
                .map { it }
        } else {
            repository
                .findById(UUID.fromString(dbRef.id.toString()))
                .map {
                    doc[fieldName] = it
                    return@map doc
                }
        }
    }

    private fun <T> listDBRefMapper(
        doc: Document,
        repository: ReactiveCrudRepository<T, UUID>,
        fieldName: String
    ): Mono<Document> {
        val dbRefs = doc.get(fieldName, ArrayList<DBRef>())
        return if (dbRefs.isEmpty()) {
            Mono.just(doc)
                .map {
                    it["groups"] = emptyList<Group>()
                    return@map it
                }
        } else {
            Mono.just(doc)
                .zipWith(repository.findAllById(dbRefs.map { UUID.fromString(it.id.toString()) }).collectList())
                .map {
                    it.t1["groups"] = it.t2
                    return@map it.t1
                }
        }
    }

    companion object {
        const val COLLECTION_NAME = "lessons"
    }


}
