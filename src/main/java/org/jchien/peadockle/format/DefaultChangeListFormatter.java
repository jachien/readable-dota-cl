package org.jchien.peadockle.format;

import com.google.common.collect.ImmutableSet;
import org.jchien.peadockle.model.ChangeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author jchien
 */
public class DefaultChangeListFormatter implements ChangeListFormatter {
    private static final Set<Character> PREFIX_CHARS = ImmutableSet.<Character>builder()
            .add('*')
            .add('-')
            .add('=')
            .build();

    private static final Set<Character> SUFFIX_CHARS = ImmutableSet.<Character>builder()
            .add(':')
            .build();

    @Override
    public ChangeList format(ChangeList cl) {
        List<String> fmtChanges = new ArrayList<>(cl.getChanges().size());

        for (String change : cl.getChanges()) {
            int start = 0;
            while (start < change.length() && skipPrefixChar(change.charAt(start))) {
                start++;
            }

            if (start >= change.length()) {
                // blank line
                continue;
            }

            // todo deal with case sensitivity
            int longestMatch = 0;
            if (cl.hasLocalizedName() && change.startsWith(cl.getLocalizedName(), start)) {
                longestMatch = Math.max(longestMatch, cl.getLocalizedName().length());
            }

            if (cl.hasAlternateNames()) {
                for (String altName : cl.getAlternateNames()) {
                    if (change.startsWith(altName, start)) {
                        longestMatch = Math.max(longestMatch, altName.length());
                    }
                }
            }

            start += longestMatch;

            while (start < change.length() && skipSuffixChar(change.charAt(start))) {
                start++;
            }

            if (start >= change.length()) {
                // essentially blank line
                continue;
            }

            StringBuilder sb = new StringBuilder(change.substring(start));
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));

            fmtChanges.add(sb.toString());
        }

        return new ChangeList(
                cl.getEntityId(),
                cl.getName(),
                cl.getLocalizedName(),
                cl.getAlternateNames(),
                fmtChanges
        );
    }

    private boolean skipPrefixChar(char ch) {
        return Character.isWhitespace(ch) || PREFIX_CHARS.contains(ch);
    }

    private boolean skipSuffixChar(char ch) {
        return Character.isWhitespace(ch) || SUFFIX_CHARS.contains(ch);
    }
}
