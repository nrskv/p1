package muic.backend.project.p1.service.formatter;

import muic.backend.project.p1.model.WordStats;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PlainTextFormatter extends Formatter{

    @Override
    public String format(WordStats wordStats) {
        StringBuilder sb = new StringBuilder();
        sb.append("Total number of words from the input URL is " + wordStats.getTotalWordCount() + ". ");
        sb.append("The most frequent words are ");
        List<Map.Entry<String, Integer>> topWords = wordStats.getTopWords();
        int n = 1;
        for (Map.Entry entry: topWords){
            if (n == topWords.size()) sb.append(" and ");
            sb.append(" " + entry.getKey() + " [" + entry.getValue() + "] ,");
            n++;
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(".");
        return sb.toString();
    }

    @Override
    public String toString() {
        return MediaType.TEXT_PLAIN_VALUE;
    }
}
