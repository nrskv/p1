package muic.backend.project.p1.service;

import muic.backend.project.p1.model.WordStats;
import muic.backend.project.p1.service.formatter.Formatter;
import muic.backend.project.p1.service.formatter.FormatterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class FormatterService {

    @Autowired
    private FormatterFactory formatterFactory;

    public String format(String format, WordStats wordStats){
        if(!formatterFactory.getFormatterNameList().contains(format)) format = MediaType.TEXT_HTML_VALUE;
        Formatter formatter = formatterFactory.getFormatter(format);
        return formatter.format(wordStats);
    }
}
