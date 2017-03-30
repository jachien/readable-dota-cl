package org.jchien.peadockle;

import freemarker.template.TemplateException;
import org.jchien.peadockle.htmlgen.ChangelogGenerator;
import org.jchien.peadockle.model.Changelog;
import org.jchien.peadockle.parse.ChangelogParser;
import org.jchien.peadockle.parse.DotaDictionary;

import java.io.IOException;

/**
 * @author jchien
 */
public class Driver {
    public static void main(String[] args) throws IOException, TemplateException {
        DotaDictionary heroDict = DotaDictionary.load("src/resources/heroes.json");
        DotaDictionary itemDict = DotaDictionary.load("src/resources/items.json");

        ChangelogParser clp = new ChangelogParser(heroDict, itemDict);
        Changelog changelog = clp.generateChangelog("src/resources/704_valve.txt");

        ChangelogGenerator gen = new ChangelogGenerator();
        gen.generateChangelogHtml(changelog, "E:\\jchien\\code\\readable-dota-cl\\changelog.html");
    }
}
