package main.mtm;

/**
 *
 * @author Agarimo
 */
public enum Compas {

    c2by4(2, 4),
    c3by4(3, 4),
    c4by4(4, 4),
    c6by8(6, 8);

    private final int count;
    private final int value;

    Compas(int count, int value) {
        this.count = count;
        this.value = value;
    }

    public int getCount() {
        return this.count;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        switch (this) {
            case c2by4:
                return "2/4";
            case c3by4:
                return "3/4";
            case c4by4:
                return "4/4";
            case c6by8:
                return "6/8";

            default:
                throw new IllegalArgumentException();
        }
    }
}
