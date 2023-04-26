package ua.com.foxminded.courseproject.aspects

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Aspect
@Component
class LoggingAspect {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val STUDENT_STRING = "student"
    private val TEACHER_STRING = "teacher"
    private val SCHEDULE_STRING = "schedule"
    @Pointcut("execution(* *..findById(*))")
    fun findByIdPointcut() {
    }

    @Pointcut("execution(* *..findAll(*))")
    fun findAllPointcut() {
    }

    @Pointcut("execution(* *..save(*))")
    fun savePointcut() {
    }

    @Pointcut("execution(* *..delete(*))")
    fun deletePointcut() {
    }

    @Pointcut("execution(* *..exists*(String,..))")
    fun existCheckPointcut() {
    }

    @Pointcut("@within(org.springframework.stereotype.Repository)")
    fun repositoryPointcut() {
    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    fun servicePointcut() {
    }

    @Around("findByIdPointcut() && repositoryPointcut()")
    @Throws(Throwable::class)
    fun logAroundFindBy(joinPoint: ProceedingJoinPoint): Any {
        logger.info(
            "Find {} in DB with ID= {}",
            chooseStringObject(joinPoint),
            Arrays.stream(joinPoint.args).findAny().get()
        )
        val result = joinPoint.proceed()
        logger.info("Query result:\n{}", result.toString())
        return result
    }

    @Around("findAllPointcut() && repositoryPointcut()")
    @Throws(Throwable::class)
    fun logAroundFindAll(joinPoint: ProceedingJoinPoint): Any {
        logger.info(
            "Find all {}s in DB with Pageble parameters= {}",
            chooseStringObject(joinPoint),
            Arrays.stream(joinPoint.args).findAny().get()
        )
        val result = joinPoint.proceed()
        logger.info("Query result:\n{}", result.toString())
        return result
    }

    @Around("savePointcut() && repositoryPointcut()")
    @Throws(Throwable::class)
    fun logAroundSave(joinPoint: ProceedingJoinPoint): Any {
        logger.info("Save {} to DB.\n{}", chooseStringObject(joinPoint), Arrays.stream(joinPoint.args).findAny().get())
        val result = joinPoint.proceed()
        logger.info("Query result:\n{}", result.toString())
        return result
    }

    @Around("deletePointcut() && repositoryPointcut()")
    @Throws(Throwable::class)
    fun logAroundDelete(joinPoint: ProceedingJoinPoint): Any? {
        logger.info(
            "Delete {} from DB.\n{}",
            chooseStringObject(joinPoint),
            Arrays.stream(joinPoint.args).findAny().get()
        )
        val result = joinPoint.proceed()
        logger.info("Deleted")
        return result
    }

    @Around("existCheckPointcut() && repositoryPointcut()")
    @Throws(Throwable::class)
    fun logAroundExistsCheck(joinPoint: ProceedingJoinPoint): Any {
        logger.info(
            "Check if {} exists.\n firstname={}, lastname={}, birthDay={}",
            chooseStringObject(joinPoint), joinPoint.args[0], joinPoint.args[1], joinPoint.args[2]
        )
        val result = joinPoint.proceed()
        logger.info(
            "{} is existed: {}",
            chooseStringObject(joinPoint),
            result.toString().uppercase(Locale.getDefault())
        )
        return result
    }

    @AfterThrowing(pointcut = "repositoryPointcut() || servicePointcut()", throwing = "e")
    fun logAfterThrowing(joinPoint: JoinPoint, e: Throwable) {
        logger.error(
            "Exception in {}.{}(). \nException: {} \nMessage: {} \nCause = {}.", joinPoint.signature.declaringTypeName,
            joinPoint.signature.name, e.javaClass, e.message, if (e.cause != null) e.cause else "NULL"
        )
    }

    private fun chooseStringObject(joinPoint: ProceedingJoinPoint): String {
        var result = ""
        val jpString = joinPoint.toLongString()
        if (jpString.contains("Student")) {
            result = STUDENT_STRING
        }
        if (jpString.contains("Teacher")) {
            result = TEACHER_STRING
        }
        if (jpString.contains("Schedule")) {
            result = SCHEDULE_STRING
        }
        return result
    }
}
