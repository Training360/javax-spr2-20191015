package empapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

    public void sendMail(String to) {
//        executor.execute(() ->
//        log.info("Send mail: " + to));
        log.info("Send mail: " + to);
        var m = new CacheManager();
        m.getCache("wetwe").evict();
    }
}
