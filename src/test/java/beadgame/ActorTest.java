package beadgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ActorTest {

  @Test
  void gives_the_right_name() {
    Actor2 bob = new Actor2("bob");
    assertEquals("bob{-|-|-|-|-}", bob.toString());

    Actor2 pratik = new Actor2("pratik", BeadColor.blue, BeadColor.green);
    assertEquals("pratik{-|IS|-|-|-}", pratik.toString());
  }

  @Test
  void character_with_beads_can_prepare_and_then_act() {
    Actor2 bob = new Actor2("bob", BeadColor.black, BeadColor.blue);

    Assertions.assertFalse(bob.canAct());  // Cannot act yet as not prepared
    Assertions.assertTrue(bob.canPrepare());  // Can prepare

    bob.prepare(BeadColor.black);      // Prepares one bead

    assertEquals("bob{F|I|-|-|-}", bob.toString());

    Assertions.assertTrue(bob.canAct());  // Now he can act
  }

  @Test
  void throws_an_error_if_we_make_a_bad_prepare_call() {
    Actor2 bob = new Actor2("bob", BeadColor.black, BeadColor.blue);

    // Cannot prepare two blacks
    assertThrows(IllegalStateException.class,
        () -> bob.prepare(BeadColor.black, BeadColor.black)
    );

  }

  @Test
  void speeds_are_correct() {
    Actor2 bob = new Actor2("bob", BeadColor.black, BeadColor.green, BeadColor.green,
        BeadColor.green);
    Actor2 billy = new Actor2("billy", BeadColor.black, BeadColor.green, BeadColor.green,
        BeadColor.green);

    bob.prepare(BeadColor.black, BeadColor.green);
    billy.prepare(BeadColor.green, BeadColor.green);

    assertEquals("bob{FS|SS|-|-|-}", bob.toString());
    assertEquals("billy{SS|FS|-|-|-}", billy.toString());


    Assertions.assertEquals(1, bob.getSpeed());
    Assertions.assertEquals(2, billy.getSpeed());

  }


}