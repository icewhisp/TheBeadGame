package beadgame.combat;

import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import beadgame.actor.Actor;

public class Combat {

  private static final Logger logger = LogManager.getLogger(Combat.class);

  private final Team A, B;
  private int roundNumber;

  public Combat() {
    this(new Team("A"), new Team("B"));
  }

  public Combat copy() {
    return new Combat(A.copy(), B.copy());
  }

  public Combat(Team a, Team b) {
    A = a;
    B = b;
  }

  public Combat reset() {
    roundNumber = 0;
    A.fullHeal();
    B.fullHeal();
    return this;
  }

  public Combat addToSideA(Actor a) {
    if (A.contains(a) || B.contains(a)) {
      throw new IllegalArgumentException("Cannot add the same actor twice");
    } else {
      A.add(a);
      return this;
    }
  }

  public Combat addToSideB(Actor b) {
    if (A.contains(b) || B.contains(b)) {
      throw new IllegalArgumentException("Cannot add the same actor twice");
    } else {
      B.add(b);
      return this;
    }
  }

  public Team winningTeam() {
    if (!A.anyActive()) {
      return B;
    }
    if (!B.anyActive()) {
      return A;
    }
    return null;
  }

  public void preparationPhase() {
    roundNumber++;
    logger.debug("round {} - preparation phase", roundNumber);

    A.actors().stream()
        .filter(Actor::canPrepare)
        .forEach(actor -> actor.prepare(B.actors()));
    B.actors().stream()
        .filter(Actor::canPrepare)
        .forEach(actor -> actor.prepare(A.actors()));
  }

  public void refreshPhase() {
    logger.debug("round {} - refresh phase", roundNumber);
    A.forEach(Actor::refresh);
    B.forEach(Actor::refresh);
  }

  /* Returns false if no-one could act */
  public boolean resolutionPhase() {
    logger.debug("round {} - resolution phase", roundNumber);

    // Find all actors that can act
    List<Actor> toAct = Team.allActors(A, B);
    toAct.removeIf(a -> !a.canAct());
    if (toAct.isEmpty()) {
      return false;
    }

    while (!toAct.isEmpty()) {
      Actor actor = toAct.stream().max(Comparator.comparingDouble(Actor::speedFactor)).get();
      toAct.remove(actor);
      if (!actor.canAct()) continue;
      if (A.contains(actor)) {
        actor.performAction(A, B);
      } else {
        actor.performAction(B, A);
      }
    }
    return true;
  }

  public Team run() {
    logger.info("Starting combat between {} and {}", A, B);
    Team winner = null;
    while (winner == null) {
      preparationPhase();
      boolean anyoneActed = resolutionPhase();
      if (!anyoneActed) {
        // No one can act, so refresh pools
        refreshPhase();
      }
      winner = winningTeam();
    }
    Team loser = (winner == A) ? B : A;
    logger.info("{} defeated {} in {} rounds", winner, loser, roundNumber);
    return winner;
  }

  public int roundsTaken() {
    return roundNumber;
  }

  public Team A() {
    return A;
  }

  public Team B() {
    return B;
  }
}
