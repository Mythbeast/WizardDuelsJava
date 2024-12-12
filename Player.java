import java.util.EnumMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Player {

  // default player values
  private String name;
  private int HP = 10;
  private int maxMinions = 1;
  private int maxActions = 2;

  // null typeblocks for gameplay
  private boolean strikeBlock = false;
  private boolean slingBlock = false;
  private boolean[] typeBlocks = { strikeBlock, slingBlock };

  // drain and empower state for gameplay
  private boolean empower = false;
  private boolean drain = false;

  // empty array lists for gameplay
  private ArrayList<Creature> currentMinions = new ArrayList<Creature>();
  private ArrayList<Creature> futureMinions = new ArrayList<Creature>();
  private ArrayList<AttackElement> attacks = new ArrayList<AttackElement>();
  private ArrayList<BlockElement> blocks = new ArrayList<BlockElement>();
  private ArrayList<CounterElement> counters = new ArrayList<CounterElement>();

  // empty EnumMap of charges for gameplay
  private EnumMap<Move.ChargeType, Integer> charges = new EnumMap<>(Move.ChargeType.class);
  private EnumMap<Move.ChargeType, Integer> tempCharges = new EnumMap<>(Move.ChargeType.class);
  private EnumMap<Move.ChargeType, Integer> costOfMoves = new EnumMap<>(Move.ChargeType.class);

  Player() {
    // add starting charges to player
    charges.put(Move.ChargeType.FIRE, 0);
    charges.put(Move.ChargeType.AIR, 0);
    charges.put(Move.ChargeType.WATER, 0);
    charges.put(Move.ChargeType.DARK, 0);
    charges.put(Move.ChargeType.EARTH, 0);

    // tempCharges is used to add charges using ChargeMoves
    tempCharges.put(Move.ChargeType.FIRE, 0);
    tempCharges.put(Move.ChargeType.AIR, 0);
    tempCharges.put(Move.ChargeType.WATER, 0);
    tempCharges.put(Move.ChargeType.DARK, 0);
    tempCharges.put(Move.ChargeType.EARTH, 0);

    // cost of all player moves used each round
    costOfMoves.put(Move.ChargeType.FIRE, 0);
    costOfMoves.put(Move.ChargeType.AIR, 0);
    costOfMoves.put(Move.ChargeType.WATER, 0);
    costOfMoves.put(Move.ChargeType.DARK, 0);
    costOfMoves.put(Move.ChargeType.EARTH, 0);
  }

  /*
   * Getters and Setters
   */
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getMaxActions() {
    return this.maxActions;
  }

  public boolean[] getTypeBlocks() {
    return this.typeBlocks;
  }

  public void setTypeBlocks(boolean[] typeBlocks) {
    this.typeBlocks = typeBlocks;
  }

  public ArrayList<AttackElement> getAttacks() {
    return this.attacks;
  }

  public void setAttacks(ArrayList<AttackElement> attacks) {
    this.attacks = attacks;
  }

  public ArrayList<BlockElement> getBlocks() {
    return this.blocks;
  }

  public void setBlocks(ArrayList<BlockElement> blocks) {
    this.blocks = blocks;
  }

  public ArrayList<CounterElement> getCounters() {
    return this.counters;
  }

  public void setCounters(ArrayList<CounterElement> counters) {
    this.counters = counters;
  }

  public ArrayList<Creature> getMinions() {
    return this.currentMinions;
  }

  public void setMinions(ArrayList<Creature> minions) {
    this.currentMinions = minions;
  }

  public ArrayList<Creature> getFutureMinions() {
    return this.futureMinions;
  }

  public void setFutureMinions(ArrayList<Creature> minions) {
    this.futureMinions = minions;
  }

  public int getMaxMinions() {
    return this.maxMinions;
  }

  public EnumMap<Move.ChargeType, Integer> getCharges() {
    return this.charges;
  }

  public void setCharges(EnumMap<Move.ChargeType, Integer> charges) {
    this.charges = charges;
  }

  public EnumMap<Move.ChargeType, Integer> getTempCharges() {
    return this.tempCharges;
  }

  public void setTempCharges(EnumMap<Move.ChargeType, Integer> charges) {
    this.tempCharges = charges;
  }

  public boolean getEmpower() {
    return this.empower;
  }

  public void setEmpower(boolean empower) {
    this.empower = empower;
  }

  public boolean getDrain() {
    return this.drain;
  }

  public void setDrain(boolean drain) {
    this.drain = drain;
  }

  public int getHP() {
    return this.HP;
  }

  // method to remove HP after each round and never go negative
  public void removeHP(int damage) {
    this.HP = Math.max(HP - damage, 0);
  }

  // method called by SummonMoves to add a minion which only gets added at the end
  // of the round
  public void addMinionToBeSummmoned(Creature minion) {
    this.futureMinions.add(minion);
  }

  // method to actually add minions to gameplay
  public void raiseMinions() {
    this.currentMinions.addAll(this.futureMinions);
    this.futureMinions.removeAll(this.futureMinions);
  }

  // method to create a new empowered attack element if player has used an empower
  // move
  public void addEmpower() {
    if (this.empower && attacks.size() > 0) {
      AttackElement empoweredAttack = new AttackElement(attacks.get(0).getDamage() + 1, attacks.get(0).getType());
      this.attacks.set(0, empoweredAttack);
    }
  }

  // method to state player charges at the end of each round
  public void printCharges() {
    System.out.println(this.name + " has " + charges.get(Move.ChargeType.FIRE) + " fire charges, "
        + charges.get(Move.ChargeType.AIR) + " air charges, " + charges.get(Move.ChargeType.WATER) + " water charges, "
        + charges.get(Move.ChargeType.DARK) + " dark charges, " + charges.get(Move.ChargeType.EARTH)
        + " earth charges.");
  }

  // method to state player minions and remaining HP at the end of each round
  public void printMinions() {
    for (Creature x : currentMinions) {
      System.out.println(this.name + " has a " + x.getCreatureName() + " with " + x.getCreatureHP()
          + " health remaining, which attacks for " + x.getAttackElement().getDamage() + " "
          + x.getAttackElement().getType() + " damage.");
    }
  }

  // method to add elements of EnumMaps, used for charge calculations
  private EnumMap<Move.ChargeType, Integer> addEnumMaps(EnumMap<Move.ChargeType, Integer> charges1,
      EnumMap<Move.ChargeType, Integer> charges2) {
    EnumMap<Move.ChargeType, Integer> resultantEnumMap = new EnumMap<>(Move.ChargeType.class);
    for (Move.ChargeType x : Move.ChargeType.values()) {
      int result = charges1.get(x) + charges2.get(x);
      resultantEnumMap.put(x, result);
    }
    return resultantEnumMap;
  }

  // method to check whether player has sufficient charges to do a move
  private boolean costCheck(EnumMap<Move.ChargeType, Integer> charges, EnumMap<Move.ChargeType, Integer> cost) {
    for (Move.ChargeType x : Move.ChargeType.values()) {
      if (charges.get(x) + cost.get(x) < 0) {
        return false;
      }
    }
    return true;
  }

  // method to get and pass on all player actions for the round to game logic
  public ArrayList<Integer> getPlayerActions(GameLogic game, Scanner scanner, ArrayList<Move> moves) {
    // list to hold all actions for the turn
    ArrayList<Integer> actions = new ArrayList<Integer>();
    int i = 0;

    // receive inputs for all but the last action
    while (i <= maxActions - 2) {
      actions.add(getSingleAction(game, scanner, i) - 1);
      // output effect for each action chosen to inform players
      System.out.println(moves.get(actions.get(i)).getEffect());
      if (actions.get(i) == 19) {
        System.out.println("This move uses two hands");
        // add null move for second hand when magic arrow is used
        actions.add((i + 1), 20);
        // add to i again to skip next index
        i++;
      }
      i++;
    }

    // checking that final action is not the second hand of a magic arrow before
    // taking input
    if (i < maxActions) {
      int finalAction = getFinalInput(game, actions.get(maxActions - 2), scanner);
      actions.add(finalAction - 1);
      // checking penultimate move is not magic arrow
      if (finalAction != 21) {
        System.out.println(moves.get(finalAction - 1).getEffect());
      }
    }
    return actions;
  }

  // method to get input for all but the final hand
  private int getSingleAction(GameLogic game, Scanner scanner, int i) {
    // code to ensure that players are asked for 1st hand etc rather than 1th hand
    String suffix;
    switch (i + 1) {
      case 1:
        suffix = "st";
        break;
      case 2:
        suffix = "nd";
        break;
      case 3:
        suffix = "rd";
        break;
      default:
        suffix = "th";
        break;
    }

    // input validation
    try {
      System.out.println("\nPlease input an action for your " + (i + 1) + suffix
          + " hand: \nPlease enter the number: 1:charge fire, 2:charge air, 3:charge water, 4:charge dark, 5:charge earth, 6:fire strike, 7:gust, 8:counter, 9:drain, 10:earth block, 11:fireball, 12:enhanced counter 13:unblockable strike 14:summon golem, 15:magma strike 16:empower, 17:strike block. 18:sling block: 19:block or 20: magic arrow(2 hands).");
      int actionInput = scanner.nextInt();
      if (1 <= actionInput && actionInput <= 20) {
        // checking whether player has charges for the move before officially adding
        // charges to costOfMoves and returning a valid input
        EnumMap<Move.ChargeType, Integer> cost = game.getMoveCost(actionInput);
        if (costCheck(charges, addEnumMaps(costOfMoves, cost))) {
          costOfMoves = addEnumMaps(costOfMoves, cost);
          return actionInput;
        } else {
          System.out.println("You do not have enough charges for this move");
          return getSingleAction(game, scanner, i);
        }

      } else {
        // if action is outside of the range
        System.out.println("Please choose a valid input");
        return getSingleAction(game, scanner, i);
      }

    }
    // if action is not an integer
    catch (Exception e) {
      System.out.println("Please choose a valid input");
      return getSingleAction(game, scanner, i);
    }
  }

  private int getFinalInput(GameLogic game, int actionInput, Scanner scanner) {
    // checking whether previous action was magic arrow to assign null move
    if (actionInput == 19) {
      return 21;
    }

    // similar code to getSingleAction
    try {
      System.out.println(
          "\nPlease input an action for your final hand: \nPlease enter the number: 1:charge fire, 2:charge air, 3:charge water, 4:charge dark, 5:charge earth, 6:fire strike, 7:gust, 8:counter, 9:drain, 10:earth block, 11:fireball, 12:enhanced counter 13:unblockable strike 14:summon golem, 15:magma strike 16:empower, 17:strike block. 18:sling block: or 19:block.");
      int rightInput = scanner.nextInt();
      if (1 <= rightInput && rightInput <= 19) {
        EnumMap<Move.ChargeType, Integer> cost = game.getMoveCost(rightInput);
        if (costCheck(charges, addEnumMaps(costOfMoves, cost))) {
          costOfMoves = addEnumMaps(costOfMoves, cost);
          return rightInput;
        } else {
          System.out.println("You do not have enough charges for this move");
          return getFinalInput(game, actionInput, scanner);
        }
      } else {
        System.out.println("Please choose a valid input1");
        return getFinalInput(game, actionInput, scanner);
      }
    } catch (Exception e) {
      System.out.println("Please choose a valid input2");
      return getFinalInput(game, actionInput, scanner);
    }

  }

  // method to add countered attacks to attack list
  public void addCounterAttacks(ArrayList<AttackElement> counterAttacks) {
    this.attacks.addAll(counterAttacks);
  }

  // add up unblockable damage remaining after minion deaths
  // if attacks are unblockable, add damage, otherwise move to new list and then
  // update attacks after
  public int unblockableDamageCalculations() {
    int damage = 0;
    ArrayList<AttackElement> attackCopy = new ArrayList<AttackElement>();

    for (AttackElement x : attacks) {
      if (x.getType() == "unblockable") {
        damage = damage + x.getDamage();
      } else {
        attackCopy.add(x);
      }
    }
    this.attacks.removeAll(this.attacks);
    this.attacks.addAll(attackCopy);
    attackCopy.removeAll(attackCopy);
    return damage;
  }

  // method to update player charges at the end of the round and reset tempCharges
  // and costOfMoves
  public void chargeUpdate() {
    charges = addEnumMaps(tempCharges, charges);
    tempCharges.put(Move.ChargeType.FIRE, 0);
    tempCharges.put(Move.ChargeType.AIR, 0);
    tempCharges.put(Move.ChargeType.WATER, 0);
    tempCharges.put(Move.ChargeType.DARK, 0);
    tempCharges.put(Move.ChargeType.EARTH, 0);

    charges = addEnumMaps(costOfMoves, charges);
    costOfMoves.put(Move.ChargeType.FIRE, 0);
    costOfMoves.put(Move.ChargeType.AIR, 0);
    costOfMoves.put(Move.ChargeType.WATER, 0);
    costOfMoves.put(Move.ChargeType.DARK, 0);
    costOfMoves.put(Move.ChargeType.EARTH, 0);
  }

  // method to remove all charges if other player used a drain attack
  public void isDrained() {
    charges.put(Move.ChargeType.FIRE, 0);
    charges.put(Move.ChargeType.AIR, 0);
    charges.put(Move.ChargeType.WATER, 0);
    charges.put(Move.ChargeType.DARK, 0);
    charges.put(Move.ChargeType.EARTH, 0);
  }

  // method to empty array lists for next round and reset status of boolean values
  public void updateMoves() {
    attacks.removeAll(attacks);
    blocks.removeAll(blocks);
    counters.removeAll(counters);
    futureMinions.removeAll(futureMinions);
    this.drain = false;
    this.strikeBlock = false;
    this.slingBlock = false;
    this.empower = false;
  }

}
