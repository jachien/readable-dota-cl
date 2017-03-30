package org.jchien.peadockle.model;

import java.util.List;

/**
 * @author jchien
 */
public class ChangeList {
    private Integer entityId;
    private String name;
    private String localizedName;
    private List<String> changes;

    public ChangeList(Integer entityId, String name, String localizedName, List<String> changes) {
        this.entityId = entityId;
        this.name = name;
        this.localizedName = localizedName;
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

    public List<String> getChanges() {
        return changes;
    }
}
