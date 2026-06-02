package de.cripacx.hardermultiplayer.soulrevival;

public enum SoulRevivalStage {
    STAGE_1(1),
    STAGE_2(2),
    STAGE_3(3);

    private final int value;

    SoulRevivalStage(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static SoulRevivalStage fromValue(int value) {
        for (SoulRevivalStage stage : values()) {
            if (stage.value == value) {
                return stage;
            }
        }
        return STAGE_1;
    }
}
