package hello.aop.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    //hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {} //pointcut signature으로 부름 (메서드 이름 + 포인트컷 이름 = pointcut signature)

    //클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    private void allOService() {} //pointcut signature으로 부름 (메서드 이름 + 포인트컷 이름 = pointcut signature)


    @Around("allOrder()") /*pointcut setting*/
    public Object doLog (ProceedingJoinPoint joinPoint) throws Throwable {
        log.info ("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    // hello.aop.order 패키지의 하위 패키지이면서 클래스 패턴 이름이 *Service인 경우
    // 트랜잭션을 가장한 로직
    @Around("allOrder() && allOService()")
    public Object doTransaction(ProceedingJoinPoint jointPoint) throws Throwable {


        try {
            log.info("[트랜잭션 시작] {}", jointPoint.getSignature());
            Object result = jointPoint.proceed();
            log.info("[트랜잭션 커밋] {}", jointPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", jointPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", jointPoint.getSignature());
        }
    }

}
