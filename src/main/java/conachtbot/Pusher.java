package conachtbot;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Pusher {

    private final ExampleBot exampleBot;

    Pusher(final ExampleBot exampleBot) {
        this.exampleBot = exampleBot;
    }

    @PostMapping(path = "/message", consumes = MediaType.TEXT_PLAIN_VALUE)
    void post(@RequestBody final String body) {
        exampleBot.broadcast(body);
    }

}
