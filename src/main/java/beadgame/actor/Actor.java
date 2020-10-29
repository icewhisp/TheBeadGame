package beadgame.actor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import beadgame.bead.Bead;
import beadgame.combat.Damage;
import beadgame.combat.Team;
import beadgame.combat.Contest;
import beadgame.combat.TestResult;
import beadgame.pool.Pool;
import beadgame.pool.PoolType;

public class Actor {

  private static final Logger logger = LogManager.getLogger(Actor.class);

  private final String name;
  private final Strategy strategy;
  private final Pool ready = new Pool(PoolType.READY);
  private final Pool action = new Pool(PoolType.ACTION);
  private final Pool spent = new Pool(PoolType.SPENT);
  private final Pool exhausted = new Pool(PoolType.EXHAUSTED);
  private final Pool injury = new Pool(PoolType.INJURY);

  public Actor(String name, Strategy strategy, Bead... beads) {
    this.name = Objects.requireNonNull(name);
    this.strategy = Objects.requireNonNull(strategy);
    ready.addAll(beads);
  }

  public Actor(String name, Strategy strategy, Stream<Bead> beads) {
    this.name = Objects.requireNonNull(name);
    this.strategy = Objects.requireNonNull(strategy);
    beads.forEach(ready::add);
  }

  public Actor copy() {
    checkAtFullHealth();
    return new Actor(name, strategy, ready.beads());
  }

  private void checkAtFullHealth() {
    if (action.hasBeads() || spent.hasBeads() || exhausted.hasBeads() || injury.hasBeads()) {
      throw new IllegalStateException(
          "This method should only be called for an actor at full health");
    }
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

  public void prepare(List<Actor> opponents) {

    logger.trace("{} is preparing using strategy {}", this, strategy.name());
    if (!canPrepare()) {
      throw new IllegalStateException("Do not ask a character who cannot prepare to prepare");
    }

    if (canAct()) {
      throw new IllegalStateException("This character is already prepared");
    }

    List<Bead> beads = strategy.chooseBeads(ready, opponents);
    ready.removeAll(beads);
    action.addAll(beads);
    logger.debug("{} has prepared to act with {} ", this, beads);
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
    List<TestResult> results = Contest.closeAttack(this, target, action);
    Pool.move(action, spent);

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

  public static List<String> outputFieldNames() {
    return Arrays.asList("name", "level", "beads", "strategy");
  }

  public List<String> outputFields() {
    checkAtFullHealth();
    return Arrays.asList(name,
        Integer.toString(totalStrength()),
        ready.beads().map(Bead::letter).collect(Collectors.joining()),
        strategy.name()
    );
  }

  public void fullHeal() {
    logger.trace("{} Fully heals", this);
    Pool.move(action, ready);
    Pool.move(spent, ready);
    Pool.move(exhausted, ready);
    Pool.move(injury, ready);
    logger.trace("{} has been fully healed", this);
  }

  public boolean hasValidStrategy() {
    return strategy.possibleFor(this);
  }
}

