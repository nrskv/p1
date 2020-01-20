package muic.backend.project.p1;

import muic.backend.project.p1.model.WordStats;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class CacheConcurrencyTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private MockMvc mockMvc;

    private final static Logger LOGGER = LoggerFactory.getLogger(CacheTest.class);

    private String refHash = null;

    @Test(threadPoolSize = 4, invocationCount = 4,  timeOut = 30000)
    public void testCacheConcurrency() throws Exception {
        String workingTarget = "https://muic.mahidol.ac.th/eng/";
        String hash = getResultHash(workingTarget);
        LOGGER.info("hash: " + hash);
        compareCacheHash(hash);
    }

    public void compareCacheHash(String hash){
        if(refHash == null){
            refHash = hash;
        }
        Assert.assertEquals(hash, refHash);
    }

    public String getResultHash(String target){
        try {
            String result = this.mockMvc.perform(get("/wc?target=" + target)
                    .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            String[] splitted = result.split("[,:}]");
            return splitted[splitted.length-1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Random().toString();
    }
}
