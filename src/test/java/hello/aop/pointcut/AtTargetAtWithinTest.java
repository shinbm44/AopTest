package hello.aop.pointcut;

import hello.aop.order.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


@Slf4j
@Import({AtTargetAtWithinTest.Config.class})
@SpringBootTest
public class AtTargetAtWithinTest {

    /**
     * @target 의 조인포인트 방식에 대한 정리
     * - 실행 객체의 클래스에 주어진 타입의 애노테이션이 있는 조인 포인트
     * - 인스턴스의 모든 메서드를 조인 포인트로 적용
     * - 부모 객체의 메서드까지 어드바이스를 다 적용
     * @within 의 조인포인트 방식에 대한 정리
     * - 주어진 애노테이션이 있는 타입 내 조인 포인트
     * - 해당 타입 내에 있는 메서드만 조인트로 적용
     * - 해당 타입 내의 메서드까지 어드바이스를 다 적용
     */

    @Autowired
    Child child;

    @Test
    void success() {
        log.info("child Proxy={}", child.getClass());
        child.childMethod(); //부모, 자식 모두 있는 메서드
        child.parentMethod(); //부모 클래스만 있는 메서드
    }

    static class Config {
        @Bean
        public Parent parent() {
            return new Parent();
        }

        @Bean
        public Child child() {
            return new Child();
        }

        @Bean
        public AtTargetAtWithinAspect atTargetAtWithinAspect() {
            return new AtTargetAtWithinAspect();
        }
    }

    static class Parent {
        public void parentMethod() {
        } //부모에만 있는 메서드
    }

    @ClassAop
    static class Child extends Parent {
        public void childMethod() {
        }
    }
    /**
    *    @target — 런타임 인스턴스(child)의 클래스에 @ClassAop가 있으면 적용. child는 Child 인스턴스이고 Child에 @ClassAop가
    *    있으므로, 어떤 메서드를 호출하든 적용.
    *    @within — 메서드가 정의된 클래스에 @ClassAop가 있어야 적용. parentMethod()는 Parent 클래스에 정의되어 있고 Parent에는 @ClassAop가 없으므로, 적용 x
    *
    *    @즉, @within은 "어떤 인스턴스가 호출했냐"가 아니라 "메서드가 어느 클래스에 선언됐냐"를 기준으로 판단하기 때문에
    *    child.parentMethod()에는 어드바이스가 걸리지 않는다.
    **/

    @Slf4j
    @Aspect
    static class AtTargetAtWithinAspect {
        //@target: 인스턴스 기준으로 모든 메서드의 조인 포인트를 선정, 부모 타입의 메서드도 적용
        @Around("execution(* hello.aop..*(..)) && @target(hello.aop.order.member.annotation.ClassAop)")
        public Object atTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@target] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //@within: 선택된 클래스 내부에 있는 메서드만 조인 포인트로 선정, 부모 타입의 메서드는 적용되지 않음
        @Around("execution(* hello.aop..*(..)) && @within(hello.aop.order.member.annotation.ClassAop)")
        public Object atWithin(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@within] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

}




