package muic.backend.project.p1.controller;

import muic.backend.project.p1.model.WordStats;
import muic.backend.project.p1.service.AnalyzerService;
import muic.backend.project.p1.service.FormatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Optional;

@RestController
public class AnalyzerController {

    @Autowired
    private AnalyzerService analyzerService;

    @Autowired
    private FormatterService formatterService;

    @RequestMapping(value = "wc",
            method = RequestMethod.GET,
            produces={MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_JSON_VALUE,
                    MediaType.TEXT_PLAIN_VALUE},
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> analyze(@RequestParam String target,
                                     @RequestParam Optional<Boolean> force,
                                     @RequestHeader("Accept") String format){
        try {
            WordStats result = analyzerService.analyze(target, force.orElseGet(() -> false));

            String formattedResult = formatterService.format(format, result);

            return new ResponseEntity<>(formattedResult, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("There's a problem with the requested URL. This can be a result of user errors, networking problems, etc."
                    , HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
