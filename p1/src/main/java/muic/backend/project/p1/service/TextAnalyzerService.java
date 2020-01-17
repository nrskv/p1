package muic.backend.project.p1.service;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import muic.backend.project.p1.model.WordStats;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TextAnalyzerService {

    public Pair<Integer, List<Map.Entry<String, Integer>>> analyzeText(String text){

        // A word is a consecutive sequence of letters [a-zA-Z] that is not longer than 20 letters
        // split when a non-letter character is found (ascii for A-Z and a-z is 65-90 and 97-122)
        // immediately skip a sequence that's longer than 20 letters to avoid a GB long sequence
        /*
        for character in text:
            if (character is not a letter):
                start next sequence
            if (sequence.length >= 20):
                stop then search for the next delimiter
            else:
                sequence += character
         */

        int totalWordCount = 0;
        Map<String, Integer> individualWordCount = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        boolean ignoreCurrentChar = false;

        for (int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            if (!isAlphabet(c)) {
                ignoreCurrentChar = false;
                String word = sb.toString().toLowerCase();
                if(!word.isEmpty()){
                    totalWordCount++;
                    addToIndividualWordCount(word, individualWordCount);
                }
                sb = new StringBuilder();
            } else if (i == text.length()-1 && !ignoreCurrentChar){
                sb.append(c);
                String word = sb.toString().toLowerCase();
                totalWordCount++;
                addToIndividualWordCount(word, individualWordCount);
            } else if (sb.length() == 19) {
                ignoreCurrentChar = true;
                sb = new StringBuilder();
            } else if (!ignoreCurrentChar) {
                sb.append(c);
            }
        }

        List<Map.Entry<String, Integer>> topWords = getTopTenWords(individualWordCount);

        return new ImmutablePair<>(totalWordCount, topWords);
    }

    private boolean isAlphabet(char c){
        int ascii = (int) c;
        return ((ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122));
    }

    private void addToIndividualWordCount(String word, Map<String, Integer> individualWordCount){
        int n = 0;
        if(individualWordCount.containsKey(word)){
            n = individualWordCount.get(word);
        }
        individualWordCount.put(word, ++n);
    }

    private List<Map.Entry<String, Integer>> getTopTenWords(Map<String, Integer> individualWordCount){

        // sort in descending order and get the first 10
        return individualWordCount
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
