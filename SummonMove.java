import java.util.*;
// subclass of moves which summon creatures
class SummonMove extends Move{
    // values stored in the move so that new creatures summoned by the move can have different stats (hp) etc.
    private Creature creature;
    private int creatureHP;
    private int damage;
    private String name;
    private String attackType;

    // constructor method
    public SummonMove(int creatureHP, String name, int damage, String attackType, EnumMap<ChargeType, Integer> charges, String effect) {
        this.charges.putAll(charges);
        this.creatureHP = creatureHP;
        this.name = name;
        this.damage = damage;
        this.attackType = attackType;
        this.effect = effect;               
    }

    // move execution to add a copy of the creature to a player's future minions
    @Override
    public void executeMove(Player player) {
        if (player.getMinions().size() + player.getFutureMinions().size() < player.getMaxMinions()) {
            creature = new Creature(creatureHP, name, damage, attackType);
            player.addMinionToBeSummmoned(creature);
        }
    }

}
