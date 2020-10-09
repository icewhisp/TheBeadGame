package beadgame.bead;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BeadTest {

  @Test
  void test_getters() {
    assertEquals(BeadColor.white, Bead.white.color());
    assertEquals(BeadColor.black, Bead.black.color());
    assertEquals(BeadColor.blue, Bead.blue.color());
    assertEquals(BeadColor.green, Bead.green.color());

    assertEquals(BeadEffect.ancestry, Bead.white.effect());
    assertEquals(BeadEffect.force, Bead.black.effect());
    assertEquals(BeadEffect.intellect, Bead.blue.effect());
    assertEquals(BeadEffect.speed, Bead.green.effect());
  }

  @Test
  void test_to_string() {
    assertEquals("Bead{WHITE:ANCESTRY}", Bead.white.toString());
    assertEquals("Bead{BLACK:FORCE}", Bead.black.toString());
    assertEquals("Bead{BLUE:INTELLECT}", Bead.blue.toString());
    assertEquals("Bead{GREEN:SPEED}", Bead.green.toString());
  }

}