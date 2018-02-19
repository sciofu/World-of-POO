package main;

public final class Wizard extends Hero {
    private float instantDmg, deflect;
    static final float L_MULT = 1.1f, D_VIDE = 100, ONE = 1f;
    static final int BASE_DMG = 20, INS_LVL = 5;
    static final int BASE_DEF = 35, LVL_DEF = 2, MAX_DEF = 70;
    static final float W_MUL = 1.05f, PHP = 0.3f;
    static final float K_INS = 1.20f, K_DEF = 1.40f;
    static final float R_INS = 0.8f, R_DEF = 1.20f;
    static final float P_INS = 0.9f, P_DEF = 1.30f;
    static final int BASE_HP = 400, LVL_HP = 30;
    int newHP() {
        return BASE_HP + LVL_HP * getLVL();
    }

    Wizard(final int line, final int col, final int ind) {
        this.setLine(line);
        this.setCol(col);
        this.setInd(ind);
        this.setHp(newHP());
        this.setmaxHP(newHP());
        this.setRace('W');
    }

    void prepBattle(final Land l) {
        instantDmg = BASE_DMG + INS_LVL * getLVL();
        deflect = BASE_DEF + LVL_DEF * getLVL();
        if (deflect > MAX_DEF) {
            deflect = MAX_DEF;
        }

        if (l == Land.Desert) {
            landMult();
        }
    }

    void landMult() {
        instantDmg = Math.round(L_MULT * instantDmg);
    }

    int isAttacked(final Hero h, final Land l) {
        return h.dealDmg(this, l);
    }

    int dealDmg(final Wizard w, final Land l) {
        prepBattle(l);

        instantDmg *= W_MUL;

        instantDmg = (instantDmg / D_VIDE) * Math.min(PHP * w.getmaxHP(), w.getHP() * ONE);

        return (Math.round(instantDmg));
    }

    int dealDmg(final Knight k, final Land l) {
        prepBattle(l);

        instantDmg *= K_INS;

        float takenDmg = Math.round(k.calcDamage(this, l));

        instantDmg = (instantDmg / D_VIDE) * Math.min(PHP * k.getmaxHP(), k.getHP() * ONE);
        float deflected = takenDmg * (deflect / D_VIDE);
        deflected = Math.round(deflected * K_DEF);
        if (l == Land.Desert) {
            deflected *= L_MULT;
        }
        return (Math.round(instantDmg) + Math.round(deflected));
    }

    int dealDmg(final Rogue r, final Land l) {
        prepBattle(l);

        instantDmg *= R_INS;

        float takenDmg = 0;
        takenDmg = Math.round(r.calcDamage(this, l));
        instantDmg = (instantDmg / D_VIDE) * Math.min(PHP * r.getmaxHP(), r.getHP() * ONE);
        float deflected = takenDmg * (deflect / D_VIDE);
        if (l == Land.Desert) {
            deflected *= L_MULT;
        }
        deflected = Math.round(deflected * R_DEF);
        return (Math.round(instantDmg) + Math.round(deflected));
    }

    int dealDmg(final Pyromancer p, final Land l) {
        prepBattle(l);

        instantDmg *= P_INS;

        float takenDmg = Math.round(p.calcDamage(this, l));

        instantDmg = (instantDmg / D_VIDE) * Math.min(PHP * p.getmaxHP(), p.getHP() * ONE);
        float deflected = Math.round(takenDmg * (deflect / D_VIDE));
        deflected = Math.round(deflected * P_DEF);
        if (l == Land.Desert) {
            deflected *= L_MULT;
        }

        return (Math.round(instantDmg) + Math.round(deflected));
    }
}
