package empapp;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

@Controller
public class NumberController {

    private TaskExecutor taskExecutor;

    public NumberController(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @GetMapping("/api/numbers")
    public SseEmitter numbers() {
        var sseEmitter = new SseEmitter();
        taskExecutor.execute(() -> sendNumbers(sseEmitter));
        return sseEmitter;
    }

    private void sendNumbers(SseEmitter sseEmitter) {
        for (int i = 0; i < 10; i++) {
            System.out.println("SSE: " + i);
            sendAndSleep(sseEmitter, Integer.toString(i));
        }
        sseEmitter.complete();
    }

    public void sendAndSleep(SseEmitter sseEmitter, String s) {
        try {
            SseEmitter.SseEventBuilder builder = SseEmitter.event()
                    .comment("A number - comment")
                    .id(UUID.randomUUID().toString())
                    .reconnectTime(10_000)
                    .name("number")
                    // JSON marshal
                    .data(s);

            sseEmitter.send(builder);
            Thread.sleep(3000);
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Can not send");
        }
    }
}
