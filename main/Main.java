package main;

import java.io.IOException;
import java.util.ArrayList;
import fileio.FileSystem;

final class Main {
    private Main() {
    }

    public static void main(final String[] args) throws IOException {
        FileSystem file = new FileSystem(args[0], args[1]);
        int n, m, p, i, j, line, col, r;
        String c;
        ArrayList<Hero> heroes = new ArrayList<Hero>();
        n = file.nextInt();
        m = file.nextInt();

        Map map = new Map(n, m);

        System.out.println("new branch");

        for (i = 0; i < n; ++i) {
            c = file.nextWord();
            for (j = 0; j < m; ++j) {
                // System.out.println(c.substring(j, j+1));
                map.setLand(i, j, c.substring(j, j + 1));
            }
        }

        p = file.nextInt();

        for (i = 0; i < p; ++i) {
            c = file.nextWord();
            line = file.nextInt();
            col = file.nextInt();
            Hero h;
            if (c.equals("W")) {
                h = new Wizard(line, col, i);
                heroes.add(h);
                map.arrive(line, col, i);
            }

            if (c.equals("R")) {
                h = new Rogue(line, col, i);
                heroes.add(h);
                map.arrive(line, col, i);
            }

            if (c.equals("K")) {
                h = new Knight(line, col, i);
                heroes.add(h);
                map.arrive(line, col, i);
            }

            if (c.equals("P")) {
                h = new Pyromancer(line, col, i);
                heroes.add(h);
                map.arrive(line, col, i);
            }
        }

        r = file.nextInt();

        for (i = 1; i <= r; ++i) {
            map.applyDot(heroes, p);
            c = file.nextWord();
            for (j = 0; j < p; ++j) {
                Hero h = heroes.get(j);
                if (!h.isDead()) {
                    // System.out.println(h.getRace() + "" + h.getind() + " " +
                    // c.charAt(j));
                    h.movement(c.charAt(j), map);
                }
            }
            map.fight(heroes, r - i, p);
        }

        for (i = 0; i < p; ++i) {
            Hero h = heroes.get(i);
            if (h.isDead()) {
                file.writeWord(h.getRace() + " dead\n");
            } else {
                file.writeWord(h.getRace() + " " + h.getLVL() + " ");
                file.writeWord(h.getxp() + " ");
                file.writeWord(h.getHP() + " " + h.getL() + " " + h.getC() + "\n");
            }
        }
        file.close();
    }
}
