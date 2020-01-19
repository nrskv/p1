package muic.backend.project.p1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class CacheConcurrencyTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private MockMvc mockMvc;

    private final static Logger LOGGER = LoggerFactory.getLogger(CacheTest.class);

    private Long refFetchTime = null;

    @Test(threadPoolSize = 4, invocationCount = 4,  timeOut = 30000)
    public void testCacheConcurrency() throws Exception {
        String workingTarget = "https://muic.mahidol.ac.th/eng/";
        long fetchTime = getFetchTime(workingTarget);
        LOGGER.info(String.valueOf(fetchTime));
        checkFetchTime(fetchTime);
    }

    public void checkFetchTime(long time){
        if(refFetchTime == null){
            refFetchTime = time;
        }
        Assert.assertTrue(time <= refFetchTime );
    }

    public long getFetchTime(String target){
        Instant start = Instant.now();
        try {
            this.mockMvc.perform(get("/wc?target=" + target)
                    .accept(MediaType.TEXT_HTML));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Instant finish = Instant.now();
        return Duration.between(start, finish).toMillis();
    }
}
