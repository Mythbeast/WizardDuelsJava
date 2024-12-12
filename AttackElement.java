public class AttackElement implements Comparable<AttackElement>{
    private int damage;
    private String type;
    
    // constructor method
    public AttackElement(int damage, String type) {
        this.damage = damage;
        this.type  = type;
    }

    // getters and setters
    public int getDamage() {
        return damage;
    }

    public String getType() {
        return type;
    }

    // mechanics to sort by descending damage order 
    @Override
    public int compareTo(AttackElement other) {
        int x = 0;
        if (other.damage > this.damage) {
            x = 1;
        }
        if (other.damage == this.damage) {
            x = 0;
        }
        if (other.damage < this.damage) { 
            x = -1;
        }
        return x;
    }
}
