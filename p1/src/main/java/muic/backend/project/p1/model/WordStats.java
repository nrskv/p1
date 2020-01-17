package muic.backend.project.p1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class WordStats {

    @JsonIgnore
    private String target;

    @JsonProperty(value = "total_words")
    private Integer totalWordCount;

    @JsonProperty(value = "top10")
    private List<Map.Entry<String, Integer>> topWords;

    @JsonIgnore
    private Instant lastFetch;

    @JsonIgnore
    private String eTag;

    public WordStats(String target, Instant lastFetch, String eTag, int totalWordCount, List<Map.Entry<String, Integer>> topWords){
        update(target, lastFetch, eTag,totalWordCount, topWords);
    }

    public void update(String url, Instant lastFetch, String eTag, int wordCount, List<Map.Entry<String, Integer>> topTen){
        setTarget(url);
        setLastFetch(lastFetch);
        seteTag(eTag);
        setTotalWordCount(wordCount);
        setTopWords(topTen);
    }

    public Integer getTotalWordCount() {
        return totalWordCount;
    }

    private void setTotalWordCount(Integer totalWordCount) {
        this.totalWordCount = totalWordCount;
    }

    public List<Map.Entry<String, Integer>> getTopWords() {
        return topWords;
    }

    private void setTopWords(List<Map.Entry<String, Integer>> topWords) {
        this.topWords = topWords;
    }

    public String getTarget() {
        return target;
    }

    private void setTarget(String target) {
        this.target = target;
    }

    public Instant getLastFetch() {
        return lastFetch;
    }

    private void setLastFetch(Instant lastFetch) {
        this.lastFetch = lastFetch;
    }

    public String geteTag() {
        return eTag;
    }

    private void seteTag(String eTag) {
        this.eTag = eTag;
    }
}
