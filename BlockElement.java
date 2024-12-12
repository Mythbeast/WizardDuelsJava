public class BlockElement implements Comparable<BlockElement>{
    int block = 0;
    String type = null;
    
    // constructor method
    public BlockElement(int block, String type) {
        this.block = block;
        this.type  = type;
    }

    // getters and setters
    public int getBlock() {
        return block;
    }

    // check whether this block element can block an attack (utilising plusBlock from other blocks)
    public boolean blockCheck(AttackElement attackElement, int plusBlock) {
        if (this.block + plusBlock >= attackElement.getDamage()){
            return true;
        } 
        else {
            return false;
        }
    }

    // mechanic to sort in descending block order
    @Override
    public int compareTo(BlockElement other) {
        int x = 0;
        if (other.block > this.block) {
            x = 1;
        }
        if (other.block == this.block) {
            x = 0;
        }
        if (other.block < this.block) { 
            x = -1;
        }
        return x;
    }


}
