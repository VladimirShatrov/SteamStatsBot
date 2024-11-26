package vladimir.shatrov.steam.stats.telegram.bot.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@AllArgsConstructor
public class SteamService {

    @Value("${api.key}")
    private static String API_KEY;
    private static final String BASE_URL = "http://api.steampowered.com/";

    private final RestTemplate restTemplate;

    public String getPlayerSummary(String steamId) {
        System.out.println(API_KEY);
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
