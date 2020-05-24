package module.marketapi;

import module.marketapi.model.MarketResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Cache introduced to reduce loading times by attempting to not wait for Market API as often.
 * TODO: Run purge in background on a timer.
 */
public class MarketCache {

    private static MarketCache SINGLETON = new MarketCache();

    public static MarketCache getInstance() {
        return SINGLETON;
    }

    private CopyOnWriteArrayList<CachedItem> cache = new CopyOnWriteArrayList<>();

    private MarketCache() {

    }

    /**
     * Clears the cache.
     */
    public void clearCache() {
        cache.clear();
    }

    /**
     * Purges all expired items from the cache.
     */
    public void purgeExpiredItems() {
        cache.stream().filter(CachedItem::isExpired).forEach(cache::remove);
    }

    /**
     * Gets a MarketResponse of a cached item if the item is located in the cache and unexpired.
     * @param id
     * @return
     */
    public Optional<MarketResponse> get(long id) {
        return cache.stream().filter(cachedItem -> cachedItem.getMarketResponse().getMainKey() == id)
                .filter(cachedItem -> !cachedItem.isExpired())
                .map(cachedItem -> cachedItem.getMarketResponse()).findAny();
    }

    public Optional<MarketResponse> get(String name) {
        return cache.stream().filter(cachedItem -> name.equalsIgnoreCase(cachedItem.getMarketResponse().getName()))
                .filter(cachedItem -> !cachedItem.isExpired())
                .map(cachedItem -> cachedItem.getMarketResponse()).findAny();
    }

    /**
     * Add data to the cache.
     * @param response
     */
    public void add(MarketResponse response) {
        // if already in cache, remove.
        cache.stream().filter(cachedItem -> cachedItem.getMarketResponse().getMainKey() == response.getMainKey()).forEach(cache::remove);
        // add new item to the cache.
        cache.add(new CachedItem(response));
    }

    class CachedItem {

        private static final long EXPIRE_TIME = 15;
        private MarketResponse marketResponse;
        private Instant time;

        public CachedItem(MarketResponse marketResponse) {
            this.marketResponse = marketResponse;
            time = Instant.now();
        }

        public MarketResponse getMarketResponse() {
            return marketResponse;
        }

        /**
         * Returns true if this cached item is expired.
         *
         * @return
         */
        public boolean isExpired() {
            return Duration.between(time, Instant.now()).toMinutes() >= EXPIRE_TIME;
        }
    }

}
