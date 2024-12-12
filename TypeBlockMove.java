import java.util.*;

// subclass of moves which block all moves of a certain type
class TypeBlockMove extends Move {
  private String type;

  // constructor method
  public TypeBlockMove(String type, EnumMap<ChargeType, Integer> charges, String effect) {
    this.charges.putAll(charges);
    this.type = type;
    this.effect = effect;
  }

  // getters and setters
  public String getTypeBlock() {
    return type;
  }

  // move execution to change player boolean value
  @Override
  public void executeMove(Player player) {
    boolean[] typeBlocks = player.getTypeBlocks();
    if (type == "strikeBlock") {
      typeBlocks[0] = true;
    }
    if (type == "slingBlock") {
      typeBlocks[1] = true;
    }

    player.setTypeBlocks(typeBlocks);
  }
}