package ua.com.foxminded.restClient.aspects

import lombok.extern.slf4j.Slf4j
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
@Slf4j
class LoggingAspect {
    private val STUDENT_STRING = "student"
    private val TEACHER_STRING = "teacher"
    private val SCHEDULE_STRING = "schedule"

    @Pointcut("execution(* *..findAll*(*))")
    fun findAllPointcut() {
    }

    @get:Pointcut("execution(* *..getRate*(*))")
    val ratePointcut: Unit
        get() {}

    @Around("findAllPointcut()")
    @Throws(Throwable::class)
    fun logAroundFindAll(joinPoint: ProceedingJoinPoint): Any {
        val args = joinPoint.args
        log.info(
            "Find transactions with person ID {} between {} and {}.\nPageable={}",
            args[0],
            args[1],
            args[2],
            args[3]
        )
        val result = joinPoint.proceed()
        log.info("Query result:\n{}", result.toString())
        return result
    }

    @Around("getRatePointcut()")
    @Throws(Throwable::class)
    fun logAroundGetRate(joinPoint: ProceedingJoinPoint): Any {
        val args = joinPoint.args
        log.info("Get currency rate between {} and {}", args[0].toString(), args[1].toString())
        val result = joinPoint.proceed()
        log.info("Query result:\n{}", result.toString())
        return result
    }

    @AfterThrowing(pointcut = "findAllPointcut() || getRatePointcut()", throwing = "e")
    fun logAfterThrowing(joinPoint: JoinPoint, e: Throwable) {
        log.error(
            "Exception in {}.{}(). \nException: {} \nMessage: {} \nCause = {}.", joinPoint.signature.declaringTypeName,
            joinPoint.signature.name, e.javaClass, e.message, if (e.cause != null) e.cause else "NULL"
        )
    }
}