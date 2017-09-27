package conachtbot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FrankReplyer implements Reply, Loggable {

    private static final Pattern PATTERN = Pattern.compile("about '(.*?)'");

    private final RestTemplate restTemplate;

    FrankReplyer(@Qualifier("frankRestTemplate") final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<String> replyTo(final String answer) {

        final Matcher matcher = PATTERN.matcher(answer);
        if (matcher.find()) {
            final String tag = matcher.group(1);
            return Optional.of("I may know something about " + tag);
        }

        return Optional.empty();
    }
}
