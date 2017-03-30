package org.jchien.peadockle.parse;

import org.jchien.peadockle.model.ChangeList;
import org.jchien.peadockle.model.Changelog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jchien
 */
public class ChangelogParser {
    private DotaDictionary heroDict;
    private DotaDictionary itemDict;

    public ChangelogParser(DotaDictionary heroDict, DotaDictionary itemDict) {
        this.heroDict = heroDict;
        this.itemDict = itemDict;
    }

    public Changelog generateChangelog(String patchPath) throws IOException {
        List<String> generalChanges = new ArrayList<>();
        Map<Integer, List<String>> itemChangeMap = new HashMap<>();
        Map<Integer, List<String>> heroChangeMap = new HashMap<>();

        try (BufferedReader in = new BufferedReader(new FileReader(patchPath))) {
            String line;
            while ((line = in.readLine()) != null) {
                Set<Integer> heroIds = heroDict.parseEntryIds(line);
                Set<Integer> itemIds = itemDict.parseEntryIds(line);

                if (!heroIds.isEmpty()) {
                    for (int heroId : heroIds) {
                        // todo format line, strip the entry name if the line starts with it
                        getOrCreateChangeList(heroChangeMap, heroId).add(line);
                    }
                } else if (!itemIds.isEmpty()) {
                    for (int itemId : itemIds) {
                        // todo format line, strip the entry name if the line starts with it
                        getOrCreateChangeList(itemChangeMap, itemId).add(line);
                    }
                } else {
                    // todo interpret general changes in groups based on double newlines
                    // todo better empty line detection
                    if (!line.isEmpty()) {
                        generalChanges.add(line);
                    }
                }
            }
        }

        System.out.println(generalChanges);
        System.out.println(itemChangeMap);
        System.out.println(heroChangeMap);

        return buildChangelog("7.xx", generalChanges, itemChangeMap, heroChangeMap);
    }

    private Changelog buildChangelog(String patchNumber,
                                     List<String> generalChanges,
                                     Map<Integer, List<String>> itemChangeMap,
                                     Map<Integer, List<String>> heroChangeMap) {
        List<ChangeList> gcl = new ArrayList<>();
        gcl.add(new ChangeList(null, null, null, generalChanges));

        Comparator<ChangeList> iclComparator = new Comparator<ChangeList>() {
            @Override
            public int compare(ChangeList o1, ChangeList o2) {
                String n1 = itemDict.getEntry(o1.getEntityId()).getLocalizedName();
                String n2 = itemDict.getEntry(o2.getEntityId()).getLocalizedName();
                return n1.compareTo(n2);
            }
        };
        List<ChangeList> icl = itemChangeMap.entrySet()
                .stream()
                .map(e -> {
                    Entry entry = itemDict.getEntry(e.getKey());
                    return new ChangeList(
                        entry.getId(),
                        entry.getName(),
                        entry.getLocalizedName(),
                        e.getValue());
                })
                .sorted(iclComparator)
                .collect(Collectors.toList());

        Comparator<ChangeList> hclComparator = new Comparator<ChangeList>() {
            @Override
            public int compare(ChangeList o1, ChangeList o2) {
                String n1 = heroDict.getEntry(o1.getEntityId()).getLocalizedName();
                String n2 = heroDict.getEntry(o2.getEntityId()).getLocalizedName();
                return n1.compareTo(n2);
            }
        };
        List<ChangeList> hcl = heroChangeMap.entrySet()
                .stream()
                .map(e -> {
                    Entry entry = heroDict.getEntry(e.getKey());
                    return new ChangeList(
                            entry.getId(),
                            entry.getName(),
                            entry.getLocalizedName(),
                            e.getValue());
                })
                .sorted(hclComparator)
                .collect(Collectors.toList());

        return new Changelog(patchNumber, gcl, icl, hcl);
    }

    private List<String> getOrCreateChangeList(Map<Integer, List<String>> map, int id) {
        List<String> ret = map.get(id);
        if (ret == null) {
            ret = new ArrayList<>();
            map.put(id, ret);
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        DotaDictionary heroDict = DotaDictionary.load("src/resources/heroes.json");
        DotaDictionary itemDict = DotaDictionary.load("src/resources/items.json");
        ChangelogParser clp = new ChangelogParser(heroDict, itemDict);
        Changelog changelog = clp.generateChangelog("src/resources/704_valve.txt");

        for (ChangeList cl : changelog.getGeneralChanges()) {
            for (String change : cl.getChanges()) {
                System.out.println(change);
            }
        }
        System.out.println("\n\nItems\n=====");
        for (ChangeList cl : changelog.getItemChanges()) {
            for (String change : cl.getChanges()) {
                System.out.println(change);
            }
        }
        System.out.println("\n\nHeroes\n======");
        for (ChangeList cl : changelog.getHeroChanges()) {
            for (String change : cl.getChanges()) {
                System.out.println(change);
            }
        }
    }
}
