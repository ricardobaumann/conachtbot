package conachtbot;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Replyer {

    private final List<Reply> replies;

    Replyer(final List<Reply> replies) {
        this.replies = replies;
    }

    public String reply(final String answer) {

        final String replyMessage = replies.stream().map(reply -> reply.replyTo(answer))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining("\n"));

        if (StringUtils.isEmpty(replyMessage)) {
            return "dont get you";
        }

        return replyMessage;
    }

}
