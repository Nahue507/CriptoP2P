package ar.edu.unq.desaap.grupoj.backenddesappapi.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class LogExecutionTimeAspectAnnotation {

    protected final Log logger = LogFactory.getLog(getClass());

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTimeAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("/////// AROUND START //////");
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;
        logger.info("/////// " + joinPoint.getSignature() + " executed in " + executionTime + "ms");
        logger.info("/////// AROUND FINISH ///////");
        return proceed;
    }

}