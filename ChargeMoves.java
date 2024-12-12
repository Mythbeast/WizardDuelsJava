import java.util.*;
// subclass of moves which gain charges
class ChargeMove extends Move {
    private Move.ChargeType element;

    // constructor method
    public ChargeMove(ChargeType typeToCharge, String effect) {
        this.element = typeToCharge;
        this.effect = effect;
    }

    // move execution to add charge to tempCharges
    @Override
    public void executeMove(Player player) {
        EnumMap<Move.ChargeType, Integer> tempCharges = player.getTempCharges();
        tempCharges.put(element, tempCharges.get(element) + 1);
        player.setTempCharges(tempCharges);
    }   


}

