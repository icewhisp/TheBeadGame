/**
 * The bead class is used to represent the different stats and pools a character has
 *
 * @author Joshua Wills
 * @version 1
 */
public class Bead {

  // instance variables
  private final BeadColor color; //What of the four colors a bead should be black,white,blue,or green
  private final int use;  //What round the A.I. will attempt to use the bead to perform an action
  private BeadState state = BeadState.in_pool;

  /**
   * Constructor for objects of class Bead
   */
  public Bead(BeadColor color, int use) {
    // initialise instance variables
    this.color = color;
    this.use = use;
  }

  public void takeDamage() //Used when a bead takes damage
  {
    state = state.becomeDamaged();
  }

  /**
   * Used when you need to check if a bead can be used, both for action damage when going second and
   * for use when choosing what beads you want to use
   */
  public boolean isUseable() {
      return state == BeadState.in_pool;
  }

  //If a bead is just spent then it returns to the in pool state
  public void refresh()
  {
    if (state == BeadState.spent) {
      state = BeadState.in_pool;
    }
  }

  public boolean inHand(int round) //Checks to see if a bead if used this roud
  {
      return round == use && state == BeadState.in_pool;
  }

  public int getEffect() //1 = black bead, 2 = blue bead, 3 = green bead, anything else returns 4
  {
    return state.ordinal() + 1;
  }

  public BeadState getState() {
    return state;
  }

  public void setState(BeadState state) {
    this.state = state;
  }
}
