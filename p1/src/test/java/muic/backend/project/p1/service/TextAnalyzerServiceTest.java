package muic.backend.project.p1.service;

import javafx.util.Pair;
import muic.backend.project.p1.model.WordStats;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class TextAnalyzerServiceTest {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Test
    void analyzeText() {

        TextAnalyzerService textAnalyzerService = new TextAnalyzerService();

        String text1 = "";
        int text1WordCount = 0;
        Set<Map.Entry<String, Integer>> text1TopWords = new HashSet<>();

        String text2 = "The word pneumonoultramicroscopicsilicovolcanoconiosis contains 45 characters.";
        int text2WordCount = 4;
        Set<Map.Entry<String, Integer>> text2TopWords = new HashSet<>();
        text2TopWords.add(new AbstractMap.SimpleEntry<>("the", 1));
        text2TopWords.add(new AbstractMap.SimpleEntry<>("word", 1));
        text2TopWords.add(new AbstractMap.SimpleEntry<>("contains", 1));
        text2TopWords.add(new AbstractMap.SimpleEntry<>("characters", 1));

        String text3 = "A%#AS abc9lll^   ";
        int text3WordCount = 4;
        Set<Map.Entry<String, Integer>> text3TopWords = new HashSet<>();
        text3TopWords.add(new AbstractMap.SimpleEntry<>("a", 1));
        text3TopWords.add(new AbstractMap.SimpleEntry<>("as", 1));
        text3TopWords.add(new AbstractMap.SimpleEntry<>("abc", 1));
        text3TopWords.add(new AbstractMap.SimpleEntry<>("lll", 1));

        String text4 = "What lies behind us and what lies before us are tiny to what lies within us.";
        int text4WordCount = 16;
        Set<Map.Entry<String, Integer>> text4TopWords = new HashSet<>();
        text4TopWords.add(new AbstractMap.SimpleEntry<>("what", 3));
        text4TopWords.add(new AbstractMap.SimpleEntry<>("us", 3));
        text4TopWords.add(new AbstractMap.SimpleEntry<>("lies", 3));
        text4TopWords.add(new AbstractMap.SimpleEntry<>("within", 1));
        text4TopWords.add(new AbstractMap.SimpleEntry<>("to", 1));
        text4TopWords.add(new AbstractMap.SimpleEntry<>("tiny", 1));
        text4TopWords.add(new AbstractMap.SimpleEntry<>("and", 1));
        text4TopWords.add(new AbstractMap.SimpleEntry<>("behind", 1));
        text4TopWords.add(new AbstractMap.SimpleEntry<>("before", 1));
        text4TopWords.add(new AbstractMap.SimpleEntry<>("are", 1));

        String text5 = "Donaudampfschiffahrtsgesellschaftskapitan";
        int text5WordCount = 0;
        Set<Map.Entry<String, Integer>> text5TopWords = new HashSet<>();

        List<String> testTexts = new ArrayList<String>(){{
            add(text1);
            add(text2);
            add(text3);
            add(text4);
            add(text5);
        }};

        List<Integer> wordCounts = new ArrayList<Integer>(){{
            add(text1WordCount);
            add(text2WordCount);
            add(text3WordCount);
            add(text4WordCount);
            add(text5WordCount);
        }};

        List<Set<Map.Entry<String, Integer>>> topWords = new ArrayList<Set<Map.Entry<String, Integer>>>(){{
            add(text1TopWords);
            add(text2TopWords);
            add(text3TopWords);
            add(text4TopWords);
            add(text5TopWords);
        }};

        for (int i = 0; i < testTexts.size(); i++){

            LOGGER.log(Level.INFO, "test no." + (i+1) + " starts.");

            Pair<Integer, List<Map.Entry<String, Integer>>> result = textAnalyzerService.analyzeText(testTexts.get(i));

            Assert.assertEquals(wordCounts.get(i), result.getKey());
            Assert.assertEquals(topWords.get(i).size(), result.getValue().size());
            Assert.assertTrue(result.getValue().containsAll(topWords.get(i)));

            LOGGER.log(Level.INFO, "test no." + (i+1) + " passed.");

        }

    }

}