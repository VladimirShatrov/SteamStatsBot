package vladimir.shatrov.steam.stats.telegram.bot.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;
import java.util.Date;

@JsonSerialize
public record PlayerSummary(
        String steamid,
        String personaname,
        String profileurl,
        String avatar,
        String avatarmedium,
        String avatarfull,
        PersonaState personastate,
        int communityvisibilitystate,
        boolean profilestate,
        Instant lastlogoff,
        boolean commentpermission
) {
}
