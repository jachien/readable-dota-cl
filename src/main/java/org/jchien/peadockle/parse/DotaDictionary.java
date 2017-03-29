package org.jchien.peadockle.parse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author jchien
 */
public class DotaDictionary {
    private static final Gson GSON =  new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private String[] strings;
    private int[] ids;
    private Map<Integer, Entry> map;

    private DotaDictionary(String[] strings, int[] ids, Map<Integer, Entry> map) {
        this.strings = strings;
        this.ids = ids;
        this.map = map;
    }

    /**
     * @param line
     * @return
     */
    private List<Integer> getTerms(String line) {
        List<Integer> ret = Lists.newArrayList();

        String fmtLine = format(line);
        int start = 0;
        while (start < fmtLine.length()) {
            String substr = fmtLine.substring(start);
            int idx = Arrays.binarySearch(strings, substr);
            if (idx >= 0) {
                int id = ids[idx];
                ret.add(id);
            } else {
                idx = getInsertionPoint(idx) - 1;
                do {
                    if (idx >= 0 && fmtLine.startsWith(strings[idx], start)) {
                        int end = start + strings[idx].length();
                        // string must end on a word boundary to avoid matching cases like "Io" in "Ion shell"
                        if (end >= fmtLine.length() || Character.isWhitespace(fmtLine.charAt(end))) {
                            int id = ids[idx];
                            ret.add(id);
                        }
                    }
                    idx--;
                    // need to continue checking because names can be prefixes of other names (Sange, Sange and Yasha)
                } while (idx >= 0 && strings[idx+1].startsWith(strings[idx]));
            }

            int nextWhitespace = fmtLine.indexOf(' ', start);
            if (nextWhitespace < 0) {
                break;
            }
            start = nextWhitespace + 1;
        }

        return ret;
    }

    private int getInsertionPoint(int idx) {
        return -(idx + 1);
    }

    private static String format(String s) {
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
            // ignore everything else, this includes +-:/.,
        }
        return sb.toString();
    }

    private static DotaDictionary load(String path) throws FileNotFoundException {
        Entry[] entries = GSON.fromJson(new FileReader(path), Entry[].class);

        Map<Integer, Entry> map = Maps.newHashMap();
        List<FormattedEntry> fmtEntries = Lists.newArrayListWithCapacity(entries.length);
        for (Entry entry : entries) {
            map.put(entry.id, entry);

            fmtEntries.add(new FormattedEntry(entry.id, format(entry.localizedName)));
            if (entry.alternateNames != null) {
                for (String altName : entry.alternateNames) {
                    fmtEntries.add(new FormattedEntry(entry.id, format(altName)));
                }
            }
        }

        Collections.sort(fmtEntries, new Comparator<FormattedEntry>() {
            @Override
            public int compare(FormattedEntry o1, FormattedEntry o2) {
                return o1.fmtName.compareTo(o2.fmtName);
            }
        });

        String[] strings = fmtEntries.stream().map(e -> e.fmtName).toArray(String[]::new);
        int[] ids = fmtEntries.stream().mapToInt(e -> e.id).toArray();

        return new DotaDictionary(strings, ids, map);
    }

    private static class Entry {
        private int id;
        private String name;
        private String localizedName;
        private String[] alternateNames;

        private Entry() {
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", localizedName='" + localizedName + '\'' +
                    ", alternateNames=" + Arrays.toString(alternateNames) +
                    '}';
        }
    }

    private static class FormattedEntry {
        private int id;
        private String fmtName;

        private FormattedEntry(int id, String fmtName) {
            this.id = id;
            this.fmtName = fmtName;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        DotaDictionary heroDict = DotaDictionary.load("src/resources/heroes.json");
        System.out.println(Arrays.toString(heroDict.strings));

        DotaDictionary itemDict = DotaDictionary.load("src/resources/items.json");
        System.out.println(Arrays.toString(itemDict.strings));

        String patchFile = "src/resources/704_valve.txt";
        try (BufferedReader in = new BufferedReader(new FileReader(patchFile))) {
            String line;
            while ((line = in.readLine()) != null) {
                List<Integer> heroIds = heroDict.getTerms(line);
                List<Integer> itemIds = itemDict.getTerms(line);

                System.out.println(line);

                for (int id : heroIds) {
                    System.out.println("\t" + heroDict.map.get(id));
                }

                for (int id : itemIds) {
                    System.out.println("\t" + itemDict.map.get(id));
                }

            }
        } catch (IOException e) {
            System.err.println("unable to open");
        }
    }
}
