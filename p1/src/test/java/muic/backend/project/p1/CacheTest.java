package muic.backend.project.p1;

import muic.backend.project.p1.model.WordStats;
import muic.backend.project.p1.service.AnalyzerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CacheTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(CacheTest.class);

    @Autowired
    private AnalyzerService analyzerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testForce(){
        try {
            WordStats request1 = analyzerService.analyze("http://muic.io", false);
            WordStats request2 = analyzerService.analyze("http://muic.io", true);
            WordStats request3 = analyzerService.analyze("http://muic.io", false);

            Assert.assertNotEquals(request1, request2);
            Assert.assertEquals(request2, request3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testModified(){
        try {
            LOGGER.info("This test takes at least 1.5 minutes");
            //Etag not changed
            WordStats request1 = analyzerService.analyze("https://www.thepolyglotdeveloper.com/css/custom.min.css", false);
            wait(30000);
            WordStats request2 = analyzerService.analyze("https://www.thepolyglotdeveloper.com/css/custom.min.css", false);

            Assert.assertEquals(request1, request2);

            //Etag changed
            WordStats request3 = analyzerService.analyze("http://example.com", false);
            request3.seteTag("randomEtag");
            wait(30000);
            WordStats request4 = analyzerService.analyze("http://example.com", false);

            Assert.assertNotEquals(request3, request4);

            //Etag is null
            WordStats request5 = analyzerService.analyze("https://www.google.com", false);
            wait(30000);
            WordStats request6 = analyzerService.analyze("https://www.google.com", false);

            Assert.assertNotEquals(request5, request6);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testScheduledCacheClear(){
        try {
            LOGGER.info("This test takes at least 3 minute");
            WordStats request1 = analyzerService.analyze("https://en.wikipedia.org/wiki/Example.com", false);
            WordStats request2 = analyzerService.analyze("https://en.wikipedia.org/wiki/Example.com", false);
            wait(60000);
            WordStats request4 = analyzerService.analyze("https://en.wikipedia.org/wiki/Example.com", false);

            Assert.assertEquals(request1, request2);
            Assert.assertNotEquals(request1, request4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wait(int ms){
        LOGGER.info("Wait " + ms + " ms");
        for(int i = 0; i < ms; i+=10000){
            try {
                int waitTime = ms-i;
                LOGGER.info(waitTime + " ms left");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("0 ms left");
    }
}


