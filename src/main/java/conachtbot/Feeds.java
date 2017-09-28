package conachtbot;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Feeds implements Reply {

    private static final Set<String> FEEDS_URL = Sets.newHashSet(
            "https://welt.de/feeds/section/wirtschaft.rss",
            "https://welt.de/feeds/topnews.rss",
            "https://welt.de/feeds/latest.rss"
    );


    @Override
    public Optional<String> replyTo(final String answer) {
        if (answer.toLowerCase().contains("feeds")) {
            return Optional.of("Add one of these to your feed consumer client: \n" + FEEDS_URL.stream().collect(Collectors.joining("\n")));
        }
        return Optional.empty();
    }

    @Override
    public String description() {
        return "Specially for the 90's kids, we have feeds. If you like it type: I like feeds";
    }

    @Override
    public int order() {
        return 10;
    }
}
