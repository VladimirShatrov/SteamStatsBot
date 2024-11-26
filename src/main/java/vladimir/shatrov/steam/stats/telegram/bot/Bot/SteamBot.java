package vladimir.shatrov.steam.stats.telegram.bot.Bot;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import vladimir.shatrov.steam.stats.telegram.bot.dto.PlayerSummary;
import vladimir.shatrov.steam.stats.telegram.bot.service.SteamService;

@Component
public class SteamBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    private SteamService steamService;

    public SteamBot(SteamService steamService) {
        this.steamService = steamService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String userInput = update.getMessage().getText();

            SendMessage message = new SendMessage();
            message.setChatId(chatId);

            if (userInput.startsWith("/getdata")) {
                String input = userInput.replace("/getdata", "").trim();

                if (!input.isEmpty()) {
                    String steamId = input;

                    if (!input.matches("\\d+")) {
                        steamId = steamService.resolveVanityURL(input);
                    }

                    if (steamId != null) {
                        PlayerSummary data = steamService.getPlayerSummary(steamId);
                        String text = data.steamid() + "\n" + data.personaname() + "\n" +
                                data.avatar() + "\n" + data.profileurl() + "\n" + data.lastlogoff() + "\n" +
                                data.profilestate() + "\n" + data.personastate();
                        message.setText(text);
                    }
                    else {
                        message.setText("Пользователь не найден.");
                    }
                }
            }
            if (userInput.startsWith("/getFriendList")) {
                String input = userInput.replace("/getFriendList", "").trim();
                if (!input.isEmpty()) {
                    String steamId = input;
                    if (!input.matches("\\d+")) {
                        steamId = steamService.resolveVanityURL(input);
                    }

                    if (steamId != null) {
                        message.setText(steamService.getFriendList(steamId));
                    }
                    else {
                        message.setText("Пользователь не найден.");
                    }
                }
            }
            try {
                execute(message);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
