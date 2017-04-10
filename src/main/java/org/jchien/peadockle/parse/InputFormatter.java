package org.jchien.peadockle.parse;

/**
 * @author jchien
 */
public class InputFormatter {
    public static String format(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        // assume everything is ascii
        for (int i=0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isAlphabetic(ch)) {
                sb.append(Character.toLowerCase(ch));
            } else if (Character.isWhitespace(ch)) {
                sb.append(' ');
            } else if (Character.isDigit(ch)) {
                sb.append(ch);
            }
            // ignore everything else, this includes +-=:/.,
        }
        return sb.toString();
    }
}
