package ua.com.foxminded.courseproject.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.DaySchedule
import ua.com.foxminded.courseproject.mapper.StudentMapper


@Repository
class WeekScheduleRepositoryImp(
    @Autowired var template: ReactiveMongoTemplate,
    @Autowired val mapper: StudentMapper
) :
    WeekScheduleRepository {
    private val COLLECTION_NAME = "weeks"
    override fun findDayScheduleByDayNumberFromOddWeek(number: Int): Mono<DaySchedule> {
        TODO("Not yet implemented")
    }

    override fun findDayScheduleByDayNumberFromEvenWeek(number: Int): Mono<DaySchedule> {
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





