package beadgame.combat;

import static beadgame.bead.Bead.black;
import static beadgame.bead.Bead.blue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import beadgame.actor.Actor;
import beadgame.actor.Strategy;

class CombatTest {

  @Test
  void simulate_one_one() {
    Combat combat = new Combat()
        .addToSideA(new Actor("BigHit", Strategy.preferMultipleDifferent(2), black, blue))
        .addToSideB(new Actor("SmallHit", Strategy.preferMultipleDifferent(1), black, blue));

    int winsA = 0;
    for (int i = 0; i < 1000; i++) {
      Combat copy = combat.copy();
      if (copy.run() == copy.A()) {
        winsA++;
      }
    }

    assertTrue(winsA > 500);
  }
}