package muic.backend.project.p1;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FormatTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Logger LOGGER = LoggerFactory.getLogger(FormatTest.class);

    @Test
    public void testTextFormat(){

        Pair<List<String>, List<String>> testCases = generateTestCases();
        List<String> formats = testCases.getKey();
        List<String> expectedResults = testCases.getValue();

        for(int i=0; i<formats.size(); i++){
            try {
                ResultActions result = this.mockMvc
                        .perform(get("/wc?target=" + "https://www.google.com")
                                .accept(formats.get(i)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(formats.get(i) + ";charset=ISO-8859-1"))
                        .andExpect(content().string(expectedResults.get(i)));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public Pair<List<String>, List<String>> generateTestCases(){
        List<String> formats = new ArrayList<>();
        List<String> expectedResults = new ArrayList<>();

        formats.add(MediaType.TEXT_HTML_VALUE);
        formats.add(MediaType.TEXT_PLAIN_VALUE);
        formats.add(MediaType.APPLICATION_JSON_VALUE);

        String plainResult = readTextFile("src/main/resources/test/format/plain.txt");
        String htmlResult = readTextFile("src/main/resources/test/format/html.html");
        String jsonResult = readTextFile("src/main/resources/test/format/json.json");

        expectedResults.add(htmlResult);
        expectedResults.add(plainResult);
        expectedResults.add(jsonResult);

        return new ImmutablePair<>(formats, expectedResults);
    }

    public String readTextFile(String fileName){
        String line = "";
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append('\n');
            }
            sb.deleteCharAt(sb.length()-1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
