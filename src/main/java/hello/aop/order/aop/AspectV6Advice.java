package hello.aop.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
public class AspectV6Advice {


    @Around("hello.aop.order.aop.Pointcuts.allOrder()") /*pointcut setting*/
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }


    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint jointPoint) throws Throwable {


        try {
            // @Before
            log.info("[트랜잭션 시작] {}", jointPoint.getSignature());
            Object result = jointPoint.proceed();
            // @AfterReturning
            log.info("[트랜잭션 커밋] {}", jointPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}", jointPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[리소스 릴리즈] {}", jointPoint.getSignature());
        }
    }

    /*
        joinPoint( = 프록시가 아닌 구현체 자체 로직)의 실행 이전까지 로직이 실행된다
        이후 실구현체 로직인 joinPoint 실행 로직은 알아서 실행된다
    */
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    /*
        joinPoint( = 프록시가 아닌 구현체 자체 로직)의 실행 이후 로직이 실행된다
        value 부분에 적은 returnin 변수값과 파라미터로 넘어온 result 값을 동일하게 명시해줘야한다.
     */
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return = {}", joinPoint.getSignature(), result);
    }

    /*
        joinPoint( = 프록시가 아닌 구현체 자체 로직)의 실행 중 예외 발생 시 실행된다
        value 부분에 적은 throwing 변수값과 파라미터로 넘어온 Exception 값을 동일하게 명시해줘야한다.
     */
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doReturn(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message = {}", joinPoint.getSignature(), ex);
    }

    /*
        joinPoint( = 프록시가 아닌 구현체 자체 로직)의 실행 성공 or 예외 발생 시 모두에 대해 실행된다
        value 부분에 적은 throwing 변수값과 파라미터로 넘어온 Exception 값을 동일하게 명시해줘야한다.
     */
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doReturn(JoinPoint joinPoint) {
        log.info("[after] {} message = {}", joinPoint.getSignature());
    }

}
