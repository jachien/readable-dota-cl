package org.jchien.peadockle.parse;

import org.jchien.peadockle.format.ChangeListFormatter;
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
    private VersionParser versionParser = new VersionParser();
    private StopPhrases stopPhrases = new StopPhrases();
    private DotaDictionary heroDict;
    private DotaDictionary itemDict;

    public ChangelogParser(DotaDictionary heroDict, DotaDictionary itemDict) {
        this.heroDict = heroDict;
        this.itemDict = itemDict;
    }

    public Changelog generateChangelog(String patchPath, ChangeListFormatter fmt) throws IOException {
        Changelog changelog = generateChangelog(patchPath);
        List<ChangeList> gcl = buildFormattedChangeList(changelog.getGeneralChanges(), fmt);
        List<ChangeList> icl = buildFormattedChangeList(changelog.getItemChanges(), fmt);
        List<ChangeList> hcl = buildFormattedChangeList(changelog.getHeroChanges(), fmt);
        return new Changelog(changelog.getPatchNumber(), gcl, icl, hcl);
    }

    private List<ChangeList> buildFormattedChangeList(List<ChangeList> changeLists, ChangeListFormatter fmt) {
        return changeLists
                .stream()
                .map(cl -> fmt.format(cl))
                .collect(Collectors.toList());
    }

    public Changelog generateChangelog(String patchPath) throws IOException {
        List<String> generalChanges = new ArrayList<>();
        Map<Integer, List<String>> itemChangeMap = new HashMap<>();
        Map<Integer, List<String>> heroChangeMap = new HashMap<>();
        String version = null;


        try (BufferedReader in = new BufferedReader(new FileReader(patchPath))) {
            String line;
            while ((line = in.readLine()) != null) {
                boolean isVersionLine = false;
                if (version == null) {
                    // find no more than one version
                    String lineVersion = versionParser.parseVersion(line);
                    if (lineVersion != null) {
                        version = lineVersion;
                        isVersionLine = true;
                    }
                }

                if (!isVersionLine) {
                    Set<Integer> heroIds = heroDict.parseEntryIds(line);
                    Set<Integer> itemIds = itemDict.parseEntryIds(line);

                    /*if (heroIds.size() > 1) {
                        System.out.println(line + "\n\theroes: " + heroIds);
                    }

                    if (itemIds.size() > 1) {
                        System.out.println(line + "\n\titems:" + heroIds);
                    }*/

                    if (!heroIds.isEmpty()) {
                        int heroId = heroIds.stream().findFirst().get();
                        getOrCreateChangeList(heroChangeMap, heroId).add(line);
                    } else if (!itemIds.isEmpty()) {
                        int itemId = itemIds.stream().findFirst().get();
                        getOrCreateChangeList(itemChangeMap, itemId).add(line);
                    } else if (stopPhrases.allowed(line)) {
                        generalChanges.add(line);
                    }
                }
            }
        }

        System.out.println(generalChanges);
        System.out.println(itemChangeMap);
        System.out.println(heroChangeMap);

        // todo actually parse the patch number
        return buildChangelog(version, generalChanges, itemChangeMap, heroChangeMap);
    }

    private Changelog buildChangelog(String patchNumber,
                                     List<String> generalChanges,
                                     Map<Integer, List<String>> itemChangeMap,
                                     Map<Integer, List<String>> heroChangeMap) {

        List<ChangeList> gcl = new ArrayList<>();
        gcl.add(new ChangeList(null, null, null, null, generalChanges));

        List<ChangeList> icl = buildChangeList(itemChangeMap, itemDict);
        List<ChangeList> hcl = buildChangeList(heroChangeMap, heroDict);

        return new Changelog(patchNumber, gcl, icl, hcl);
    }

    private List<ChangeList> buildChangeList(Map<Integer, List<String>> changeMap, DotaDictionary dict) {
        Comparator<ChangeList> comparator = new Comparator<ChangeList>() {
            @Override
            public int compare(ChangeList o1, ChangeList o2) {
                String n1 = dict.getEntry(o1.getEntityId()).getLocalizedName();
                String n2 = dict.getEntry(o2.getEntityId()).getLocalizedName();
                return n1.compareTo(n2);
            }
        };

        List<ChangeList> cl = changeMap
                .entrySet()
                .stream()
                .map(e -> {
                    Entry entry = dict.getEntry(e.getKey());
                    return new ChangeList(
                            entry.getId(),
                            entry.getName(),
                            entry.getLocalizedName(),
                            entry.getAlternateNames(),
                            e.getValue());
                })
                .sorted(comparator)
                .collect(Collectors.toList());

        return cl;
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
