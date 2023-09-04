package ua.com.foxminded.courseproject.aspects

import java.util.Arrays
import java.util.Locale
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
@Suppress("TooManyFunctions")
class LoggingAspect {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Suppress("EmptyFunctionBlock")
    @Pointcut("execution(* findById*(..))")
    fun findByIdPointcut() {
    }

    @Suppress("EmptyFunctionBlock")
    @Pointcut("execution(* findDay*(..))")
    fun findDayPointcut() {
    }

    @Suppress("EmptyFunctionBlock")
    @Pointcut("execution(* findAll*(..))")
    fun findAllPointcut() {
    }

    @Suppress("EmptyFunctionBlock")
    @Pointcut("execution(* save(..))")
    fun savePointcut() {
    }

    @Suppress("EmptyFunctionBlock")
    @Pointcut("execution(* delete(..))")
    fun deletePointcut() {
    }

    @Suppress("EmptyFunctionBlock")
    @Pointcut("execution(* exists*(String,..))")
    fun existCheckPointcut() {
    }

    @Suppress("EmptyFunctionBlock")
    @Pointcut("@within(org.springframework.stereotype.Repository)")
    fun repositoryPointcut() {
    }

    @Suppress("EmptyFunctionBlock")
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
        val args = joinPoint.args
        if (args.isNotEmpty()) {
            logger.info(
                "Find all {}s in DB with Pageble parameters= {}",
                chooseStringObject(joinPoint),
                Arrays.stream(joinPoint.args).findAny().get()
            )
        } else {
            logger.info("Find all {}s in DB", chooseStringObject(joinPoint))
        }
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

    companion object {
        const val SCHEDULE_STRING = "schedule"
        const val STUDENT_STRING = "student"
        const val TEACHER_STRING = "teacher"
    }
}
