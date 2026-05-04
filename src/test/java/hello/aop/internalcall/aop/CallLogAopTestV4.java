package hello.aop.internalcall.aop;

import hello.aop.internalcall.CallServiceV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAop.class)
@SpringBootTest
class CallLogAopTestV4 {

    @Autowired
    CallServiceV3 callService;

    @Test
    @DisplayName("프록시를 통해 내부 메서드를 호출하는 경우[구조적 변환을 통해 해결]")
    void externalCall() {
        callService.externalCall();
    }

}