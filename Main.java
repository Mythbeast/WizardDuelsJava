public class Main {
  static void main() {
    GameLogic game1 = new GameLogic();
    game1.game(game1);
  }

  // TODO: Currently all attacks will always hit the oldest minion, even if they
  // could kill other ones
  // TODO: add minion sort so the most damaging minion dies first (if the attack
  // will kill it [similar to combination block code]), not the oldest one
  // TODO: add config file/option to change max actions per turn, max hp etc.
  // TODO: add computer players
  // TODO: campaign with different themed players (minion enemy, fire enemy etc
  // with different minion types and moves)
  // TODO: move player and move data to a database

  public static void main(String[] args) {
    main();
  }
}