package conachtbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * This example bot is an echo bot that just repeats the messages sent to him
 */
@Component
public class ExampleBot extends TelegramLongPollingBot {


    private final Replyer replyer;

    ExampleBot(final Replyer replyer) {
        this.replyer = replyer;
    }

    private static final Logger logger = LoggerFactory.getLogger(ExampleBot.class);

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
            final String text = message.getText();
            response.setText(replyer.reply(text));
            try {
                sendMessage(response);
                logger.info("Sent message \"{}\" to {}", text, chatId);
            } catch (final TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
    }

}
