package conachtbot;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Replyer {

    private final List<Reply> replies;

    Replyer(final List<Reply> replies) {
        this.replies = replies;
    }

    String reply(final String question) {

        if (question.toLowerCase().contains("what do you know")) {
            return replies.stream().sorted(Comparator.comparingInt(Reply::order)).map(Reply::description)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining("\n\n and \n\n"));
        }

        final String replyMessage = replies.stream().map(reply -> reply.replyTo(question))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining("\n"));

        if (StringUtils.isEmpty(replyMessage)) {
            return "dont get you";
        }

        return replyMessage;
    }

}
