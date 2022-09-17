package main;

public final class Unit {
    private Land land;
    private int h1, h2;

    Unit() {
        land = null;
        h1 = -1;
        h2 = -1;
    }

    void add(final int ind) {
        System.out.println("Change this");
//        TODO: repair
        if (h1 == -1) {
            h1 = ind;
        } else {
            h2 = ind;
        }
    }

    void remove(final int ind) {
        if (h1 == ind) {
            h1 = -1;
        } else {
            h2 = -1;
        }
    }

    public void setLand(final Land land) {
        this.land = land;
    }

    int getH1() {
        return h1;
    }

    int getH2() {
        return h2;
    }

    Land getLand() {
        return land;
    }
}
