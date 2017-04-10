package org.jchien.peadockle.parse;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * // like stopwords, but stop phrases
 * @author jchien
 */
public class StopPhrases {
    private static final Set<String> PHRASES = ImmutableSet.<String>builder()
            .add("")
            .add("general")
            .add("general changes")
            .add("items")
            .add("item changes")
            .add("heroes")
            .add("hero changes")
            .build();


    public boolean allowed(String line) {
        String fmtLine = InputFormatter.format(line);
        if (PHRASES.contains(fmtLine)) {
            return false;
        }
        return true;
    }
}
