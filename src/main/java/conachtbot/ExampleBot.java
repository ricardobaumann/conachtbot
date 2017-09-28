package conachtbot;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Set;

@Component
public class ExampleBot extends TelegramLongPollingBot implements Loggable {

    private final Set<Long> activeChatIds = Sets.newHashSet();

    private final Replyer replyer;

    ExampleBot(final Replyer replyer) {
        this.replyer = replyer;
    }

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(final Update update) {
        if (update.hasMessage()) {
            final Message message = update.getMessage();
            final SendMessage response = new SendMessage();
            final Long chatId = message.getChatId();
            response.setChatId(chatId);
            activeChatIds.add(chatId);
            final String text = message.getText();
            response.setText(replyer.reply(text));
            try {
                sendMessage(response);
            } catch (final TelegramApiException e) {
                logger().error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
    }


    void broadcast(final String message) {
        activeChatIds.forEach(chatId -> {
            try {
                sendMessage(new SendMessage(chatId, message));
            } catch (final TelegramApiException e) {
                logger().error("Unable to send message", e);
            }
        });
    }
}
