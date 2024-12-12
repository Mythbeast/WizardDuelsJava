import java.util.*;

class Empower extends Move {

    // constructor method
    public Empower(EnumMap<ChargeType, Integer> charges, String effect) {
        this.effect = effect;
    }

    // move execution to change boolean for player
    @Override
    public void executeMove(Player player) {
        player.setEmpower(true);
    }
}