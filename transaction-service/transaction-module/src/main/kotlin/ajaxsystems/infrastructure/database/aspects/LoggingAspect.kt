package ajaxsystems.infrastructure.database.aspects

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {
    val log: Logger = LoggerFactory.getLogger(LoggingAspect::class.java)

    @Suppress("EmptyFunctionBlock")
    @Pointcut("execution(* findAll*(..))")
    fun findAllPointcut() {
    }

    @Suppress("EmptyFunctionBlock")
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
                args[PERSON_ID_NUMBER],
                args[START_DATE_NUMBER],
                args[END_DATE_NUMBER],
                args[PAGEABLE_NUMBER]
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

    companion object {
        const val PERSON_ID_NUMBER = 0
        const val START_DATE_NUMBER = 1
        const val END_DATE_NUMBER = 2
        const val PAGEABLE_NUMBER = 3
    }
}
