package org.jchien.peadockle.model;

import java.util.List;

/**
 * @author jchien
 */
public class Changelog {
    private String patchNumber;
    private List<ChangeList> generalChanges;
    private List<ChangeList> itemChanges;
    private List<ChangeList> heroChanges;

    public Changelog(String patchNumber, List<ChangeList> generalChanges, List<ChangeList> itemChanges, List<ChangeList> heroChanges) {
        this.patchNumber = patchNumber;
        this.generalChanges = generalChanges;
        this.itemChanges = itemChanges;
        this.heroChanges = heroChanges;
    }

    public String getPatchNumber() {
        return patchNumber;
    }

    public List<ChangeList> getGeneralChanges() {
        return generalChanges;
    }

    public List<ChangeList> getItemChanges() {
        return itemChanges;
    }

    public List<ChangeList> getHeroChanges() {
        return heroChanges;
    }
}
