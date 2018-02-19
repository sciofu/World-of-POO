package main;

final class Knight extends Hero {
    private float instantDmg, instantDot;
    static final int BASE_HP = 900, LVL_HP = 80;
    static final float BASE_DMG = 200, LVL_DMG = 30;
    static final float BHP_LIM = 20, MHP_LIM = 40;
    static final float INST_DOT = 100, LVL_INSTDOT = 40;
    static final float D_VIDE = 100;
    static final float L_AMP = 1.15f;
    static final int STUN_ROUNDS = 1;
    static final float WI_AMP = 0.8f, WD_AMP = 1.05f;
    static final float KD_AMP = 1.20f;
    static final float RI_AMP = 1.15f, RD_AMP = 0.8f;
    static final float PI_AMP = 1.10f, PD_AMP = 0.9f;

    public float getInstantDmg() {
        return instantDmg;
    }

    public void setInstantDmg(final float instantDmg) {
        this.instantDmg = instantDmg;
    }

    public float getInstantDot() {
        return instantDot;
    }

    public void setInstantDot(final float instantDot) {
        this.instantDot = instantDot;
    }

    public float getHpLim() {
        return hpLim;
    }

    public void setHpLim(final float hpLim) {
        this.hpLim = hpLim;
    }

    private float hpLim;

    int newHP() {
        return BASE_HP + LVL_HP * getLVL();
    }

    Knight(final int line, final int col, final int ind) {
        this.setLine(line);
        this.setCol(col);
        this.setInd(ind);
        this.setHp(newHP());
        this.setmaxHP(newHP());
        this.setRace('K');
    }

    void landMult() {
        instantDmg *= L_AMP;
        instantDot *= L_AMP;
    }

    void prepBatlle(final Land l) {
        instantDmg = BASE_DMG + LVL_DMG * getLVL();
        hpLim = BHP_LIM + getLVL();

        if (hpLim > MHP_LIM) {
            hpLim = MHP_LIM;
        }

        instantDot = INST_DOT + LVL_INSTDOT * getLVL();

        if (l == Land.Field) {
            landMult();
        }
    }

    void hpLimit(final Hero h) {
        int hhp = h.getHP();
        int hmhp = h.getmaxHP();

        if (hhp <= (hpLim / D_VIDE) * hmhp) {
            instantDmg = hhp;
        }
    }

    int isAttacked(final Hero h, final Land l) {
        return h.dealDmg(this, l);
    }

    int dealDmg(final Wizard w, final Land l) {
        prepBatlle(l);

        instantDmg *= WI_AMP;
        instantDot *= WD_AMP;

        hpLimit(w);

        w.getStuned(STUN_ROUNDS);

        return (Math.round(instantDmg) + Math.round(instantDot));
    }

    int dealDmg(final Knight k, final Land l) {
        prepBatlle(l);

        instantDot *= KD_AMP;

        hpLimit(k);

        k.getStuned(STUN_ROUNDS);
        return (Math.round(instantDmg) + Math.round(instantDot));
    }

    int dealDmg(final Rogue r, final Land l) {
        prepBatlle(l);

        instantDmg *= RI_AMP;
        instantDot *= RD_AMP;

        hpLimit(r);
        r.getStuned(STUN_ROUNDS);
        return (Math.round(instantDmg) + Math.round(instantDot));
    }

    int dealDmg(final Pyromancer p, final Land l) {
        prepBatlle(l);

        instantDmg *= PI_AMP;
        instantDot *= PD_AMP;

        hpLimit(p);
        p.getStuned(STUN_ROUNDS);
        return (Math.round(instantDmg) + Math.round(instantDot));
    }

    float calcDamage(final Hero h, final Land l) {
        prepBatlle(l);
        hpLimit(h);

        return instantDmg + instantDot;
    }
}
