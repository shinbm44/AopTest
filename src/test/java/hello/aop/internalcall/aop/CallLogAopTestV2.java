package hello.aop.internalcall.aop;

import hello.aop.internalcall.CallServiceV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAop.class)
@SpringBootTest
class CallLogAopTestV2 {

    @Autowired
    CallServiceV1 callService;

    @Test
    @DisplayName("프록시를 통해 내부 메서드를 호출하는 경우")
    void externalCall() {
        callService.externalCall();
    }

    @Test
    @DisplayName("프록시가 내부의 메서드 한개만을 호출하는 경우")
    void internalCall() {
        callService.internalCall();
    }

}