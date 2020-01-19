package muic.backend.project.p1;

import muic.backend.project.p1.model.WordStats;
import muic.backend.project.p1.service.AnalyzerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(CacheTest.class);

    @Autowired
    private AnalyzerService analyzerService;

    @Test
    public void testForce(){
        try {
            WordStats request1 = analyzerService.analyze("http://example.com", false);
            WordStats request2 = analyzerService.analyze("http://example.com", true);
            WordStats request3 = analyzerService.analyze("http://example.com", false);

            Assert.assertNotEquals(request1, request2);
            Assert.assertEquals(request2, request3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testModified(){
        try {
            LOGGER.info("This test takes at least 3 minutes");
            //Etag not changed
            WordStats request1 = analyzerService.analyze("http://example.com", false);
            waitOneMinute();
            WordStats request2 = analyzerService.analyze("http://example.com", false);

            Assert.assertEquals(request1, request2);

            //Etag changed
            WordStats request3 = analyzerService.analyze("http://example.com", false);
            request3.seteTag("randomEtag");
            waitOneMinute();
            WordStats request4 = analyzerService.analyze("http://example.com", false);

            Assert.assertNotEquals(request3, request4);

            //Etag is null
            WordStats request5 = analyzerService.analyze("https://www.google.com", false);
            waitOneMinute();
            WordStats request6 = analyzerService.analyze("https://www.google.com", false);

            Assert.assertNotEquals(request5, request6);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testScheduledCacheClear(){
        try {
            LOGGER.info("This test takes at least 2 minutes");
            WordStats request1 = analyzerService.analyze("http://example.com", false);
            WordStats request2 = analyzerService.analyze("http://example.com", false);
            waitOneMinute();
            WordStats request3 = analyzerService.analyze("http://example.com", false);
            waitOneMinute();
            WordStats request4 = analyzerService.analyze("http://example.com", false);

            Assert.assertEquals(request1, request2);
            Assert.assertEquals(request1, request3);
            Assert.assertNotEquals(request1, request4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCacheConcurrency(){

    }

    public void waitOneMinute(){
        LOGGER.info("Wait one minute");
        for(int i = 1; i < 7; i++){
            try {
                Thread.sleep(10000);
                int waitTime = 60-i*10;
                LOGGER.info(waitTime + "s left");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


