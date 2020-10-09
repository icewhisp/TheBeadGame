package beadgame.combat;

import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import beadgame.actor.Actor;
import beadgame.bead.Bead;
import beadgame.pool.Pool;
import beadgame.pool.PoolType;

public class Damage {

  private static final Logger logger = LogManager.getLogger(Damage.class);

  public static void applyDamage(Actor target, Function<Pool, Bead> picker) {
    logger.trace("Applying damage to {}", target);

    Pool damagedPool = target.getPoolToApplyDamageTo();
    Bead bead = picker.apply(damagedPool);

    boolean wasActive = target.isActive();

    if (damagedPool.type() == PoolType.INJURY) {
      // The target is already completely injured (dead?). Nothing worse can be done
      logger.debug("{} is already dead", target.name());
    } else {
      // Unless the charcter is completely exhausted, move to exhaustion pool
      Pool destinationPool = damagedPool.type() == PoolType.EXHAUSTED
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

  public static Bead chooseBeadForCriticalDamage(Pool pool) {
    if (pool.count(Bead.black) > 0) {
      return Bead.black;
    } else if (pool.count(Bead.blue) > 0) {
      return Bead.blue;
    } else if (pool.count(Bead.green) > 0) {
      return Bead.green;
    } else if (pool.count(Bead.white) > 0) {
      return Bead.white;
    } else {
      throw new IllegalStateException("Pool did not have any beads");
    }
  }

  public static Bead chooseBeadForRegularDamage(Pool pool) {
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
