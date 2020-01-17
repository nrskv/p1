package muic.backend.project.p1.service;

import muic.backend.project.p1.model.WordStats;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class AnalyzerService {

    @Autowired
    private WordStatsService wordStatsService;

    @Autowired
    private CacheManager cacheManager;

    private static final String CACHENAME = "wordStatsCache";

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public WordStats analyze(String target, Boolean force) throws IOException{
        if(isCached(target)){
            if(force){
                evictCache(target);
            } else if (!isRecentlyAnalyzed(target) && isModified(target)) {
                evictCache(target);
            }
        }
        return wordStatsService.getStats(target);
    }

    private boolean isCached(String target){
        Cache cache = cacheManager.getCache(CACHENAME);
        if(cache != null) return cache.get(target) != null;
        return false;
    }

    private void evictCache(String target){
        LOGGER.info("Evict cache for " + target);
        cacheManager.getCache(CACHENAME).evict(target);
    }

    private boolean isRecentlyAnalyzed(String target){
        Cache cache = cacheManager.getCache(CACHENAME);
        WordStats ws = cache.get(target, WordStats.class);
        Instant lastFetch = ws.getLastFetch();
        long elapsedTime = Duration.between(lastFetch, Instant.now()).toMillis();
        return elapsedTime > 60000;
    }

    private boolean isModified(String target) throws IOException{
        String oldETag = cacheManager.getCache(CACHENAME).get(target, WordStats.class).geteTag();
        try {
            URL url = new URL(target);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            String newETag = connection.getHeaderField("ETag");

            return oldETag == null || !oldETag.equals(newETag);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}


