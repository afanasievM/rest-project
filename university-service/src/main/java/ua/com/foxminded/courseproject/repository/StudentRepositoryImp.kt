package ua.com.foxminded.courseproject.repository

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.databind.util.JSONWrappedObject
import com.google.gson.Gson
import com.mongodb.DBRef
import org.bson.Document
import org.bson.conversions.Bson
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.json.GsonJsonParser
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.convert.MongoTypeMapper
import org.springframework.data.mongodb.core.findAll
import org.springframework.data.mongodb.core.findOne
import org.springframework.data.mongodb.util.BsonUtils
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Group
import ua.com.foxminded.courseproject.entity.Student
import ua.com.foxminded.courseproject.mapper.StudentMapper
import java.time.LocalDate
import java.util.*
import javax.print.Doc

@Repository
@Primary
class StudentRepositoryImp(@Autowired var template: ReactiveMongoTemplate, @Autowired val mapper: StudentMapper) :
    ReactiveSortingRepository<Student, UUID> {


    override fun findById(id: UUID): Mono<Student> {
        TODO("Not yet implemented")
    }


    override fun findAll(): Flux<Student> {
        return template.findAll(Document::class.java, "students")
            .flatMap { doc ->
                val dbRef = doc.get("group_id", DBRef::class.java)
                Mono.just(doc)
                    .zipWith(template.findById(dbRef.id, Group::class.java, dbRef.collectionName))
                    .map {
                        it.t1["group"] = it.t2
                        it.t1.remove("group_id")
                        return@map mapper.documentToEntity(it.t1)
                    }
            }
            .log()

    }

    override fun findById(id: Publisher<UUID>): Mono<Student> {
        TODO("Not yet implemented")
    }

    override fun findAll(sort: Sort): Flux<Student> {
        TODO("Not yet implemented")
    }

//    override fun save(student: Student): Mono<Student> {
//        TODO("Not yet implemented")
//    }

    override fun <S : Student?> save(entity: S): Mono<S> {
        TODO("Not yet implemented")
    }

    override fun delete(student: Student): Mono<Void> {
        TODO("Not yet implemented")
    }

    fun existsStudentByFirstNameAndLastNameAndBirthDay(
        firstName: String,
        lastName: String,
        birthDay: LocalDate
    ): Mono<Boolean> {
        TODO("Not yet implemented")
    }

    override fun <S : Student?> saveAll(entities: MutableIterable<S>): Flux<S> {
        TODO("Not yet implemented")
    }

    override fun <S : Student?> saveAll(entityStream: Publisher<S>): Flux<S> {
        TODO("Not yet implemented")
    }

    override fun existsById(id: UUID): Mono<Boolean> {
        TODO("Not yet implemented")
    }

    override fun existsById(id: Publisher<UUID>): Mono<Boolean> {
        TODO("Not yet implemented")
    }

    override fun findAllById(ids: MutableIterable<UUID>): Flux<Student> {
        TODO("Not yet implemented")
    }

    override fun findAllById(idStream: Publisher<UUID>): Flux<Student> {
        TODO("Not yet implemented")
    }

    override fun count(): Mono<Long> {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: UUID): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Publisher<UUID>): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteAllById(ids: MutableIterable<UUID>): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(entities: MutableIterable<Student>): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(entityStream: Publisher<out Student>): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Mono<Void> {
        TODO("Not yet implemented")
    }
}