package muic.backend.project.p1.service.formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FormatterFactory {

    @Autowired
    private List<Formatter> formatterList;

    private final Map<String, Formatter> formatterCache = new HashMap<>();
    private final List<String> formatterNameList = new ArrayList<>();

    @PostConstruct
    private void initFormatterCache(){
        for (Formatter formatter : formatterList) {
            formatterCache.put(formatter.toString(), formatter);
            formatterNameList.add(formatter.toString());
        }
    }

    public Formatter getFormatter(String format) {
        return formatterCache.get(format);
    }

    public List<String> getFormatterNameList() { return formatterNameList; }

}
