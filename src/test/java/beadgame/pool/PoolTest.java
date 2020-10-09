package beadgame.pool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import beadgame.bead.Bead;

class PoolTest {

  @Test
  void initial_empty_pool() {
    Pool pool = new Pool(PoolType.ACTION);
    assertEquals(0, pool.count());
    assertFalse(pool.hasBeads());
    assertEquals("Pool{ACTION: }", pool.toString());
  }

  @Test
  void adding_and_removing_beads() {
    Pool pool = new Pool(PoolType.INJURY);
    assertEquals(0, pool.count());
    assertEquals(0, pool.count(Bead.blue));
    assertEquals(0, pool.count(Bead.green));

    pool.add(Bead.blue);
    assertEquals(1, pool.count());
    assertEquals(1, pool.count(Bead.blue));
    assertEquals(0, pool.count(Bead.green));
    assertEquals("Pool{INJURY: BLUE=1}", pool.toString());

    pool.add(Bead.blue);
    assertEquals(2, pool.count());
    assertEquals(2, pool.count(Bead.blue));
    assertEquals(0, pool.count(Bead.green));
    assertEquals("Pool{INJURY: BLUE=2}", pool.toString());

    pool.add(Bead.green);
    assertEquals(3, pool.count());
    assertEquals(2, pool.count(Bead.blue));
    assertEquals(1, pool.count(Bead.green));
    assertEquals("Pool{INJURY: BLUE=2, GREEN=1}", pool.toString());

    pool.remove(Bead.blue);
    assertEquals(2, pool.count());
    assertEquals(1, pool.count(Bead.blue));
    assertEquals(1, pool.count(Bead.green));
    assertEquals("Pool{INJURY: BLUE=1, GREEN=1}", pool.toString());

    pool.remove(Bead.blue);
    assertEquals(1, pool.count());
    assertEquals(0, pool.count(Bead.blue));
    assertEquals(1, pool.count(Bead.green));
    assertEquals("Pool{INJURY: GREEN=1}", pool.toString());
    assertTrue(pool.hasBeads());
  }

  @Test
  void throws_error_if_we_try_to_remove_a_bead_which_is_not_there() {
    Pool pool = new Pool(PoolType.INJURY);
    assertThrows(IllegalStateException.class, () -> pool.remove(Bead.green));
  }

  @Test
  void test_add_all() {
    Pool pool = new Pool(PoolType.EXHAUSTED).addAll(Bead.white, Bead.white, Bead.black);
    assertEquals("Pool{EXHAUSTED: BLACK=1, WHITE=2}", pool.toString());
  }

  @Test
  void test_remove_all() {
    Pool pool = new Pool(PoolType.EXHAUSTED)
        .addAll(Bead.white, Bead.white, Bead.black)
        .removeAll(Bead.white, Bead.black);
    assertEquals("Pool{EXHAUSTED: WHITE=1}", pool.toString());
  }

  @Test
  void throws_error_if_we_try_to_remove_beads_not_there() {
    Pool pool = new Pool(PoolType.INJURY)
        .addAll(Bead.white, Bead.white, Bead.black);
    assertThrows(IllegalStateException.class, () -> pool.removeAll(Bead.black, Bead.black));
  }

}