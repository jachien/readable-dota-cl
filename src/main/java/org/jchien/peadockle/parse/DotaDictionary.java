package org.jchien.peadockle.parse;

import com.google.common.annotations.VisibleForTesting;
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
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jchien
 */
public class DotaDictionary {
    private static final Gson GSON =  new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    // todo check on item load that these ids haven't changed
    // special case: for each Sange and Yasha identified, remove one Sange and one Yasha from matched terms
    private static final int SANGE = 162;
    private static final int YASHA = 170;
    private static final int SANGE_AND_YASHA = 154;

    private String[] strings;
    private int[] ids;
    private Map<Integer, Entry> map;

    /**
     * Use {@link #load(String)} to create an DotaDictionary instance.
     *
     * @param strings
     * @param ids
     * @param map
     */
    private DotaDictionary(String[] strings, int[] ids, Map<Integer, Entry> map) {
        this.strings = strings;
        this.ids = ids;
        this.map = map;
    }

    public Entry getEntry(int id) {
        Entry ret = map.get(id);
        if (ret != null) {
            return ret;
        }
        throw new NoSuchElementException("no dictionary entry for id: " + id);
    }

    /**
     * @param line
     * @return
     */
    public Set<Integer> parseEntryIds(String line) {
        List<Integer> ids = Lists.newArrayList();

        String fmtLine = InputFormatter.format(line);
        int start = 0;
        while (start < fmtLine.length()) {
            String substr = fmtLine.substring(start);
            int idx = Arrays.binarySearch(strings, substr);
            if (idx >= 0) {
                int id = this.ids[idx];
                ids.add(id);
            } else {
                idx = getInsertionPoint(idx) - 1;
                do {
                    if (idx >= 0 && fmtLine.startsWith(strings[idx], start)) {
                        int end = start + strings[idx].length();
                        // string must end on a word boundary to avoid matching cases like "Io" in "Ion shell"
                        // maybe it should look for a non-alphanumeric char instead of end of line or whitespace
                        if (end >= fmtLine.length() || Character.isWhitespace(fmtLine.charAt(end))) {
                            int id = this.ids[idx];
                            ids.add(id);
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

        return dedupeSny(ids);
    }

    private int getInsertionPoint(int idx) {
        return -(idx + 1);
    }


    private Set<Integer> dedupeSny(List<Integer> ids) {
        Set<Integer> ret = new LinkedHashSet<>(ids);

        // my intellij is definitely too old, this is not a compile error
        Map<Integer, Long> idCounts = ids.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long snyCnt = getCount(idCounts, SANGE_AND_YASHA);
        long sangeCnt = getCount(idCounts, SANGE);
        long yashaCnt = getCount(idCounts, YASHA);

        if (snyCnt >= sangeCnt) {
            ret.remove(SANGE);
        }
        if (snyCnt >= yashaCnt) {
            ret.remove(YASHA);
        }

        return ret;
    }

    private long getCount(Map<Integer, Long> countMap, int key) {
        Long ret = countMap.get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }



    public static DotaDictionary load(String path) throws FileNotFoundException {
        Entry[] entries = GSON.fromJson(new FileReader(path), Entry[].class);
        return load(entries);
    }

    @VisibleForTesting
    static DotaDictionary load(Entry[] entries) {
        Map<Integer, Entry> map = Maps.newHashMap();
        List<FormattedEntry> fmtEntries = Lists.newArrayListWithCapacity(entries.length);
        for (Entry entry : entries) {
            map.put(entry.getId(), entry);
            String fmtLocalName = InputFormatter.format(entry.getLocalizedName());
            fmtEntries.add(new FormattedEntry(entry.getId(), fmtLocalName));
            if (entry.getAlternateNames() != null) {
                for (String altName : entry.getAlternateNames()) {
                    String fmtAltName = InputFormatter.format(altName);
                    fmtEntries.add(new FormattedEntry(entry.getId(), fmtAltName));
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
                Collection<Integer> heroIds = heroDict.parseEntryIds(line);
                Collection<Integer> itemIds = itemDict.parseEntryIds(line);

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
