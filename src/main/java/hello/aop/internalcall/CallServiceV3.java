package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

    private final InternalService internalService;

    /**
     * @내용 구조적 변화를 통한 객체 주입으로 프록시 객체 내부 메서드 호출에 대한 미적용 해결 방안의 코드
     * @방법1 하나의 클래스 내부에서 사용하던 내부메서드를 별도의 클래스를 추출하여 호출하도록 변환
     */

    public void externalCall() {
        log.info("call external");
        internalService.internalCall();
    }

}
