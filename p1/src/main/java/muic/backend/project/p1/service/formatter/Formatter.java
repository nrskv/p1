package muic.backend.project.p1.service.formatter;

import muic.backend.project.p1.model.WordStats;

public abstract class Formatter {

    abstract public String format(WordStats wordStats);
    abstract public String toString();
}
