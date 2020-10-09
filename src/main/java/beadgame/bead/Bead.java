package beadgame.bead;

import java.util.Objects;

public class Bead {

  public static final Bead black = new Bead(BeadColor.black, BeadEffect.force);
  public static final Bead green = new Bead(BeadColor.green, BeadEffect.speed);
  public static final Bead blue = new Bead(BeadColor.blue, BeadEffect.intellect);
  public static final Bead white = new Bead(BeadColor.white, BeadEffect.ancestry);

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
    return color.toString();
  }
}
