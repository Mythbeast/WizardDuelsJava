import java.util.*;

// subclass of moves which do damage AND block
class AttackAndBlockMove  extends Move{
    private BlockElement blockelem;
    private AttackElement attackelem;

    // constructor method
    public AttackAndBlockMove(int damage, String damageType, int block, String blockType, EnumMap<ChargeType, Integer> charges, String effect) {
        this.charges.putAll(charges);
        blockelem = new BlockElement(block, blockType);
        attackelem = new AttackElement(damage, damageType);
        this.effect = effect;
    }

    // getters and setters
    public BlockElement getBlockElement(){
        return blockelem;
    }

    public AttackElement getAttackElement(){
        return attackelem;
    }

    // move execution to add an attack and a block to lists
    @Override
    public void executeMove(Player player) {
        ArrayList<BlockElement> blocks = player.getBlocks();
        blocks.add(this.blockelem);
        player.setBlocks(blocks);

        ArrayList<AttackElement> attacks = player.getAttacks();
        attacks.add(this.attackelem);
        player.setAttacks(attacks);
    }
    
}