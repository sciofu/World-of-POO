package main;

public final class Rogue extends Hero {
    private int stabAtt;
    static final int BASE_HP = 600, LVL_HP = 40;
    static final float BASE_INST_DMG = 200, LVL_INST_DMG = 20;
    static final float BASE_DOT_DMG = 40, LVL_DOT_DMG = 10;
    static final float L_MULT = 1.15f, CRIT = 1.5f;;
    static final int MOD = 3;
    static final float W_MULT = 1.25f, K_INS = 0.9f, K_DOT = 0.8f;
    static final float R_INS = 1.2f, R_DOT = 0.9f, P_INS = 1.25f, P_DOT = 1.20f;
    static final int R = 3, WR = 6;
    public int getstabAtt() {
        return stabAtt;
    }

    public void setstabAtt(final int stabatt) {
        this.stabAtt = stabatt;
    }

    public float getinstantDmg() {
        return instantDmg;
    }

    public void setinstantDmg(final float instantdmg) {
        this.instantDmg = instantdmg;
    }

    public float getdotDmg() {
        return dotDmg;
    }

    public void setdotDmg(final float dotdmg) {
        this.dotDmg = dotdmg;
    }

    private float instantDmg, dotDmg;

    int newHP() {
        return BASE_HP + LVL_HP * getLVL();
    }

    Rogue(final int line, final int col, final int ind) {
        this.setLine(line);
        this.setCol(col);
        this.setInd(ind);
        this.setHp(newHP());
        this.setmaxHP(newHP());
        this.setRace('R');
        this.stabAtt = 0;
    }

    void prepBattle(final Land l) {
        instantDmg = BASE_INST_DMG + LVL_INST_DMG * getLVL();
        dotDmg = BASE_DOT_DMG + LVL_DOT_DMG * getLVL();

        if (l == Land.Wood) {
            landMult();
        }
    }

    void landMult() {
        instantDmg = Math.round(L_MULT * instantDmg);
        dotDmg = Math.round(L_MULT * dotDmg);
    }

    void applycrit(final Land l) {
        if (l == Land.Wood && stabAtt % MOD == 0) {
            instantDmg = Math.round(CRIT * instantDmg);
        }
    }

    int isAttacked(final Hero h, final Land l) {
        return h.dealDmg(this, l);
    }

    int dealDmg(final Wizard w, final Land l) {
        prepBattle(l);
        applycrit(l);

        instantDmg *= W_MULT;
        dotDmg *= W_MULT;

        int rounds = R;
        if (l == Land.Wood) {
            rounds = WR;
        }

        w.setParalysis(Math.round(dotDmg), rounds);
        return (Math.round(instantDmg) + Math.round(dotDmg));
    }

    int dealDmg(final Knight k, final Land l) {
        prepBattle(l);
        applycrit(l);

        instantDmg *= K_INS;
        dotDmg *= K_DOT;

        int rounds = R;
        if (l == Land.Wood) {
            rounds = WR;
        }

        k.setParalysis(Math.round(dotDmg), rounds);

        // System.out.println(Math.round(dotDmg) + Math.round(instantDmg));
        return (Math.round(instantDmg) + Math.round(dotDmg));
    }

    int dealDmg(final Rogue r, final Land l) {
        prepBattle(l);
        applycrit(l);

        instantDmg *= R_INS;
        dotDmg *= R_DOT;

        int rounds = R;
        if (l == Land.Wood) {
            rounds = WR;
        }

        r.setParalysis(Math.round(dotDmg), rounds);

        return (Math.round(instantDmg) + Math.round(dotDmg));
    }

    int dealDmg(final Pyromancer p, final Land l) {
        prepBattle(l);
        applycrit(l);

        instantDmg *= P_INS;
        dotDmg *= P_DOT;
        int rounds = R;
        if (l == Land.Wood) {
            rounds = WR;
        }

        p.setParalysis(Math.round(dotDmg), rounds);

        return (Math.round(instantDmg) + Math.round(dotDmg));
    }

    float calcDamage(final Hero h, final Land l) {
        prepBattle(l);
        applycrit(l);

        return Math.round(instantDmg) + Math.round(dotDmg);
    }

    public void plusStab() {
        ++stabAtt;
    }
}
