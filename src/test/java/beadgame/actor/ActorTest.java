package beadgame.actor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import beadgame.bead.Bead;

class ActorTest {

  @Test
  void empty_pool_is_as_expected() {
    Actor a = new Actor("Juan");
    assertEquals("Juan{READY:[], ACTION:[], SPENT:[], EXHAUSTED:[], INJURY:[]}", a.toString());
    assertFalse(a.canPrepare());
  }

  @Test
  void starting_with_a_few_beads() {
    Actor a = new Actor("Emily", Bead.black, Bead.black, Bead.blue, Bead.white);
    assertEquals(
        "Emily{READY:[BLACK=2, BLUE=1, WHITE=1], ACTION:[], SPENT:[], EXHAUSTED:[], INJURY:[]}",
        a.toString());
  }

}