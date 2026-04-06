package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    //hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {} //pointcut signature으로 부름 (메서드 이름 + 포인트컷 이름 = pointcut signature)

    //클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {} //pointcut signature으로 부름 (메서드 이름 + 포인트컷 이름 = pointcut signature)

    // allOrder하고 allService 조합
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}


}
