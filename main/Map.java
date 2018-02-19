package main;

import java.util.ArrayList;

public final class Map {
    private Unit[][] map;
    private int n, m;

    Map() {
    }

    Map(final int n, final int m) {
        this.n = n;
        this.m = m;
        map = new Unit[n][m];
    }

    public void arrive(final int line, final int col, final int ind) {
        map[line][col].add(ind);
    }

    public void leave(final int line, final int col, final int ind) {
        map[line][col].remove(ind);
    }

    public void setLand(final int i, final int j, final String l) {
        Land land = null;
        if (l.equals("W")) {
            land = Land.Wood;
        }
        if (l.equals("L")) {
            land = Land.Field;
        }
        if (l.equals("V")) {
            land = Land.Lava;
        }
        if (l.equals("D")) {
            land = Land.Desert;
        }
        map[i][j] = new Unit();
        map[i][j].setLand(land);
    }

    public void fight(final ArrayList<Hero> heroes, final int rounds, final int p) {
        int i, j;
        for (i = 0; i < p; ++i) {
            Hero h1 = heroes.get(i);
            if (!h1.isDead()) {
                for (j = i + 1; j < p; ++j) {
                    Hero h2 = heroes.get(j);
                    if (h1.getC() == h2.getC() && h1.getL() == h2.getL()) {
                        if (!h2.isDead()) {
                            int d1, d2;
                            int l = h1.getL();
                            int c = h1.getC();
                            d1 = h1.isAttacked(h2, map[l][c].getLand());
                            d2 = h2.isAttacked(h1, map[l][c].getLand());

                            h1.takeDmg(d1);
                            h2.takeDmg(d2);

                            if (h1 instanceof Rogue) {
                                h1.plusStab();
                            }
                            if (h2 instanceof Rogue) {
                                h2.plusStab();
                            }
                            if (!h1.isDead()) {
                                h1.checkBattle(h2);
                            }
                            if (!h2.isDead()) {
                                h2.checkBattle(h1);
                            }
                     }
                }
            }
        }
    }
}

    void applyDot(final ArrayList<Hero> heroes, final int p) {
        Hero h;
        for (int i = 0; i < p; ++i) {
            h = heroes.get(i);
            if (h.getDot()) {
                h.takeDot(this);
            }
        }
    }
}
