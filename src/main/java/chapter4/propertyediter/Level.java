package chapter4.propertyediter;

public enum Level {
    GOLD(3), SILVER(2), BASIC(1);

    private int value;

    Level() {}

    Level(int value) {
        this.value = value;
    }

    public int intValue() {
        return value;
    }

    public static Level valueOf(int value) {
        switch(value) {
            case 1: return BASIC;
            case 2: return SILVER;
            case 3: return GOLD;
            default: throw new AssertionError("Unknown " + value);
        }
    }
}
