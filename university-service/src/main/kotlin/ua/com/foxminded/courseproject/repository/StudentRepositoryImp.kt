package ua.com.foxminded.courseproject.repository

import com.mongodb.DBRef
import java.time.LocalDate
import java.util.UUID
import org.bson.Document
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Student
import ua.com.foxminded.courseproject.mapper.StudentMapper

@Repository
class StudentRepositoryImp(
    var template: ReactiveMongoTemplate,
    val groupRepository: GroupRepository,
    val mapper: StudentMapper
) : StudentRepository {
    override fun findById(id: UUID): Mono<Student> {
        return template
            .findById(id.toString(), Document::class.java, Companion.COLLECTION_NAME)
            .flatMap { studentDBRefsMapper(it) }
            .map { mapper.documentToEntity(it) }
    }


    override fun findAll(): Flux<Student> {
        return template.findAll(Document::class.java, Companion.COLLECTION_NAME)
            .flatMap { studentDBRefsMapper(it) }
            .map { mapper.documentToEntity(it) }
    }

    override fun save(student: Student): Mono<Student> {
        return template.save(mapper.entityToDocument(student), Companion.COLLECTION_NAME)
            .flatMap { studentDBRefsMapper(it) }
            .map { mapper.documentToEntity(it) }
    }

    override fun delete(student: Student): Mono<Void> {
        val query = Query()
        query.addCriteria(Criteria.where("_id").`is`(student.id.toString()))
        return template.remove(query, Companion.COLLECTION_NAME)
            .then()
    }

    override fun existsStudentByFirstNameAndLastNameAndBirthDay(
        firstName: String,
        lastName: String,
        birthDay: LocalDate
    ): Mono<Boolean> {
        val query = Query()
        query.addCriteria(
            Criteria
                .where("birthday").`is`(birthDay)
                .and("firstname").`is`(firstName)
                .and("lastname").`is`(lastName)
        )
        return template.exists(query, Companion.COLLECTION_NAME)
    }

    private fun studentDBRefsMapper(doc: Document): Mono<Document> {
        val dbRef = doc.get("group_id", DBRef::class.java)
        return if (dbRef.id == "null") {
            Mono.just(doc)
                .map { it }
        } else {
            Mono.just(doc)
                .zipWith(groupRepository.findById(UUID.fromString(dbRef.id.toString())))
                .map {
                    it.t1["group"] = it.t2
                    it.t1.remove("group_id")
                    return@map it.t1
                }
        }
    }

    companion object {
        const val COLLECTION_NAME = "students"
    }
}
