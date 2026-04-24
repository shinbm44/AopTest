package hello.aop.exam;

import hello.aop.exam.aop.TraceAspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TraceAspect.class)
@SpringBootTest
class ExamServiceTest {

    @Autowired
    private ExamService examService;

    @Test
    @DisplayName("5번 호출하기 -> 의도적인 예외 발생")
    void Test() {
        for (int i =0; i< 5; i++) {
            examService.request("data" + i);
        }
    }

}