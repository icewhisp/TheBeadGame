package beadgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * This class is emant to capture all information on a character. A character has a set of pools
 * each containing beads
 * <p>
 * I'm using the names of pools taken from the PDF describing the game
 * <p>
 * TODO: Add a boolean function to signify when somoene is out of the fight (and test it)
 * <p>
 * TODO: test the refresh function
 * <p>
 * TODO: write takeSinglePointOfDamage and test takeDamage
 */
public class Actor {

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
   * Define a new character by name and number of beads they have. This just calls the other
   * constructir and is really jsut to make writign tests easier
   *
   * @param name  call them this
   * @param beads their beads
   */
  public Actor(String name, BeadColor... beads) {
    this(name, Arrays.asList(beads));
  }

  /**
   * Define a new character by name and number of beads they have.
   *
   * @param name  call them this
   * @param beads their beads
   */
  public Actor(String name, Collection<BeadColor> beads) {
    this.name = name;
    for (BeadColor bead : beads) {
      ready.add(bead);
    }
  }


  /**
   * Take some damage
   *
   * @param hits the number of beads damage to take
   */
  public void takeDamage(int hits) {

    // We can just repeat taking one point of damage at a time
    for (int i = 0; i < hits; i++) {
      takeSinglePointOfDamage();
    }
  }

  private void takeSinglePointOfDamage() {
    /*
        When an action damages an opponent, they must move a bead to the exhausted pool.
        This must be taken from the active pool if possible, then the ready pool.
        And if both those are empty, from the spent pool. If all these pools are empty
        a bead is moved from the exhausted pool to the injury pool.
    */

    // If the active pool is not empty, move a random bead from it to exhausted
    // Otherwise try from the ready pool
    // Otherwise try from the spent pool
    // Otherwise move a random bead from exhausted to injured
    // if all beads are in injured pool I guess do nothing as the chaarcetr is already out of it
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

  /**
   * Move beads from the ready pool to the action pool. Thsi calls the other prepare methdo and is
   * just to make testing easier
   *
   * @param beads items to prepare
   */
  public void prepare(BeadColor... beads) {
    prepare(Arrays.asList(beads));
  }


  /**
   * Move beads from the ready pool to the action pool
   *
   * @param beads items to prepare
   */
  public void prepare(Collection<BeadColor> beads) {

    if (!canPrepare()) {
      throw new IllegalStateException("Don't ask a character who cannot prepare to prepare");
    }

    if (canAct()) {
      throw new IllegalStateException("This character is already prepared");
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
      throw new IllegalStateException("Don't ask for the speed of someone who cannot act");
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
    return name + "{" + describe(action) + "|" + describe(ready) + "|" + describe(spent)
        + "|" + describe(exhausted) + "|" + describe(injury) + "}";
  }

  private String describe(List<BeadColor> pool) {
    if (pool.isEmpty()) {
      return "-";
    }

    String result = "";
    for (BeadColor bead : pool) {
      result = result + bead.shortName();
    }
    return result;
  }

  public List<BeadColor> readyPool() {
    return ready;
  }
}