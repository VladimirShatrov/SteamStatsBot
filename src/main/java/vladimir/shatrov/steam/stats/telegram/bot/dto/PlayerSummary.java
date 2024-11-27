package vladimir.shatrov.steam.stats.telegram.bot.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
        long lastlogoff,
        boolean commentpermission
) {
}
