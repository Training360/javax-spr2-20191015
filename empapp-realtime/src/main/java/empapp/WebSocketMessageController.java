package empapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketMessageController {

    private SimpMessagingTemplate template;

    public WebSocketMessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    //@SendTo("/topic/employees")
    //@SubscribeMapping("/topic/employees")
    @EventListener
    public void employeeHasCreated(EmployeeHasCreatedEvent event) {
        Message message = new Message("Employee has created: " + event.getName());
        template.convertAndSend("/topic/employees", message);
    }

    @SendTo("/topic/employees")
    //@SubscribeMapping("/topic/employees") // csak a subscription (feliratkozo) uzenetekre iratkozik fel
    @MessageMapping("/messages")
    public Message sendMessage(MessageCommand command) {
        return new Message("Reply: " + command.getContent());
    }

}
