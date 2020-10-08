package beadgame.bead;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BeadTest {

  @Test
  void test_getters() {
    assertEquals(BeadColor.WHITE, Bead.WHITE.color());
    assertEquals(BeadColor.BLACK, Bead.BLACK.color());
    assertEquals(BeadColor.BLUE, Bead.BLUE.color());
    assertEquals(BeadColor.GREEN, Bead.GREEN.color());

    assertEquals(BeadEffect.ANCESTRY, Bead.WHITE.effect());
    assertEquals(BeadEffect.FORCE, Bead.BLACK.effect());
    assertEquals(BeadEffect.INTELLECT, Bead.BLUE.effect());
    assertEquals(BeadEffect.SPEED, Bead.GREEN.effect());
  }

  @Test
  void test_to_string() {
    assertEquals("Bead{WHITE:ANCESTRY}", Bead.WHITE.toString());
    assertEquals("Bead{BLACK:FORCE}", Bead.BLACK.toString());
    assertEquals("Bead{BLUE:INTELLECT}", Bead.BLUE.toString());
    assertEquals("Bead{GREEN:SPEED}", Bead.GREEN.toString());
  }

}