import java.util.EnumMap;

// main move class used for all moves
public class Move {
    // charge types for reference
    public enum ChargeType {FIRE, AIR, WATER, DARK, EARTH}
    // cost of move
    protected EnumMap<ChargeType, Integer> charges = new EnumMap <>(ChargeType.class);
    // text to print on move choice
    protected String effect;

    // constructor method
    // default cost is 0 for all to allow for less code when writing costs
    Move(){
        charges.put(ChargeType.FIRE, 0);
        charges.put(ChargeType.AIR, 0);
        charges.put(ChargeType.WATER, 0);
        charges.put(ChargeType.DARK, 0);
        charges.put(ChargeType.EARTH, 0);
    }

    // getters and setters
    public String getEffect() {
        return this.effect;
    }

    // method to be overwritten by all moves
    public void executeMove(Player player) {
    }   
}
    

