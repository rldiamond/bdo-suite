package common.settings;

public class UserSettings {

    public static final UserSettings SINGLETON = new UserSettings();

    public static UserSettings getInstance() {
        return SINGLETON;
    }

    private int marketCachePurgeRepeatTimeMinutes = 5;
    private int marketCacheKeepTimeMinutes = 15;

    public int getMarketCachePurgeRepeatTimeMinutes() {
        return marketCachePurgeRepeatTimeMinutes;
    }

    public int getMarketCacheKeepTimeMinutes() {
        return marketCacheKeepTimeMinutes;
    }
}
