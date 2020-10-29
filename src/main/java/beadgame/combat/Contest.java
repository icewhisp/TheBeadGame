package beadgame.combat;

import java.util.ArrayList;
import java.util.List;

import beadgame.actor.Actor;
import beadgame.bead.Bead;
import beadgame.pool.Pool;
import beadgame.util.Utility;

public class Contest {

  public static List<TestResult> closeAttack(Actor actor, Actor target, Pool beads) {
    int force = beads.count(Bead.black);
    int intellect = beads.count(Bead.blue);

    List<TestResult> results = new ArrayList<>();
    for (int i = 0; i < force + 1; i++) {
      results.add(generalTest(intellect));
    }
    return results;
  }

  private static TestResult generalTest(int modifier) {
    int DC = 5;
    int critDC = 6;

    int roll = Utility.d6(modifier) + modifier;
    if (roll < DC) {
      return TestResult.fail;
    } else {
      if (roll >= critDC && Utility.d6(modifier) >= DC) {
        return TestResult.critical;
      } else {
        return TestResult.success;
      }
    }
  }
}
