import java.util.*;

public class GameLogic {
  private ArrayList<Move> moves = new ArrayList<Move>();
  // used to check game is still going and players haven't died
  private boolean isGameRunning = true;
  // used for round intro
  private int roundNumber = 1;
  private String separator = new String(new char[20]).replace("\0", "-");
  // create players
  public Player player1 = new Player();
  public Player player2 = new Player();

  public GameLogic() {

    // define cost for each move
    EnumMap<Move.ChargeType, Integer> fireStrikeCost = new EnumMap<>(Move.ChargeType.class);
    fireStrikeCost.put(Move.ChargeType.FIRE, -1);

    EnumMap<Move.ChargeType, Integer> gustCost = new EnumMap<>(Move.ChargeType.class);
    gustCost.put(Move.ChargeType.AIR, -1);

    EnumMap<Move.ChargeType, Integer> counterCost = new EnumMap<>(Move.ChargeType.class);
    counterCost.put(Move.ChargeType.WATER, -1);

    EnumMap<Move.ChargeType, Integer> drainCost = new EnumMap<>(Move.ChargeType.class);
    drainCost.put(Move.ChargeType.DARK, -1);

    EnumMap<Move.ChargeType, Integer> earthBlockCost = new EnumMap<>(Move.ChargeType.class);
    earthBlockCost.put(Move.ChargeType.EARTH, -1);

    EnumMap<Move.ChargeType, Integer> fireballCost = new EnumMap<>(Move.ChargeType.class);
    fireballCost.put(Move.ChargeType.FIRE, -1);
    fireballCost.put(Move.ChargeType.AIR, -1);

    EnumMap<Move.ChargeType, Integer> enhancedCounterCost = new EnumMap<>(Move.ChargeType.class);
    enhancedCounterCost.put(Move.ChargeType.AIR, -1);
    enhancedCounterCost.put(Move.ChargeType.WATER, -1);

    EnumMap<Move.ChargeType, Integer> unblockableAttackCost = new EnumMap<>(Move.ChargeType.class);
    unblockableAttackCost.put(Move.ChargeType.WATER, -1);
    unblockableAttackCost.put(Move.ChargeType.DARK, -1);

    EnumMap<Move.ChargeType, Integer> summonGolemCost = new EnumMap<>(Move.ChargeType.class);
    summonGolemCost.put(Move.ChargeType.EARTH, -1);
    summonGolemCost.put(Move.ChargeType.DARK, -1);

    EnumMap<Move.ChargeType, Integer> magmaStrikeCost = new EnumMap<>(Move.ChargeType.class);
    magmaStrikeCost.put(Move.ChargeType.FIRE, -1);
    magmaStrikeCost.put(Move.ChargeType.EARTH, -1);

    EnumMap<Move.ChargeType, Integer> noCost = new EnumMap<>(Move.ChargeType.class);

    // add moves to ArrayList to allow GameLogic to identify them from inputs
    moves.add(new ChargeMove(Move.ChargeType.FIRE, "You gained a fire charge!"));
    moves.add(new ChargeMove(Move.ChargeType.AIR, "You gained an air charge!"));
    moves.add(new ChargeMove(Move.ChargeType.WATER, "You gained a water charge!"));
    moves.add(new ChargeMove(Move.ChargeType.DARK, "You gained a dark charge!"));
    moves.add(new ChargeMove(Move.ChargeType.EARTH, "You gained an earth charge!"));
    moves.add(new AttackMove(2, "strike", fireStrikeCost, "You attempted to attack the enemy!"));
    moves.add(new AttackMove(2, "sling", gustCost, "You attempted to attack the enemy!"));
    moves.add(new CounterMove(2, "normalCounter", counterCost, "You attempted to counter!"));
    moves.add(new Drain(drainCost, "You drained the enemy of all charges!"));
    moves.add(new BlockMove(2, "normalBlock", earthBlockCost, "You attempted to block!"));
    moves.add(new AttackMove(3, "sling", fireballCost, "You attempted to attack the enemy!"));
    moves.add(new CounterMove(3, "normalCounter", enhancedCounterCost, "You attempted to counter!"));
    moves.add(
        new AttackMove(1, "unblockable", unblockableAttackCost, "You struck the enemy with an unblockable attack!"));
    moves.add(new SummonMove(1, "golem", 1, "attack", summonGolemCost, "You summoned a golem!"));
    moves.add(new AttackAndBlockMove(2, "strike", 1, "normalBlock", magmaStrikeCost,
        "You attempted to attack the enemy and block!"));
    moves.add(new Empower(noCost, "you empowered your attack!"));
    moves.add(new TypeBlockMove("strikeBlock", noCost, "You blocked all strike attacks!"));
    moves.add(new TypeBlockMove("slingBlock", noCost, "You blocked all sling attacks!"));
    moves.add(new BlockMove(1, "normalBlock", noCost, "You attempted to block!"));
    moves.add(new AttackMove(1, "normalAttack", noCost, "You attempted to attack the enemy!"));

    // null move for magic arrow 2nd hand:
    moves.add(new AttackMove(0, "normalAttack", noCost, null));
  }

  // method to change isGameRunning for when a player dies
  private void setIsGameRunning(boolean isRunning) {
    this.isGameRunning = isRunning;
  }

  // method to pass the cost of a move
  public EnumMap<Move.ChargeType, Integer> getMoveCost(int n) {
    return moves.get(n - 1).charges;
  }

  // method to input player names
  private void getPlayerNames(Scanner scanner) {
    System.out.println("Player 1, enter your name:");
    player1.setName(scanner.nextLine());
    System.out.println("Player 2, enter your name:");
    player2.setName(scanner.nextLine());
  }

  // method to execute all moves and update attack, block, counter lists etc.
  private void executeAllMoves(Player player1, Player player2, ArrayList<Integer> actions1,
      ArrayList<Integer> actions2) {
    for (int input : actions1) {
      Move move = moves.get(input);
      move.executeMove(player1);
    }

    for (int input : actions2) {
      Move move = moves.get(input);
      move.executeMove(player2);
    }
  }

  // method to add empower damage for both players
  private void empowerCheck() {
    player1.addEmpower();
    player2.addEmpower();
  }

  // method to add minion attacks into attack list prior to counters/blocks
  private void addMinionAttacks(Player player) {
    ArrayList<AttackElement> attacks = player.getAttacks();
    ArrayList<Creature> minions = player.getMinions();
    // for each alive minion, add the attack
    for (Creature x : minions) {
      attacks.add(x.getAttackElement());
    }
    player.setAttacks(attacks);
  }

  // method to sort attacks by damage in descending order, with typeblocked
  // attacks at the end of the array list
  private ArrayList<AttackElement> sortAttacks(ArrayList<AttackElement> attacks, boolean[] typeBlocks) {
    // sort by damage order
    Collections.sort(attacks);
    ArrayList<AttackElement> tempAttacks = new ArrayList<AttackElement>();
    // move strike attacks to tempAttacks
    if (typeBlocks[0]) {
      for (int i = 0; i < attacks.size(); i++) {
        if (attacks.get(i).getType() != "strike") {
          tempAttacks.add(attacks.get(i));
          attacks.remove(i);
        }
      }
    }

    // move sling attacks to tempAttacks
    if (typeBlocks[1]) {
      for (int i = 0; i < attacks.size(); i++) {
        if (attacks.get(i).getType() != "sling") {
          tempAttacks.add(attacks.get(i));
          attacks.remove(i);
        }
      }
    }

    // unblockable attacks moved to tempAttacks last, since these can never be
    // blocked or countered
    for (int i = 0; i < attacks.size(); i++) {
      if (attacks.get(i).getType() != "unblockable") {
        tempAttacks.add(attacks.get(i));
        attacks.remove(i);
      }
    }
    // update attack lists so that typeblocked attack and unblockable attacks are at
    // the end
    tempAttacks.addAll(attacks);
    attacks = tempAttacks;

    return attacks;
  }

  // sort counters by counter value
  private ArrayList<CounterElement> sortCounters(ArrayList<CounterElement> counters) {
    Collections.sort(counters);
    return counters;
  }

  // sort blocks by block value
  private ArrayList<BlockElement> sortBlocks(ArrayList<BlockElement> blocks) {
    Collections.sort(blocks);
    return blocks;
  }

  // apply sorting methods to all move lists
  private void sortAllMoves(Player player) {
    player.setAttacks(sortAttacks(player.getAttacks(), player.getTypeBlocks()));
    player.setCounters(sortCounters(player.getCounters()));
    player.setBlocks(sortBlocks(player.getBlocks()));
  }

  // perform all one player's counters for the opposing player's attacks
  private ArrayList<AttackElement> allCounters(ArrayList<AttackElement> attacks, ArrayList<CounterElement> counters,
      Player attackingPlayer) {

    ArrayList<AttackElement> counterAttacks = new ArrayList<AttackElement>();
    ArrayList<AttackElement> unblockedAttacks = new ArrayList<AttackElement>();
    boolean isCountering = false;

    // if there are attacks and counters, begin countering
    if (counters.size() > 0) {
      if (attacks.size() > 0) {
        isCountering = true;
      }
    }

    // if statement used to ensure attacks are only set to unblocked attacks if
    // countering occurred
    if (isCountering) {
      while (isCountering == true) {
        // if attack is countered, add it to counterattacks and remove from attacks and
        // counters
        if (counters.get(0).counterCheck(attacks.get(0))) {
          counterAttacks.add(attacks.get(0));
          attacks.remove(0);
          counters.remove(0);
        } else {
          // if attack is not countered, add to unblocked attacks and remove from counter
          // considerations
          unblockedAttacks.add(attacks.get(0));
          attacks.remove(0);
        }
        // if there are no more coutners or no more attacks, stop countering
        if (attacks.size() == 0 || counters.size() == 0) {
          isCountering = false;
          unblockedAttacks.addAll(attacks);
        }
      }
      // set attacks to the unblockedAttacks that were not countered
      attackingPlayer.setAttacks(unblockedAttacks);
    }

    return counterAttacks;
  }

  // method for all counters for both players
  private void counterCalculations() {

    // check for player1's counters
    ArrayList<AttackElement> counterAttacks1 = allCounters(player2.getAttacks(), player1.getCounters(), player2);
    // // check for player2's counters
    ArrayList<AttackElement> counterAttacks2 = allCounters(player1.getAttacks(), player2.getCounters(), player1);

    // add counter attacks to player attacks before minion and block calculations
    player1.addCounterAttacks(counterAttacks1);
    player2.addCounterAttacks(counterAttacks2);
  }

  // check for damage / death to minions for one player
  private void minionDeathCheck(ArrayList<AttackElement> attacks1, ArrayList<Creature> minions2, Player attacker,
      Player minionmancer) {
    boolean minionCombat = true;

    // check to see if minion combat is necessary
    if (attacks1.size() == 0 || minions2.size() == 0) {
      minionCombat = false;
    }

    // if there are minions and attacks remaining, go to minion combat
    while (minionCombat) {
      // each instance deals with the damage for a single attack and we keep going
      // until all minions dead or all attacks dealt with
      minionCombat(attacks1, minions2, attacker, minionmancer);
      if (attacks1.size() == 0 || minions2.size() == 0) {
        minionCombat = false;
      }
    }
  }

  // method to calculate damage to minions and remove from their HP for a single
  // attack
  private void minionCombat(ArrayList<AttackElement> attacks1, ArrayList<Creature> minions2, Player player1,
      Player player2) {

    // remove damage of attack from minion HP (no negative HP)
    minions2.get(0).setCreatureHP(Math.max(0, minions2.get(0).getCreatureHP() - attacks1.get(0).getDamage()));
    attacks1.remove(0);
    if (minions2.get(0).getCreatureHP() == 0) {
      minions2.remove(0);
    }
    // update attack and minion lists after damage
    player1.setAttacks(attacks1);
    player2.setMinions(minions2);

  }

  // remove all attacks which are typeblocked
  public void removeTypeblockedAttacks(ArrayList<AttackElement> attacks1, boolean[] typeBlocks2, String type,
      int index) {
    // new array list used to prevent NoSuchElementExceptions from changing a list
    // mid loop
    ArrayList<AttackElement> attackCopy = new ArrayList<AttackElement>();
    attackCopy.addAll(attacks1);
    if (typeBlocks2[index]) {
      if (attacks1.size() > 0) {
        for (AttackElement x : attackCopy) {
          if (x.getType() == type) {
            attacks1.remove(x);
          }
        }
      }

    }
  }

  // method to calculate all blocks for attacks1.get(index)
  // method iterated over all indices to calculate blocks for each attack
  public void blockAttackIndex(ArrayList<AttackElement> attacks1, ArrayList<BlockElement> blocks2, int index) {

    // plusblock is used to add up block value of blocks which weren't sufficient to
    // block an attack on their own
    int plusBlock = 0;
    boolean isBlocking = false;

    // only go to blocking if necessary
    if (attacks1.size() > 0) {
      if (blocks2.size() > 0) {
        isBlocking = true;
      }
    }

    // copies used to prevent NoSuchElementExceptions from changing a list mid loop
    ArrayList<AttackElement> attackCopy = new ArrayList<AttackElement>();
    attackCopy.addAll(attacks1);
    ArrayList<BlockElement> blockCopy = new ArrayList<BlockElement>();
    blockCopy.addAll(blocks2);

    while (isBlocking) {
      // if block is successful, reset plusblock and remove attack and blocks (could
      // be multiple blocks due to plusBlock)
      if (blockCopy.get(0).blockCheck(attackCopy.get(index), plusBlock)) {
        plusBlock = 0;
        attackCopy.remove(index);
        blockCopy.remove(0);

        // update attacks and blocks after successful block
        attacks1.remove(index);
        blocks2.removeAll(blocks2);
        blocks2.addAll(blockCopy);
      } else {
        // add block value to plusBlock and remove from blockCopy to check whether a
        // combination of blocks can block the attack
        plusBlock = plusBlock + blockCopy.get(0).getBlock();
        blockCopy.remove(0);
      }

      // if we are done blocking, stop
      if (blockCopy.size() == 0 || attackCopy.size() == 0) {
        isBlocking = false;
        plusBlock = 0;
      }
    }
  }

  // method to calculate all blocking, checking over each index of attacks1
  public void removeBlockedAttacks(ArrayList<AttackElement> attacks1, ArrayList<BlockElement> blocks2) {

    boolean isBlocking = true;
    while (isBlocking == true) {
      int i = attacks1.size();
      int k = 0;
      while (k < i) {
        blockAttackIndex(attacks1, blocks2, k);
        i = attacks1.size();
        k = k + 1;
      }
      // if we have checked all indices of attacks1, stop
      isBlocking = false;
    }
  }

  // method to manage all blocking, including damage blocking and typeblocking
  private void blockCalculations(ArrayList<AttackElement> attacks1, ArrayList<BlockElement> blocks2,
      boolean[] typeBlocks2) {

    // start by removing all typeblocked attacks
    removeTypeblockedAttacks(attacks1, typeBlocks2, "strike", 0);
    removeTypeblockedAttacks(attacks1, typeBlocks2, "sling", 1);

    // remove all other blocked attacks
    removeBlockedAttacks(attacks1, blocks2);
  }

  // add unblockable damage to the damage of remaining attacks
  private int finalDamage(ArrayList<AttackElement> attacks, int damage) {
    for (AttackElement x : attacks) {
      damage = damage + x.getDamage();
    }
    return damage;
  }

  // get attacks, minions, blocks and counters and calculate damage
  private int[] calculateDamage(Player player1, Player player2) {

    // add damage if players have empowered
    empowerCheck();

    // add minion attacks to attack lists
    addMinionAttacks(player1);
    addMinionAttacks(player2);

    // sort attacks so that typeblocked attacks are not prioritised for counters
    sortAllMoves(player1);
    sortAllMoves(player2);

    // perform all counters and update attack lists with countered attacks
    counterCalculations();

    // sort attacks again to order them with the new counterattacks
    player1.setAttacks(sortAttacks(player1.getAttacks(), player2.getTypeBlocks()));
    player2.setAttacks(sortAttacks(player2.getAttacks(), player1.getTypeBlocks()));

    // check for minion deaths as minions can't block attacks and will bodyblock for
    // player
    minionDeathCheck(player1.getAttacks(), player2.getMinions(), player1, player2);
    minionDeathCheck(player2.getAttacks(), player1.getMinions(), player2, player1);

    // remove unblockable attacks and add to player damage now that minions have
    // been killed
    // damage1 is the damage dealt by player 1
    int damage1 = player1.unblockableDamageCalculations();
    int damage2 = player2.unblockableDamageCalculations();

    // check for blocks
    blockCalculations(player1.getAttacks(), player2.getBlocks(), player2.getTypeBlocks());
    blockCalculations(player2.getAttacks(), player1.getBlocks(), player1.getTypeBlocks());

    // calculate final damage
    damage1 = finalDamage(player1.getAttacks(), damage1);
    damage2 = finalDamage(player2.getAttacks(), damage2);
    int[] damage = { damage1, damage2 };

    return damage;
  }

  // check whether a player's hp has reached zero and announce the game's result
  private void checkForPlayerDeaths(Player player1, Player player2) {
    if (player1.getHP() == 0 && player2.getHP() == 0) {
      System.out.println("Both players have been defeated, the game is a draw!");
      setIsGameRunning(false);
    }

    if (player1.getHP() == 0) {
      System.out.println(player1.getName() + " is defeated, Player 2 wins!");
      setIsGameRunning(false);
    }

    if (player2.getHP() == 0) {
      System.out.println(player2.getName() + " is defeated, Player 1 wins!");
      setIsGameRunning(false);
    }
  }

  // method to check for drain attacks for each player
  private void drainCheck() {
    if (player1.getDrain()) {
      player2.isDrained();
    }

    if (player2.getDrain()) {
      player1.isDrained();
    }
  }

  // raise new minions
  private void minionUpdate() {
    player1.raiseMinions();
    player2.raiseMinions();
  }

  // reset player array lists and boolean values
  private void moveUpdate() {
    player1.updateMoves();
    player2.updateMoves();
  }

  // print out HP and minions ready for next round
  private void printCurrentPlayerStats() {
    player1.printCharges();
    player2.printCharges();
    player1.printMinions();
    player2.printMinions();
  }

  // method to perform end of round updates
  private void prepareForNextRound() {
    if (isGameRunning == true) {
      // update charges
      player1.chargeUpdate();
      player2.chargeUpdate();

      drainCheck();
      minionUpdate();
      moveUpdate();
      printCurrentPlayerStats();
    }
  }

  // turn mechanics
  public void turn(Player player1, Player player2, GameLogic game, Scanner scanner) {

    // get action inputs for players
    System.out.println(player1.getName() + ":");
    ArrayList<Integer> actions1 = player1.getPlayerActions(game, scanner, moves);
    System.out.println("\n" + player2.getName() + ":");
    ArrayList<Integer> actions2 = player2.getPlayerActions(game, scanner, moves);

    // execute moves for players
    executeAllMoves(player1, player2, actions1, actions2);

    // calculate all damage, blocks, counters etc
    int[] damage = calculateDamage(player1, player2);

    // update health
    player1.removeHP(damage[1]);
    player2.removeHP(damage[0]);

    // print damage taken from the round
    System.out.println("\n" + player1.getName() + " took " + damage[1] + " damage and has "
        + Math.max(player1.getHP(), 0) + " health remaining.");
    System.out.println(player2.getName() + " took " + damage[0] + " damage and has " + Math.max(player2.getHP(), 0)
        + " health remaining.");

    // check whether game should continue before preparing for the next turn
    checkForPlayerDeaths(player1, player2);
    prepareForNextRound();
  }

  // method to print round intro
  private void printRoundIntro() {
    System.out.println(separator);
    System.out.println("Round " + roundNumber + ":");
    System.out.println(separator);
  }

  // method to execute a game
  public void game(GameLogic game) {
    // scanner used to input player names and actions
    Scanner scanner = new Scanner(System.in);
    getPlayerNames(scanner);

    while (isGameRunning == true) {
      printRoundIntro();
      turn(player1, player2, game, scanner);
      roundNumber++;
    }
    scanner.close();

  }

}
