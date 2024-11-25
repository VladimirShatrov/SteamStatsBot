package vladimir.shatrov.steam.stats.telegram.bot.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@AllArgsConstructor
public class SteamService {
    private static final String API_KEY = "C99EF26887D46F3A50456EB471710E95";
    private static final String BASE_URL = "http://api.steampowered.com/";

    private final RestTemplate restTemplate;

    public String getPlayerSummary(String steamId) {
        String url = UriComponentsBuilder.fromUriString(BASE_URL + "ISteamUser/GetPlayerSummaries/v0002/")
                .queryParam("key", API_KEY)
                .queryParam("steamids", steamId)
                .toUriString();
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error to get player summary.";
        }
    }
}
