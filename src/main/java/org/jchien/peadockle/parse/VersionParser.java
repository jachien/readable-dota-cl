package org.jchien.peadockle.parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jchien
 */
public class VersionParser {
    private final static Pattern VERSION_STRING_PATTERN = Pattern.compile("[*-=#\\s]*(\\d\\.\\d\\d[a-z]?)[:]?[*-=#\\s]*");

    /**
     * @param line  line from changelog
     * @return      patch version string or null if no version found
     */
    public String parseVersion(String line) {
        Matcher m = VERSION_STRING_PATTERN.matcher(line);
        if (m.matches()) {
            return m.group(1);
        }
        return null;
    }
}
