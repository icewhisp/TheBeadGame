package beadgame.actor;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import beadgame.bead.Bead;
import beadgame.combat.Damage;
import beadgame.combat.Team;
import beadgame.combat.Test;
import beadgame.combat.TestResult;
import beadgame.pool.Pool;
import beadgame.pool.PoolType;
import beadgame.util.Utility;

public class Actor {

  private static final Logger logger = LogManager.getLogger(Actor.class);

  private final String name;
  private final Pool ready = new Pool(PoolType.READY);
  private final Pool action = new Pool(PoolType.ACTION);
  private final Pool spent = new Pool(PoolType.SPENT);
  private final Pool exhausted = new Pool(PoolType.EXHAUSTED);
  private final Pool injury = new Pool(PoolType.INJURY);

  public Actor(String name, Bead... beads) {
    this.name = name;
    ready.addAll(beads);
  }

  public boolean canPrepare() {
    return ready.hasBeads();
  }

  public boolean canAct() {
    return action.hasBeads();
  }

  /* These are beads that are still "in play" and might be used in the combat */
  public int fightingStrength() {
    return ready.count() + action.count() + spent.count();
  }

  /* These are all the beads -- the actor's total raw strength */
  public int totalStrength() {
    return fightingStrength() + exhausted.count() + injury.count();
  }

  public void prepare(Bead... beads) {
    logger.debug("{} is preparing {}", this, Arrays.toString(beads));
    if (!canPrepare()) {
      throw new IllegalStateException("Don't ask a character who cannot prepare to prepare");
    }

    if (canAct()) {
      throw new IllegalStateException("This character is already prepared");
    }

    ready.removeAll(beads);
    action.addAll(beads);
    logger.trace("{} has prepared", this);
  }

  public boolean isActive() {
    return fightingStrength() > 0;
  }

  public Pool readyPool() {
    return ready;
  }

  public void refresh() {
    logger.debug("{} is refreshing {} beads", this, spent.count());
    Pool.move(spent, ready);
    logger.trace("{} has refreshed", this);
  }

  public double speedFactor() {
    // Add a randomish number to the green bead count
    return action.count(Bead.green)
        + new Random(hashCode()).nextDouble() / 100;
  }

  public void performAction(Team allies, Team enemies) {
    logger.debug("{} is taking action", this);

    // Naive method just hits a random enemy
    Actor target = enemies.randomActor();

    logger.debug("{} is attacking {}", this, target);


    // Make the test
    Pool.move(action, spent);
    List<TestResult> results = Test.closeAttack(this, target, action);

    logger.debug("{} has attack results of {}", this, results);


    // Apply criticals first, then regular
    for (TestResult result : results) {
      if (result == TestResult.critical) {
        Damage.applyDamage(target, Damage::chooseBeadForCriticalDamage);
      }
    }
    for (TestResult result : results) {
      if (result == TestResult.success) {
        Damage.applyDamage(target, Damage::chooseBeadForRegularDamage);
      }
    }

    // Spend the beads
    logger.trace("{} has finished taking action", this);
  }

  public Pool getPoolToApplyDamageTo() {
    if (action.hasBeads()) {
      return action;
    } else if (ready.hasBeads()) {
      return ready;
    } else if (spent.hasBeads()) {
      return spent;
    } else if (exhausted.hasBeads()) {
      return exhausted;
    } else {
      return injury;
    }
  }

  public Pool exhaustedPool() {
    return exhausted;
  }

  public Pool injuryPool() {
    return injury;
  }

  public String toString() {
    return String.format("%s[%d-%d-%d-%d-%d]", name,
        ready.count(), action.count(), spent.count(), exhausted.count(), injuryPool().count());
  }

  public String name() {
    return name;
  }

  public void fullHeal() {
    Pool.move(action, ready);
    Pool.move(spent, ready);
    Pool.move(exhausted, ready);
    Pool.move(injury, ready);
  }
}

