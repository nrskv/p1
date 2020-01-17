package muic.backend.project.p1.service;

import org.apache.commons.lang3.tuple.Pair;
import muic.backend.project.p1.model.WordStats;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordStatsService {

    @Autowired
    private TextAnalyzerService textAnalyzerService;

    @Cacheable(cacheNames = "wordStatsCache", key = "#target",sync = true)
    public WordStats getStats(String target) throws IOException{

        String text = extractText(target);

        Pair<Integer, List<Map.Entry<String, Integer>>> result = textAnalyzerService.analyzeText(text);

        return new WordStats(target, Instant.now(), getEtag(target), result.getKey(), result.getValue());
    }

    private String extractText(String target) throws IOException{
        Document doc = null;

        try {
            doc = Jsoup.connect(target).get();
        } catch (IOException e) {
            //notify all pending requests
            e.printStackTrace();
            throw e;
        }

        return doc.body().text();
    }

    public String getEtag(String target) throws IOException{
        String eTag = null;
        try{
            URL url = new URL(target);
            URLConnection connection = url.openConnection();
            connection.setReadTimeout(30000);
            eTag = connection.getHeaderField("ETag");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return eTag;
    }

}
