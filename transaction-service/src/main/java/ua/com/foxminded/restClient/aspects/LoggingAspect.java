package ua.com.foxminded.restClient.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LoggingAspect {


    private final String STUDENT_STRING = "student";
    private final String TEACHER_STRING = "teacher";
    private final String SCHEDULE_STRING = "schedule";

    @Pointcut("execution(* *..findAll*(*))")
    public void findAllPointcut() {
    }

    @Pointcut("execution(* *..getRate*(*))")
    public void getRatePointcut() {
    }


    @Around("findAllPointcut()")
    public Object logAroundFindAll(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        log.info("Find transactions with person ID {} between {} and {}.\nPageable={}", args[0], args[1], args[2], args[3]);
        Object result = joinPoint.proceed();
        log.info("Query result:\n{}", result.toString());
        return result;
    }

    @Around("getRatePointcut()")
    public Object logAroundGetRate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        log.info("Get currency rate between {} and {}", args[0].toString(), args[1].toString());
        Object result = joinPoint.proceed();
        log.info("Query result:\n{}", result.toString());
        return result;
    }

    @AfterThrowing(pointcut = "findAllPointcut() || getRatePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}(). \nException: {} \nMessage: {} \nCause = {}.", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getClass(), e.getMessage(), e.getCause() != null ? e.getCause() : "NULL");
    }

}
