import java.util.*;

public class Drain extends Move {
    // constructor method
    public Drain(EnumMap<ChargeType, Integer> charges, String effect) {
        this.charges.putAll(charges);
        this.effect = effect;
    }

    // move execution to change drain boolean for player
    @Override
    public void executeMove(Player player) {
        player.setDrain(true);
    }       
}
