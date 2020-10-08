package beadgame.pool;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import beadgame.bead.Bead;
import beadgame.util.Utility;

public class Pool {

  private final PoolType type;
  private final Map<Bead, Integer> contents;

  public Pool(PoolType type) {
    this.type = type;
    this.contents = new TreeMap<>(Comparator.comparing(Bead::color));
  }

  @Override
  public String toString() {
    return "Pool{" + type + ": " + Utility.stringify(contents) + '}';
  }

  public int count() {
    return contents.values().stream().mapToInt(v -> v).sum();
  }

  public int count(Bead bead) {
    return contents.getOrDefault(bead, 0);
  }

  public boolean contains(Bead bead) {
    return count(bead) > 0;
  }

  public void add(Bead bead) {
    int current = count(bead);
    contents.put(bead, current + 1);
  }

  public void remove(Bead bead) {
    int current = count(bead);
    if (current == 0) {
      throw new IllegalStateException(
          "Cannot remove a bead if none fo that type si contained in the pool: " + this);
    } else if (current == 1) {
      contents.remove(bead);
    } else {
      contents.put(bead, current - 1);
    }
  }

}
