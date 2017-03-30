package org.jchien.peadockle.htmlgen;

import freemarker.template.*;
import org.jchien.peadockle.model.Changelog;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author jchien
 */
public class ChangelogGenerator {
    private Configuration cfg;

    public ChangelogGenerator() throws IOException {
        Configuration cfg = new Configuration(new Version(2, 3, 20));
        cfg.setDirectoryForTemplateLoading(new File("src/resources"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        this.cfg = cfg;
    }

    public void generateChangelogHtml(Changelog changelog, String outputFile) throws IOException, TemplateException {
        Template template = cfg.getTemplate("changelog_template.ftl");

        Map<String, Object> input = new HashMap<>();
        input.put("changelog", changelog);

        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);

        Writer fileWriter = new FileWriter(new File(outputFile));
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }
    }
}
