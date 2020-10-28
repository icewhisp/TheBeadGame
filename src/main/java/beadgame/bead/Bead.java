package beadgame.bead;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Bead {

  public static final Bead black = new Bead(BeadColor.black, BeadEffect.force, "B");
  public static final Bead green = new Bead(BeadColor.green, BeadEffect.speed, "G");
  public static final Bead blue = new Bead(BeadColor.blue, BeadEffect.intellect, "b");
  public static final Bead white = new Bead(BeadColor.white, BeadEffect.ancestry, "W");

  public static final List<Bead> ALL_BEADS = Arrays.asList(black, blue, green, white);

  private final BeadColor color;
  private final BeadEffect effect;
  private final String letter;

  private Bead(BeadColor color, BeadEffect effect, String letter) {
    this.color = color;
    this.effect = effect;
    this.letter = letter;
  }

  public BeadColor color() {
    return color;
  }

  public BeadEffect effect() {
    return effect;
  }

  public String letter() {
    return letter;
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
