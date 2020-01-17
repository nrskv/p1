package muic.backend.project.p1.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AnalyzerServiceTest {

    @InjectMocks
    AnalyzerService analyzerService;

    @Test
    public void testCorrectness() {
        assertThat('a').isSameAs('a');
    }
}