package conachtbot;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FrankReplyer implements Reply, Loggable {

    private static final Pattern PATTERN = Pattern.compile("about '(.*?)'");

    private final RestTemplate restTemplate;

    FrankReplyer(@Qualifier("frankRestTemplate") final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<String> replyTo(final String answer) {

        try {
            final Matcher matcher = PATTERN.matcher(answer);
            if (matcher.find()) {
                final String tag = matcher.group(1);

                return Optional.of("I may know something about " + tag + ": \n" + extractUrlsFrom(tag).stream().collect(Collectors.joining("\n")));
            }
        } catch (final Exception e) {
            logger().error("Unable to fetch response", e);
        }

        return Optional.empty();
    }

    private Set<String> extractUrlsFrom(final String answer) {
        final String sectionPath = "/" + answer.replaceAll("\\s", "/") + "/";
        final ObjectNode response = restTemplate.getForObject("/_search?sectionPath={sectionPath}&type=article&sortBy=lastModifiedDate&pageSize=10", ObjectNode.class, sectionPath);
        final ArrayNode results = (ArrayNode) response.get("response").get("results");
        return StreamSupport.stream(results.spliterator(), false).map(jsonNode -> jsonNode.get("webUrl").asText()).map(s -> "https://welt.de/".concat(s)).collect(Collectors.toSet());
    }
}
