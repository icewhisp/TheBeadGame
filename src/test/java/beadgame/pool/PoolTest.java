package beadgame.pool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import beadgame.bead.Bead;

class PoolTest {

  @Test
  void initial_empty_pool() {
    Pool pool = new Pool(PoolType.ACTION);
    assertEquals(0, pool.count());
    assertEquals("Pool{ACTION: }", pool.toString());
  }

  @Test
  void adding_and_removing_beads() {
    Pool pool = new Pool(PoolType.INJURY);
    assertEquals(0, pool.count());
    assertEquals(0, pool.count(Bead.BLUE));
    assertEquals(0, pool.count(Bead.GREEN));

    pool.add(Bead.BLUE);
    assertEquals(1, pool.count());
    assertEquals(1, pool.count(Bead.BLUE));
    assertEquals(0, pool.count(Bead.GREEN));
    assertEquals("Pool{INJURY: BLUE=1}", pool.toString());

    pool.add(Bead.BLUE);
    assertEquals(2, pool.count());
    assertEquals(2, pool.count(Bead.BLUE));
    assertEquals(0, pool.count(Bead.GREEN));
    assertEquals("Pool{INJURY: BLUE=2}", pool.toString());

    pool.add(Bead.GREEN);
    assertEquals(3, pool.count());
    assertEquals(2, pool.count(Bead.BLUE));
    assertEquals(1, pool.count(Bead.GREEN));
    assertEquals("Pool{INJURY: BLUE=2, GREEN=1}", pool.toString());

    pool.remove(Bead.BLUE);
    assertEquals(2, pool.count());
    assertEquals(1, pool.count(Bead.BLUE));
    assertEquals(1, pool.count(Bead.GREEN));
    assertEquals("Pool{INJURY: BLUE=1, GREEN=1}", pool.toString());

    pool.remove(Bead.BLUE);
    assertEquals(1, pool.count());
    assertEquals(0, pool.count(Bead.BLUE));
    assertEquals(1, pool.count(Bead.GREEN));
    assertEquals("Pool{INJURY: GREEN=1}", pool.toString());
  }

  @Test
  void throws_error_if_we_try_to_remove_a_bead_which_is_not_there() {
    Pool pool = new Pool(PoolType.INJURY);
    assertThrows(IllegalStateException.class, () -> pool.remove(Bead.GREEN));
  }
}