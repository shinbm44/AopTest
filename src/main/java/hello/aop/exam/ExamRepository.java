package hello.aop.exam;

import hello.aop.exam.annotation.Retry;
import hello.aop.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository  {


    /**
     *
     * 5번 시도에 1번 실패하는 요청
     *
     */
    private static int seq =0;

    @Trace
    @Retry(value = 4)
    public String save(String itemId) {
        seq++;

        if(seq % 5 == 0 ) {
            throw new IllegalStateException("예외발생");
        }

        return "ok";
    }
}
