package beadgame.bead;

import java.util.Objects;

public class Bead {

  public static final Bead BLACK = new Bead(BeadColor.BLACK, BeadEffect.FORCE);
  public static final Bead GREEN = new Bead(BeadColor.GREEN, BeadEffect.SPEED);
  public static final Bead BLUE = new Bead(BeadColor.BLUE, BeadEffect.INTELLECT);
  public static final Bead WHITE = new Bead(BeadColor.WHITE, BeadEffect.ANCESTRY);

  private final BeadColor color;
  private final BeadEffect effect;

  private Bead(BeadColor color, BeadEffect effect) {
    this.color = color;
    this.effect = effect;
  }

  public BeadColor color() {
    return color;
  }

  public BeadEffect effect() {
    return effect;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || o.getClass() != getClass()) {
      return false;
    } else {
      Bead bead = (Bead) o;
      return color == bead.color && effect == bead.effect;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(color, effect);
  }

  @Override
  public String toString() {
    return "Bead{" + color + ":" + effect + "}";
  }
}
