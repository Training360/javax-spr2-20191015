package empapp.sse;

import empapp.Message;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SseClientMain {

    public static void main(String[] args) throws InterruptedException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/api/numbers");
        try (SseEventSource source = SseEventSource.target(target)
                .reconnectingEvery(30, SECONDS)

                .build()) {
            source.register(
                    inboundSseEvent -> System.out.println(inboundSseEvent.readData(String.class)),
                    t -> t.printStackTrace(),
                    () -> System.out.println("VEGE"));

            source.open();
            new Scanner(System.in).nextLine();
        }
    }
}
