package beadgame;

public enum BeadColor {
  black("Force"), blue("Intelligence"), green("Speed"), white("Ancestry");

  private final String effortName;

  BeadColor(String effortName) {
    this.effortName = effortName;
  }

  public String effortName() { return effortName; }

  public char shortName() { return effortName.charAt(0); }
}
