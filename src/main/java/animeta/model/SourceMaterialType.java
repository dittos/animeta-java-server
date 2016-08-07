package animeta.model;

public enum SourceMaterialType {
    MANGA("manga"),
    ORIGINAL("original"),
    LIGHT_NOVEL("lightnovel"),
    GAME("game"),
    FOUR_KOMA("4koma"),
    VISUAL_NOVEL("visualnovel"),
    NOVEL("novel");

    private String legacyName;

    SourceMaterialType(String legacyName) {
        this.legacyName = legacyName;
    }

    public String getLegacyName() {
        return legacyName;
    }

    public static SourceMaterialType create(String s) {
        for (SourceMaterialType type : values()) {
            if (type.legacyName.equals(s)) {
                return type;
            }
        }
        return null;
    }
}
