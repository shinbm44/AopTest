package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {
    /**
     * 프록시 사용시 문제점이 드러나는 코드
     * 프록시 내부에서 호출하는 메서드는 AOP 코드가 적용되지 않는다.
     * 프록시 내부로 옮겨간다음, 실제 객체(TARGET)의 코드가 내부 메서를 직접 호출하기 때문
     */
    public void externalCall() {
        log.info("externalCall");
        internalCall();
    }

    public void internalCall() {
        log.info("internalCall");
    }
}
