package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    /**
     * @param callServiceV1
     * @내용 수정자 주입을 통한 자기 자신 주입을 통해서 프록시 상태에서 내부 함수 호출 문제를 해결
     * @주의할점 생성자 호출 사에는 내부 순환 문제로 에러가 발생하여 수정자 주입으로 해결
     */

    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        this.callServiceV1 = callServiceV1;
    }


    public void externalCall() {
        log.info("call external");
        callServiceV1.internalCall();
    }

    public void internalCall() {
        log.info("call internal");
    }


}
