public class Creature {
    private int creatureHP;
    private String name;
    private AttackElement attackelem;
    
    // constructor method
    public Creature(int creatureHP, String name, int damage, String attackType) {
        this.attackelem = new AttackElement(damage, attackType);
        this.creatureHP = creatureHP;
        this.name = name;
        }

    // getters and setters
    public AttackElement getAttackElement(){
        return attackelem;
    }

    public int getCreatureHP(){
        return creatureHP;
    }

    public void setCreatureHP(int n) {
        this.creatureHP = n;
    }

    public String getCreatureName(){
        return name;
    }
}
