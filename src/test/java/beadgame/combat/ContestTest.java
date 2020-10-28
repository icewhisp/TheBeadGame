package beadgame.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import beadgame.bead.Bead;
import beadgame.pool.Pool;
import beadgame.pool.PoolType;

class ContestTest {

  public static final int REPLICATIONS = 100000;

  @Test
  void simple_odds_green() {
    double[] hits = countSuccesses(Bead.green);
    assertEquals(0.5, hits[0] / REPLICATIONS, 0.05);
    assertEquals(0.5 / 6, hits[1] / REPLICATIONS, 0.05);
  }

  @Test
  void simple_odds_black() {
    double[] hits = countSuccesses(Bead.black);
    assertEquals(1.0, hits[0] / REPLICATIONS, 0.05);
    assertEquals(1.0 / 6, hits[1] / REPLICATIONS, 0.05);
  }

  @Test
  void simple_odds_blue() {
    double[] hits = countSuccesses(Bead.blue);
    assertEquals(4 / 6.0, hits[0] / REPLICATIONS, 0.05);
    assertEquals(4 / 6.0 / 6, hits[1] / REPLICATIONS, 0.05);
  }

  @Test
  void odds_blue_and_black() {
    double[] hits = countSuccesses(Bead.blue, Bead.black);
    assertEquals(2 * 4 / 6.0, hits[0] / REPLICATIONS, 0.05);
    assertEquals(2 * 4 / 6.0 / 6, hits[1] / REPLICATIONS, 0.05);
  }

  private double[] countSuccesses(Bead... beads) {
    Pool pool = new Pool(PoolType.ACTION).addAll(beads);
    double[] hits = new double[2];
    for (int i = 0; i < REPLICATIONS; i++) {
      List<TestResult> results = Contest.closeAttack(null, null, pool);
      hits[0] += results.stream().filter(r -> r != TestResult.fail).count();
      hits[1] += results.stream().filter(r -> r == TestResult.critical).count();
    }
    return hits;
  }
}