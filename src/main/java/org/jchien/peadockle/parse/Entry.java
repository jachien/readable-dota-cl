package org.jchien.peadockle.parse;

import com.google.common.annotations.VisibleForTesting;

import java.util.Arrays;

/**
 * Poorly named, this used to be an inner class. Contains parsed hero and item data from json.
 * @author jchien
 */
public class Entry {
    private int id;
    private String name;
    private String localizedName;
    private String[] alternateNames;

    /**
     * This class is intended to be populated from json by gson, so there is no public constructor.
     */
    private Entry() {
    }

    @VisibleForTesting
    Entry(int id, String name, String localizedName, String[] alternateNames) {
        this.id = id;
        this.name = name;
        this.localizedName = localizedName;
        this.alternateNames = alternateNames;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String[] getAlternateNames() {
        return alternateNames;
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
