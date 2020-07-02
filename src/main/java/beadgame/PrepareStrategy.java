package beadgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Thsi calss defines a stratgy for which beads to pick for actions in a prepare round
 */
public class PrepareStrategy {

  public final List<BeadColor[]> orderedPreferences;

  /**
   * This takes a string version of preferences in order. It's much more convenient to use than the
   * constructor
   * <p>
   * Example input: "F-I-S-A" -- single values in this order Example input: "SF-SI-SA-S-F-I-A" --
   * speed if possible in pairs
   *
   * @param preferenceDefinition dash separated list of bead combinations
   * @return the strategy
   *
   * TODO: Test all this
   */
  public static PrepareStrategy makeStrategy(String preferenceDefinition) {

    List<BeadColor[]> prefs = new ArrayList<>();

    String[] parts = preferenceDefinition.split("-");
    for (String bit : parts) {
      BeadColor[] choice = new BeadColor[bit.length()];
      for (int i = 0; i < choice.length; i++) {
        choice[i] = decodeCharacter(bit.charAt(i));
      }
      prefs.add(choice);
    }

    return new PrepareStrategy(prefs);
  }

  static BeadColor decodeCharacter(char c) {
    for (BeadColor bead : BeadColor.values()) {
      if (bead.shortName() == c) {
        return bead;
      }
    }
    throw new IllegalStateException("Unknown character without a matching bead: '" + c + "'");
  }


  private PrepareStrategy(List<BeadColor[]> orderedPreferences) {
    this.orderedPreferences = orderedPreferences;
  }


  /**
   * Make a prepare decision
   *
   * @param actor taregt to choose for
   * @return what to prepare
   */
  public List<BeadColor> choose(Actor actor) {
    List<BeadColor> available = actor.readyPool();

    for (BeadColor[] choice : orderedPreferences) {
      if (poolContainsBeads(available, choice)) {
        return Arrays.asList(choice);
      }
    }

    throw new IllegalStateException("Choices did not cover all possible options");
  }

  boolean poolContainsBeads(List<BeadColor> pool, BeadColor[] beads) {
    // we will destroy this list so we make a copy
    // if we can remove all beads from this copy we are goof to use this combo
    List<BeadColor> copy = new ArrayList<>(pool);
    for (BeadColor bead : beads) {
      boolean success = pool.remove(bead);
      if (!success) {
        return false;
      }
    }
    return true;
  }


}
