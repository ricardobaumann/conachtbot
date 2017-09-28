package conachtbot;


import java.util.Optional;

public interface Reply {
    Optional<String> replyTo(String answer);

    String description();

    default int order() {
        return 0;
    }
}
