package vladimir.shatrov.steam.stats.telegram.bot.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vladimir.shatrov.steam.stats.telegram.bot.dto.FriendListResponse;
import vladimir.shatrov.steam.stats.telegram.bot.dto.PlayerSummary;
import vladimir.shatrov.steam.stats.telegram.bot.dto.PlayerSummaryResponse;

import java.util.List;
import java.util.Map;

@Service
public class SteamService {

    @Value("${api.key}")
    private String API_KEY;
    private static final String BASE_URL = "http://api.steampowered.com/";

    private final RestTemplate restTemplate;

    public SteamService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PlayerSummary getPlayerSummary(String steamId) {
        String url = UriComponentsBuilder.fromUriString(BASE_URL + "ISteamUser/GetPlayerSummaries/v0002/")
                .queryParam("key", API_KEY)
                .queryParam("steamids", steamId)
                .toUriString();
        try {
            PlayerSummaryResponse response = restTemplate.getForObject(url, PlayerSummaryResponse.class);

            if (response != null && response.getResponse() != null && !response.getResponse().getPlayers().isEmpty()) {
                return response.getResponse().getPlayers().get(0);
            }
            else {
                throw new RuntimeException("Аккаунт не найден.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String resolveVanityURL(String username) {
        String url = UriComponentsBuilder.fromUriString(BASE_URL + "ISteamUser/ResolveVanityUrl/v0001/")
                .queryParam("key", API_KEY)
                .queryParam("vanityurl", username)
                .toUriString();
        try {
            var response = restTemplate.getForObject(url, Map.class);
            Map responseMap = (Map) response.get("response");

            if (responseMap != null && "1".equals(responseMap.get("success").toString())) {
                return responseMap.get("steamid").toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FriendListResponse getFriendList (String steamId) {
        String url = UriComponentsBuilder.fromUriString(BASE_URL + "ISteamUser/GetFriendList/v0001/")
                .queryParam("key", API_KEY)
                .queryParam("steamid", steamId)
                .queryParam("relationship", "friend")
                .toUriString();
        try {
            return restTemplate.getForObject(url, FriendListResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
