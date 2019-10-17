package empapp;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class EmployeeHasCreatedEvent extends ApplicationEvent {

    private String name;

    public EmployeeHasCreatedEvent(Object source, String name) {
        super(source);
        this.name = name;
    }
}
