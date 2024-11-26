package vladimir.shatrov.steam.stats.telegram.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlayerSummaryResponse {

    @JsonProperty("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public static class Response {
        @JsonProperty("players")
        private List<PlayerSummary> players;

        public List<PlayerSummary> getPlayers() {
            return players;
        }
    }
}
