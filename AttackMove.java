import java.util.*;
// subclass of moves which do damage
class AttackMove extends Move{
    private AttackElement attackelem;

    // constructor method
    public AttackMove(int damage, String type, EnumMap<ChargeType, Integer> charges, String effect) {
        this.charges.putAll(charges);
        attackelem = new AttackElement(damage, type);
        this.effect = effect;
    }

    // getters and setters
    public AttackElement getAttackElement(){
        return attackelem;
    }

    // move execution to add attack to attack list
    @Override
    public void executeMove(Player player) {
        ArrayList<AttackElement> attacks = player.getAttacks();
        attacks.add(attackelem);
        player.setAttacks(attacks);
    }
}
