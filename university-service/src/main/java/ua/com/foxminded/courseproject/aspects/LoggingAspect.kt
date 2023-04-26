package ua.com.foxminded.courseproject.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String STUDENT_STRING = "student";
    private final String TEACHER_STRING = "teacher";
    private final String SCHEDULE_STRING = "schedule";

    @Pointcut("execution(* *..findById(*))")
    public void findByIdPointcut() {
    }
    @Pointcut("execution(* *..findAll(*))")
    public void findAllPointcut() {
    }

    @Pointcut("execution(* *..save(*))")
    public void savePointcut() {
    }

    @Pointcut("execution(* *..delete(*))")
    public void deletePointcut() {
    }

    @Pointcut("execution(* *..exists*(String,..))")
    public void existCheckPointcut() {
    }

    @Pointcut("@within(org.springframework.stereotype.Repository)")
    public void repositoryPointcut() {
    }


    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void servicePointcut() {
    }

    @Around("findByIdPointcut() && repositoryPointcut()")
    public Object logAroundFindBy(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Find {} in DB with ID= {}", chooseStringObject(joinPoint), Arrays.stream(joinPoint.getArgs()).findAny().get());
        Object result = joinPoint.proceed();
        logger.info("Query result:\n{}", result.toString());
        return result;
    }

    @Around("findAllPointcut() && repositoryPointcut()")
    public Object logAroundFindAll(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Find all {}s in DB with Pageble parameters= {}", chooseStringObject(joinPoint), Arrays.stream(joinPoint.getArgs()).findAny().get());
        Object result = joinPoint.proceed();
        logger.info("Query result:\n{}", result.toString());
        return result;
    }

    @Around("savePointcut() && repositoryPointcut()")
    public Object logAroundSave(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Save {} to DB.\n{}", chooseStringObject(joinPoint), Arrays.stream(joinPoint.getArgs()).findAny().get());
        Object result = joinPoint.proceed();
        logger.info("Query result:\n{}", result.toString());
        return result;
    }

    @Around("deletePointcut() && repositoryPointcut()")
    public Object logAroundDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Delete {} from DB.\n{}", chooseStringObject(joinPoint), Arrays.stream(joinPoint.getArgs()).findAny().get());
        Object result = joinPoint.proceed();
        logger.info("Deleted");
        return result;
    }

    @Around("existCheckPointcut() && repositoryPointcut()")
    public Object logAroundExistsCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Check if {} exists.\n firstname={}, lastname={}, birthDay={}",
                chooseStringObject(joinPoint), joinPoint.getArgs()[0], joinPoint.getArgs()[1], joinPoint.getArgs()[2]);
        Object result = joinPoint.proceed();
        logger.info("{} is existed: {}",chooseStringObject(joinPoint), result.toString().toUpperCase());
        return result;
    }

    @AfterThrowing(pointcut = "repositoryPointcut() || servicePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.error("Exception in {}.{}(). \nException: {} \nMessage: {} \nCause = {}.", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getClass(), e.getMessage(), e.getCause() != null ? e.getCause() : "NULL");
    }

    private String chooseStringObject(ProceedingJoinPoint joinPoint) {
        String result = "";
        String jpString = joinPoint.toLongString();
        if (jpString.contains("Student")){
            result = STUDENT_STRING;
        }
        if (jpString.contains("Teacher")){
            result = TEACHER_STRING;
        }
        if (jpString.contains("Schedule")){
            result = SCHEDULE_STRING;
        }
        return result;
    }

}
