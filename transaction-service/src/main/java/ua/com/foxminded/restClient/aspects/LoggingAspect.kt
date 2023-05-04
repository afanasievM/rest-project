package ua.com.foxminded.restClient.aspects

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
class LoggingAspect {
    private val log = LoggerFactory.getLogger(LoggingAspect::class.java)
    private val STUDENT_STRING = "student"
    private val TEACHER_STRING = "teacher"
    private val SCHEDULE_STRING = "schedule"

    @Pointcut("execution(* findAll*(..))")
    fun findAllPointcut() {
    }

    @Pointcut("execution(* chooseRate(..))")
    fun ratePointcut() {
    }

    @Around("findAllPointcut()")
    @Throws(Throwable::class)
    fun logAroundFindAll(joinPoint: ProceedingJoinPoint): Any {
        val args = joinPoint.args
        if (args.isNotEmpty()) {
            log.info(
                "Find transactions with person ID {} between {} and {}.\nPageable={}",
                args[0],
                args[1],
                args[2],
                args[3]
            )
        }
        val result = joinPoint.proceed()
        log.info("Query result:\n{}", result.toString())
        return result
    }

    @Around("ratePointcut()")
    @Throws(Throwable::class)
    fun logAroundGetRate(joinPoint: ProceedingJoinPoint): Any {
        val args = joinPoint.args
        println(args)
        log.info("Get currency rate between {} and {}", args[0].toString(), args[1].toString())
        val result = joinPoint.proceed()
        log.info("Query result:\n{}", result.toString())
        return result
    }

    @AfterThrowing(pointcut = "findAllPointcut() || ratePointcut()", throwing = "e")
    fun logAfterThrowing(joinPoint: JoinPoint, e: Throwable) {
        log.error(
            "Exception in {}.{}(). \nException: {} \nMessage: {} \nCause = {}.", joinPoint.signature.declaringTypeName,
            joinPoint.signature.name, e.javaClass, e.message, if (e.cause != null) e.cause else "NULL"
        )
    }
}