package beadgame.combat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import beadgame.actor.Actor;
import beadgame.bead.Bead;
import beadgame.pool.Pool;
import beadgame.pool.PoolType;

public class Damage {

  private static final Logger logger = LogManager.getLogger(Damage.class);

  public static void applyDamage(Actor target, TestResult severity) {
    if (severity == TestResult.fail) {
      throw new IllegalArgumentException("Failures do not cause damage");
    }

    logger.trace("Applying damage to {}", target);

    Pool damagedPool = target.getPoolToApplyDamageTo();
    Bead bead = chooseBeadDamage(damagedPool);

    boolean wasActive = target.isActive();

    if (damagedPool.type() == PoolType.INJURY) {
      // The target is already completely injured (dead?). Nothing worse can be done
      logger.debug("{} is already dead", target.name());
    } else {
      // We usually move to the exhausted pool, but critical damage is moved to the injury pool
      // and also wwe move to the injury pool in the player is completely exhausted
      Pool destinationPool =
          severity == TestResult.critical || damagedPool.type() == PoolType.EXHAUSTED
              ? target.injuryPool() : target.exhaustedPool();

      logger.debug("{} loses {} from {} to {}", target, bead.color(),
          damagedPool.type(), destinationPool.type());
      damagedPool.remove(bead);
      destinationPool.add(bead);
    }

    if (wasActive && !target.isActive()) {
      logger.debug("{} has been defeated", target.name());
    }

    logger.trace("Finished applying damage to {}", target);

  }


  public static Bead chooseBeadDamage(Pool pool) {
    if (pool.count(Bead.white) > 0) {
      return Bead.white;
    } else if (pool.count(Bead.green) > 0) {
      return Bead.green;
    } else if (pool.count(Bead.blue) > 0) {
      return Bead.blue;
    } else if (pool.count(Bead.black) > 0) {
      return Bead.black;
    } else {
      throw new IllegalStateException("Pool did not have any beads");
    }
  }
}
