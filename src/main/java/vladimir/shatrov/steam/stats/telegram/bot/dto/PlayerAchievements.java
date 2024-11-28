package vladimir.shatrov.steam.stats.telegram.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class PlayerAchievements {

    @JsonProperty("playerstats")
    private PlayerStats playerStats;

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    public static class PlayerStats {
        @JsonProperty("steamID")
        private String steamId;

        @JsonProperty("gameName")
        private String gameName;

        @JsonProperty("achievements")
        private List<Achievement> achievements;

        public List<Achievement> getAchievements () {
            return achievements;
        }

        public String getSteamId () {
            return steamId;
        }

        public String getGameName () {
            return gameName;
        }

        @Getter
        public static class Achievement {

            @JsonProperty("apiname")
            private String apiname;

            @JsonProperty("achieved")
            private boolean achieved;

            @JsonProperty("unlocktime")
            private Instant unlocktime;

            @JsonProperty("name")
            private String name;

            @JsonProperty("description")
            private String description;
        }
    }
}
