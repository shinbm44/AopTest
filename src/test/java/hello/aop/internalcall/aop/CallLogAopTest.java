package hello.aop.internalcall.aop;

import hello.aop.internalcall.CallServiceV0;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAop.class)
@SpringBootTest
class CallLogAopTest {

    @Autowired
    CallServiceV0 callService;

    @Test
    @DisplayName("")
    void externalCall() {
        callService.externalCall();
    }

    @Test
    @DisplayName("")
    void internalCall() {
        callService.internalCall();
    }

}