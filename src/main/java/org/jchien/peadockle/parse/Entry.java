package org.jchien.peadockle.parse;

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

    private Entry() {
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
