package org.jchien.peadockle.model;

import java.util.List;

/**
 * @author jchien
 */
public class ChangeList {
    private Integer entityId;
    private String name;
    private String localizedName;
    private String[] alternateNames;
    private List<String> changes;

    public ChangeList(Integer entityId, String name, String localizedName, String[] alternateNames, List<String> changes) {
        this.entityId = entityId;
        this.name = name;
        this.localizedName = localizedName;
        this.alternateNames = alternateNames;
        this.changes = changes;
    }

    public boolean hasEntityId() {
        return entityId != null;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public boolean hasName() {
        return name != null;
    }

    public String getName() {
        return name;
    }

    public boolean hasLocalizedName() {
        return localizedName != null;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public boolean hasAlternateNames() {
        return alternateNames != null && alternateNames.length > 0;
    }

    public String[] getAlternateNames() {
        return alternateNames;
    }

    public List<String> getChanges() {
        return changes;
    }
}
