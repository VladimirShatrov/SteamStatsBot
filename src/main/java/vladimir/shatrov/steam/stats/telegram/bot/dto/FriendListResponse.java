package vladimir.shatrov.steam.stats.telegram.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FriendListResponse {
    @JsonProperty("friendslist")
    private FriendsList friendslist;

    public FriendsList getFriendslist() {
        return friendslist;
    }

    public static class FriendsList {
        @JsonProperty("friends")
        private List<Friend> friends;

        public List<Friend> getFriends() {
            return friends;
        }
    }

    public static class Friend {
        @JsonProperty("steamid")
        private String steamid;

        @JsonProperty("relationship")
        private String relationship;

        @JsonProperty("friend_since")
        private long friendSince;

        public String getSteamid() {
            return steamid;
        }

        public String getRelationship() {
            return relationship;
        }

        public long getFriendSince() {
            return friendSince;
        }
    }
}
