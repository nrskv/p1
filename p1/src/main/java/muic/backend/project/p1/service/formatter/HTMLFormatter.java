package muic.backend.project.p1.service.formatter;

import muic.backend.project.p1.model.WordStats;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class HTMLFormatter extends Formatter{

    @Override
    public String format(WordStats wordStats) {

        VelocityEngine velocity = new VelocityEngine();
        velocity.init();
        Template template = velocity.getTemplate("src/main/resources/templates/wordStats.vm");

        VelocityContext context = new VelocityContext();
        context.put("totalWordCount", wordStats.getTotalWordCount());
        context.put("topWords", wordStats.getTopWords());

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }

    @Override
    public String toString() {
        return MediaType.TEXT_HTML_VALUE;
    }
}
