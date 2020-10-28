package beadgame.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import beadgame.bead.Bead;
import beadgame.pool.Pool;

public abstract class Strategy {

  public static Strategy preferMultipleDifferent(int i) {
    return new RandomStrategy(i);
  }

  private final String name;

  public Strategy(String name) {
    this.name = name;
  }

  public String name() {
    return name;
  }

  public abstract List<Bead> chooseBeads(Pool available, List<Actor> opponents);

  public abstract boolean possibleFor(Actor actor);

  /**
   * RThis stragety simply picks up to a given number of beads at random
   */
  private static class RandomStrategy extends Strategy {

    private final int beadsToUse;

    public RandomStrategy(int beadsToUse) {
      super("Multi-" + beadsToUse);
      this.beadsToUse = beadsToUse;
    }

    @Override
    public boolean possibleFor(Actor actor) {
      // Does he have enough beads?
      return actor.totalStrength() >= beadsToUse;
    }

    @Override
    public List<Bead> chooseBeads(Pool pool, List<Actor> opponents) {
      List<Bead> available = pool.beads().collect(Collectors.toList());
      List<Bead> result = new ArrayList<>();

      // Add one of each if possible
      for (Bead bead : Bead.ALL_BEADS) {
        if (result.size() < beadsToUse && available.contains(bead)) {
          if (available.remove(bead)) {
            result.add(bead);
          }
        }
      }

      // Fill up the extra places
      while (result.size() < beadsToUse && !available.isEmpty()) {
        result.add(available.remove(0));
      }

      return result;

    }
  }


}
