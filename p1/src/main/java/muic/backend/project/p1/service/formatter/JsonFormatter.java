package muic.backend.project.p1.service.formatter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import muic.backend.project.p1.model.WordStats;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class JsonFormatter extends Formatter{
    @Override
    public String format(WordStats wordStats) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
            json = objectMapper.writeValueAsString(wordStats);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public String toString() {
        return MediaType.APPLICATION_JSON_VALUE;
    }
}
