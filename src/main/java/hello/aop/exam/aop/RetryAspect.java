package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    /**
     *   @포인트컷 포인트컷 설정에 대한 설명
     *
     *   @Around("@annotation(hello.aop.exam.annotation.Retry)") 1) 해당 방신은 포인트컷의 위치만 지정
     *   @Around("@annotation(retry)") 2) 해당 방식으로 포인트컷을 지정하면 <br>
     *   public Object doRetry(..., Retry retry)  <br>이 파라미터로 받은 retry 객체에 정보가 담김
     *   <br> 이처럼 파라미터로 retry 객체를 받아 객체에 담긴 정보를 활용 가능하다.
     *
     * */

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {

        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        Exception exceptionHolder = null;

        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] try count={}/{}", retryCount, maxRetry);
                return joinPoint.proceed();
            }
            catch (Exception e) {
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;

    }
}
