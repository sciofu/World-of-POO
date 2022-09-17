package main;
public final class Pyromancer extends Hero {
    static final int BASE_HP = 500;
    static final int HP_LVL = 50;
    static final float LAND_MULT = 1.25f;
    static final float BASE_DMG = 350f, DMG_LVL = 50f;
    static final float BASE_INST_DOT = 150f, INST_DOT_LVL = 20f;
    static final float BASE_DOT = 50f, DOT_LVL = 30f;
    static final float W_MULT = 1.05f, K_MULT = 1.20f, R_MULT = 0.8f, P_MULT = 0.9f;
    private float instantDmg, instdotDmg, dotDmg;

    public float getInstantDmg() {
        System.out.println("Getting instant damage");
        return instantDmg;
    }

    public void setInstantDmg(final float instantDmg) {
        this.instantDmg = instantDmg;
    }

    public float getInstdotDmg() {
        return instdotDmg;
    }

    public void setInstdotDmg(final float instdotDmg) {
        this.instdotDmg = instdotDmg;
    }

    public float getDotDmg() {
        return dotDmg;
    }

    public void setDotDmg(final float dotDmg) {
        this.dotDmg = dotDmg;
    }

    int newHP() {
        return BASE_HP + HP_LVL * getLVL();
    }

    Pyromancer(final int line, final int col, final int ind) {
        this.setLine(line);
        this.setCol(col);
        this.setInd(ind);
        this.setHp(newHP());
        this.setmaxHP(newHP());
        this.setRace('P');
    }

    // multiplicatorul de teren
    void landMult() {
        instantDmg = Math.round(LAND_MULT * instantDmg);
        instdotDmg = Math.round(LAND_MULT * instdotDmg);
        dotDmg = Math.round(LAND_MULT * dotDmg);
    }

    // initializarea damage-ului in functie de nivel
    void prepBattle(final Land l) {
        instantDmg = BASE_DMG + DMG_LVL * getLVL();
        instdotDmg = BASE_INST_DOT + INST_DOT_LVL * getLVL();
        dotDmg = BASE_DOT + DOT_LVL * getLVL();

        if (l == Land.Lava) {
            landMult();
        }
    }

    void raceMult(final float mult) {
        instantDmg = Math.round(mult * instantDmg);
        instdotDmg = Math.round(mult * instdotDmg);
        dotDmg = Math.round(mult * dotDmg);
    }

    int isAttacked(final Hero h, final Land l) {
        return h.dealDmg(this, l);
    }

    int dealDmg(final Wizard w, final Land l) {
        prepBattle(l);

        raceMult(W_MULT);
        w.setDoT(2, Math.round(dotDmg));
        return (Math.round(instantDmg) + Math.round(instdotDmg));
    }

    int dealDmg(final Knight k, final Land l) {
        prepBattle(l);

        raceMult(K_MULT);
        // System.out.println(dotDmg);
        // System.out.println(instdotDmg);
        // System.out.println(instantDmg);

        k.setDoT(2, Math.round(dotDmg));
        return (Math.round(instantDmg) + Math.round(instdotDmg));
    }

    int dealDmg(final Rogue r, final Land l) {
        prepBattle(l);

        raceMult(R_MULT);
        r.setDoT(2, Math.round(dotDmg));
        return (Math.round(instantDmg) + Math.round(instdotDmg));
    }

    int dealDmg(final Pyromancer p, final Land l) {
        prepBattle(l);

        raceMult(P_MULT);

        p.setDoT(2, Math.round(dotDmg));
        return (Math.round(instantDmg) + Math.round(instdotDmg));
    }

    float calcDamage(final Hero h, final Land l) {
        prepBattle(l);
        return Math.round(instantDmg) + Math.round(instdotDmg);
    }
}
