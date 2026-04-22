package hello.aop.pointcut;


import hello.aop.order.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;


    /*
    * method 정보 뽑기
    * ' hello 메소드의 String 파라미터 타입 ' 정보인 helloMethod 메소드 정보 추출
    * */
    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    @DisplayName("메소드 정보 확인")
    void printMethod() {
        // public java.lang.String hello.aop.order.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    /*
    1. execution pointcut 사용하기
    - 가장 정확하게 매칭되는 case ( helloMethod 랑 )
     */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 1")
    void exactMatch() {

        pointcut.setExpression("execution(public String hello.aop.order.member.MemberServiceImpl.hello(String))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    2. execution pointcut 사용하기
    - 가장 간단하게 매칭하는[생략이 가장 많은 / 전부 만족하는 조건] case ( helloMethod 랑 )
     */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 2")
    void allMatch() {

        pointcut.setExpression("execution(* *(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    3. execution pointcut 사용하기
    - 이름을 기반으로 매칭하는 case ( helloMethod 랑 )
     */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 3")
    void nameMatch1() {

        pointcut.setExpression("execution(* hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    4. execution pointcut 사용하기
    - 이름을 기반[패턴기반]으로 매칭하는 case ( helloMethod 랑 )
     */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 4")
    void nameMatch2() {

        pointcut.setExpression("execution(* hel*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    5. execution pointcut 사용하기
    - 이름을 기반[패턴기반]으로 매칭하는 case ( helloMethod 랑 )
     */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 5")
    void nameMatch3() {

        pointcut.setExpression("execution(* *el*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    6. execution pointcut 사용하기
    - 이름을 기반[패턴기반]으로 매칭 실패하는 case ( helloMethod 랑 )
     */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 6")
    void nameMatch4() {

        pointcut.setExpression("execution(* no(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    /*
    7. execution pointcut 사용하기
    - 패키지 정보로 매칭하는 case ( helloMethod 랑 )
     */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 7")
    void packageMatch1() {

        pointcut.setExpression("execution(* hello.aop.order.member.MemberServiceImpl.hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    8. execution pointcut 사용하기
   - 패키지 정보[패턴 사용]로 매칭하는 case ( helloMethod 랑 )
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 8")
    void packageMatch2() {

        pointcut.setExpression("execution(* hello.aop.order.member.*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    9. execution pointcut 사용하기
    - 패키지 정보[패턴 사용]로 매칭에 실패하는 case ( helloMethod 랑 )
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 9")
    void packageMatchFalse() {

        pointcut.setExpression("execution(* hello.aop.order.*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }


    /*
    10. execution pointcut 사용하기
    - 패키지 정보[패턴 사용]로 매칭하는 case ( helloMethod 랑 )
    - 해당 패키지 아래 존재하는 모든 요소들이 전부 포인트 컷 대상
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 10")
    void packageMatchSubPackage1() {

        pointcut.setExpression("execution(* hello.aop.order.member..*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    11. execution pointcut 사용하기
    - 패키지 정보[패턴 사용]로 매칭하는 case ( helloMethod 랑 )
    - 해당 패키지 아래 존재하는 모든 요소들이 전부 포인트 컷 대상
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 11")
    void packageMatchSubPackage2() {

        pointcut.setExpression("execution(* hello.aop.order..*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    12. execution pointcut 사용하기
    - 타입 정보로 매칭하는 case ( helloMethod 랑 )
    - pointcut 내에 존재하는 타입 정보와 매칭시킨 요소들이 포인트 컷 대상
    - 메소드(helloMethod)의 객체 타입과 포인트컷 대상의 타입이 정확하게 일치하는 경우
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 12")
    void typeExactMatch() {

        pointcut.setExpression("execution(* hello.aop.order.member.MemberServiceImpl.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    13. execution pointcut 사용하기
    - 타입 정보로 매칭하는 case ( helloMethod 랑 )
    - pointcut 내에 존재하는 타입 정보와 매칭시킨 요소들이 포인트 컷 대상
    - 메소드(helloMethod)의 객체 타입과 포인트컷 대상의 타입이 부모 타입으로 일치하는 경우
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 13")
    void typeMatchSuperMatch() {

        pointcut.setExpression("execution(* hello.aop.order.member.MemberService.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    14. execution pointcut 사용하기
    - 타입 정보로 매칭하는 case ( intetnalMethod 랑 )
    - pointcut 내에 존재하는 타입 정보와 매칭시킨 요소들이 포인트 컷 대상
    - 메소드(helloMethod)의 객체 타입과 포인트컷 대상의 타입이 부모 타입으로 일치하지만, 부모 타입에 정의된 메서드가 아닌 경우
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 13")
    void typeMatchInternal() throws NoSuchMethodException {

        pointcut.setExpression("execution(* hello.aop.order.member.MemberService.*(..))");

        Method intetnalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        Assertions.assertThat(pointcut.matches(intetnalMethod, MemberServiceImpl.class)).isFalse();
    }

    /*
    15. execution pointcut 사용하기
    - 타입 정보로 매칭하는 case ( intetnalMethod 랑 )
    - pointcut 내에 존재하는 타입 정보와 매칭시킨 요소들이 포인트 컷 대상
    - 메소드(helloMethod)의 객체 타입과 포인트컷 대상의 타입이 일치하면서(부모타입 x), 해당 객체에 정의된 메서드인 경우
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 13")
    void typeMatchInternal2() throws NoSuchMethodException {

        pointcut.setExpression("execution(* hello.aop.order.member.MemberServiceImpl.*(..))");

        Method intetnalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        Assertions.assertThat(pointcut.matches(intetnalMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    16. execution pointcut 사용하기
    - 파라미터(메서드)로 매칭하는 case ( helloMethod 랑 )
    - pointcut 내에 명시한 파라미터 타입과 매칭되는 경우 고려
    - 메소드(helloMethod)의 파라미터 타입과 pointcut에 명시한 파라미터가 일치하는 경우 True
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 13")
    void argsMatch()  {

        pointcut.setExpression("execution(* *(String))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    /*
    17. execution pointcut 사용하기
    - 파라미터(메서드)로 매칭하는 case ( helloMethod 랑 )
    - pointcut 내에 명시한 파라미터 타입과 매칭되지 않는 경우 고려
    - 메소드(helloMethod)의 파라미터 타입과 pointcut에 명시한 파라미터가 일치하지 않는 경우 False
    - 단, ()의 경우 파라미터가 없는 경우
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 13")
    void argsMatchNoArgs()  {

        pointcut.setExpression("execution(* *())");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }


    /*
    18. execution pointcut 사용하기
    - 파라미터(메서드)로 매칭하는 case ( helloMethod 랑 )
    - pointcut 내에 명시한 파라미터 타입이 파라미터의 모든 타입을 고려하는 경우
    - 메소드(helloMethod)의 파라미터 타입과 pointcut에 명시한 파라미터가 일치하는 경우 True
    - 단 정확히 하나의 파라미터를 허용하면서 모든 타입에 대해 매칭 가능한 경우
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 13")
    void argsMatchStar()  {

        pointcut.setExpression("execution(* *(*))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    19. execution pointcut 사용하기
    - 파라미터(메서드)로 매칭하는 case ( helloMethod 랑 )
    - pointcut 내에 명시한 파라미터 타입이 파라미터의 모든 타입을 고려하는 경우
    - 메소드(helloMethod)의 파라미터 타입과 pointcut에 명시한 파라미터가 일치하는 경우 True
    - 모든 파라미터 갯수와 모든 타입의 파라미터를 허용하면서 매칭 가능한 경우
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 13")
    void argsMatchAll()  {

        pointcut.setExpression("execution(* *(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*
    20. execution pointcut 사용하기
    - 파라미터(메서드)로 매칭하는 case ( helloMethod 랑 )
    - pointcut 내에 명시한 파라미터 타입이 파라미터의 모든 타입을 고려하는 경우
    - 메소드(helloMethod)의 파라미터 타입과 pointcut에 명시한 파라미터가 일치하는 경우 True
    - 모든 파라미터 갯수와 String 타입의 파라미터를 허용하면서 매칭 가능한 경우
    */
    @Test
    @DisplayName("execution pointcut으로 매칭하기 13")
    void argsMatchComplex()  {

        pointcut.setExpression("execution(* *(String, ..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}

