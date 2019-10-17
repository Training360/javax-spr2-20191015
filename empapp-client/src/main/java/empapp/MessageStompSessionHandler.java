package empapp;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;

public class MessageStompSessionHandler implements StompSessionHandler {

    public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
        stompSession.subscribe("/topic/employees", this);
        stompSession.send("/app/messages",
                new MessageCommand("Hello world from client"));
    }

    public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
        throw new IllegalStateException("Exception by websocket communication", throwable);
    }

    public void handleTransportError(StompSession stompSession, Throwable throwable) {
        throw new IllegalStateException("Exception by websocket transport", throwable);
    }

    public Type getPayloadType(StompHeaders stompHeaders) {
        return Message.class;
    }

    public void handleFrame(StompHeaders stompHeaders, Object o) {
        String content = ((Message) o).getContent();
        System.out.println(content);
    }
}
