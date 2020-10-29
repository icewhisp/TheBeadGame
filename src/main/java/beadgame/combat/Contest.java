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
      int dc = 4 - intellect;
      results.add(generalTest(dc));
    }
    return results;
  }

  private static TestResult generalTest(int difficulty) {
    int roll = Utility.d6();
//    System.out.println("roll = " + roll + " vs. dc = " + difficulty);
    if (roll < difficulty) {
      return TestResult.fail;
    } else if (roll == 6 && Utility.d6() >= difficulty) {
      return TestResult.critical;
    } else {
      return TestResult.success;
    }
  }
}
