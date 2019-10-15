package empapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

    private TaskExecutor executor;

    public MailService(TaskExecutor executor) {
        this.executor = executor;
    }

    public void sendMail(String to) {
//        executor.execute(() ->
//        log.info("Send mail: " + to));
        log.info("Send mail: " + to);
    }
}
