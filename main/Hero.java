package main;

abstract class Hero {
    static final int MAXPAYNE = 250;
    private int maxhp, hp, xp, level, maxxp = MAXPAYNE;
    public int getHp() {
        return hp;
    }

    public void setHp(final int hp) {
        this.hp = hp;
    }

    public void setXp(final int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public int getMaxxp() {
        return maxxp;
    }

    public void setMaxxp(final int maxxp) {
        this.maxxp = maxxp;
    }

    public int getLine() {
        return line;
    }

    public void setLine(final int line) {
        this.line = line;
    }

    public int getCol() {
        return col;
    }

    public void setCol(final int col) {
        this.col = col;
    }

    public int getDotRounds() {
        return dotRounds;
    }

    public void setDotRounds(final int dotRounds) {
        this.dotRounds = dotRounds;
    }

    public int getDotpRound() {
        return dotpRound;
    }

    public void setDotpRound(final int dotpRound) {
        this.dotpRound = dotpRound;
    }

    public int getStunRounds() {
        return stunRounds;
    }

    public void setStunRounds(final int stunRounds) {
        this.stunRounds = stunRounds;
    }

    public int getInd() {
        return ind;
    }

    public void setInd(final int ind) {
        this.ind = ind;
    }

    public boolean isStun() {
        return stun;
    }

    public void setStun(final boolean stun) {
        this.stun = stun;
    }

    public void setDead(final boolean dead) {
        this.dead = dead;
    }

    public void setDot(final boolean dot) {
        this.dot = dot;
    }

    public void setRace(final char race) {
        this.race = race;
    }

    // hero's combat stats
    private int line, col, dotRounds, dotpRound, stunRounds, ind;
    private boolean dead, dot, stun;
    // battle stats
    private char race;
    static final int DETXP = 200, FXP = 250, LXP = 50, CXP = 40;

    Hero() {
    }

    public int getmaxHP() {
        return maxhp;
    }
    public void setmaxHP(final int mhp) {
        this.maxhp = mhp;
    }

    public int getHP() {
        return hp;
    }

    public int getxp() {
        return xp;
    }

    public int getLVL() {
        return level;
    }

    public int getL() {
        return line;
    }

    public int getC() {
        return col;
    }

    public int getind() {
        return ind;
    }

    public char getRace() {
        return race;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean getDot() {
        return dot;
    }

    void takeDmg(final int dmg) {
        hp -= dmg;
        if (hp <= 0) {
            dead = true;
        }
    }

    void setDoT(final int dotR, final int dotD) {
        this.stun = false;
        this.dot = true;
        this.dotRounds = dotR;
        this.dotpRound = dotD;
    }

    void getStuned(final int rounds) {
        setDoT(0, 0);
        this.stun = true;
        this.stunRounds = rounds;
    }

    void setParalysis(final int ot, final int rounds) {
        this.stun = true;
        this.dot = true;
        this.dotRounds = rounds;
        this.stunRounds = rounds;
        this.dotpRound = ot;
    }

    void checkBattle(final Hero h) {
        if (h.isDead()) {
            // System.out.println(this.getRace() + "->" + h.getRace());
            int gxp = Math.max(0, DETXP - (this.getLVL() - h.getLVL()) * CXP);
            this.gainXP(gxp);
        }
    }

    abstract int newHP();

    void levelUp() {
        ++level;
        hp = newHP();
        maxxp = FXP + LXP * level;
        maxhp = hp;
    }

    void gainXP(final int gxp) {
        xp += gxp;
        while (xp >= maxxp) {
            levelUp();
        }
    }

    void makeM(final char direction) {
        if (direction == 'R') {
            col++;
        }
        if (direction == 'L') {
            col--;
        }
        if (direction == 'D') {
            line++;
        }
        if (direction == 'U') {
            line--;
        }
    }

    void movement(final char direction, final Map map) {
        if (stun) {
            --stunRounds;
            if (stunRounds == 0) {
                stun = false;
            }
        } else {
            makeM(direction);
        }
    }

    void takeDot(final Map map) {
        if (dot) {
            // System.out.println(this.race);
            // System.out.println(dotpRound);
            --dotRounds;
            if (dotRounds == 0) {
                dot = false;
            }
            hp -= dotpRound;
            if (hp <= 0) {
                dead = true;
            }
        }
    }

    public boolean checkDot(final int rounds) {
        if (dotpRound * Math.min(rounds, dotRounds) < hp) {
            return true;
        }
        return false;
    }

    public void plusStab() {
    }

    abstract int isAttacked(Hero h, Land l);

    abstract int dealDmg(Wizard w, Land l);

    abstract int dealDmg(Knight k, Land l);

    abstract int dealDmg(Rogue r, Land l);

    abstract int dealDmg(Pyromancer p, Land l);
}
