import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is emant to capture all information on a character. A character has a set of pools
 * each containing beads
 * <p>
 * I'm using the names of pools taken from the PDF describing the game
 */
public class Character {

  /* The action pool that contains the effort you are committing to an action right now */
  private final List<BeadColor> action = new ArrayList<>();

  /* The ready pool contains effort that is available for use in the current encounter */
  private final List<BeadColor> ready = new ArrayList<>();

  /* The spent pool contains effort that has been spent on actions */
  private final List<BeadColor> spent = new ArrayList<>();

  /* The exhausted pool contains effort that has been exhausted and is no longer available
  for use in this encounter */
  private final List<BeadColor> exhausted = new ArrayList<>();

  /* The injury pool contains effort that is completely unavailable to the character */
  private final List<BeadColor> injury = new ArrayList<>();

  // We'll give them a name because it's fun and helps in debugging
  private final String name;

  /**
   * Define a new character by name and number of beads they have.
   *
   * @param name  call them this
   * @param beads their beads
   */
  public Character(String name, BeadColor... beads) {
    this.name = name;
    for (BeadColor bead : beads) {
      ready.add(bead);
    }
  }


  /**
   * A character can prepare if they have any beads in their action pool
   *
   * @return true if they can act
   */
  public boolean canAct() {
    return action.size() > 0;
  }

  /**
   * A character can prepare if they have any beads in their ready pool
   *
   * @return true if they can prepare
   */
  public boolean canPrepare() {
    return ready.size() > 0;
  }


  public void prepare(Collection<BeadColor> beads) {

    if (!canPrepare()) {
      throw new IllegalStateException("Don't ask a characetr who cannot prepare to prepare");
    }

    // First we remove the beads from the ready pool
    removeBeadsFromPool(ready, beads);
    // Then add them to action pool;
    action.addAll(beads);
  }

  private void removeBeadsFromPool(List<BeadColor> pool, Collection<BeadColor> beadsToRemove) {
    for (BeadColor bead : beadsToRemove) {
      boolean success = pool.remove(bead);
      if (!success) {
        throw new IllegalStateException("Did not find bead '" + bead + "' in the pool");
      }
    }
  }

  /**
   * Characters refresh by moving beads from their spent pool into their ready pool
   */
  public void refresh() {
    ready.addAll(spent);    // Copy into the ready pool
    spent.clear(); // and clear the spent pool
  }

  /**
   * Returns n where n is the number of green beads
   *
   * @return speed
   */
  public int getSpeed() {
    if (!canAct()) {
      throw new IllegalStateException("Don't ask for the speed of seomeone who cannot act");
    }

    int speed = 0;
    for (BeadColor x : action) {
      if (x == BeadColor.green) {
        speed++;
      }
    }
    return speed;
  }


  @Override
  public String toString() {
    return name;
  }
}
