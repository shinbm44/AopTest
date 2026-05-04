package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2 {

    private final ApplicationContext applicationContext;
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    /**
     * @내용 지연로딩을 통한 객체 주입으로 프록시 객체 내부 메서드 호출에 대한 미적용 해결 방안의 코드
     * @방법1 applicationContext ( = 스프링 컨테이너 ) 객체를 활용한 bean 조회 및 활용
     * @방법2 ObjectProvider 객체를 통한 bean 조회 및 활용
     */


    public void externalCall() {
        log.info("call external");

        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        callServiceV2.internalCall();

        CallServiceV2 callServiceV2_2 = callServiceProvider.getObject();
        callServiceV2_2.internalCall();

    }

    public void internalCall() {
        log.info("call internal");
    }


}
