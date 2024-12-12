import java.util.*;

// subclass of moves which do blocking
class BlockMove extends Move{
    private BlockElement blockelem;

    // constructor method
    public BlockMove(int block, String type, EnumMap<ChargeType, Integer> charges, String effect) {
        this.charges.putAll(charges);
        blockelem = new BlockElement(block, type);
        this.effect = effect;
    }

    // getters and setters
    public BlockElement getBlockElement(){
        return blockelem;
    }
    
    // move execution to add block element 
    @Override
    public void executeMove(Player player) {
        ArrayList<BlockElement> blocks = player.getBlocks();
        blocks.add(blockelem);
        player.setBlocks(blocks);
    }

}