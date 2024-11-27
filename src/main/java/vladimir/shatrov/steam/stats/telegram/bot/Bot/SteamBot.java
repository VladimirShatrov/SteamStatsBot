package vladimir.shatrov.steam.stats.telegram.bot.Bot;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import vladimir.shatrov.steam.stats.telegram.bot.dto.FriendListResponse;
import vladimir.shatrov.steam.stats.telegram.bot.dto.PlayerSummary;
import vladimir.shatrov.steam.stats.telegram.bot.service.SteamService;

import java.util.Date;
import java.util.List;

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
                        Date date = new Date((long)data.lastlogoff() * 1000);
                        String text = "Имя пользователя: " + data.personaname() + "\n" +
                                "Аватарка пользователя: " + data.avatar() + "\n" +
                                "Профиль пользователя: " + data.profileurl() + "\n" +
                                "Онлайн: " + date.toString() + "\n" +
                                "Статус: " + data.personastate();
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
                        List<FriendListResponse.Friend> friends = steamService.getFriendList(steamId).getFriendslist().getFriends();
                        if (friends.isEmpty()) {
                            message.setText("У пользователя нет друзей.");
                        }
                        else {
                            StringBuilder text = new StringBuilder();
                            for (FriendListResponse.Friend friend: friends
                                 ) {
                                text.append(steamService.getPlayerSummary(friend.getSteamid()).personaname()).append(" ");
                                Date date = new Date((long)friend.getFriendSince() * 1000);
                                text.append(date.toString()).append("\n");
                            }
                            message.setText(text.toString());
                        }
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
