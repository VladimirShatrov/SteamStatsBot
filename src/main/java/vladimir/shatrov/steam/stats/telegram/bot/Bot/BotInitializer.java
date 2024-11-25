package vladimir.shatrov.steam.stats.telegram.bot.Bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitializer {

    public BotInitializer(SteamBot steamBot) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(steamBot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}