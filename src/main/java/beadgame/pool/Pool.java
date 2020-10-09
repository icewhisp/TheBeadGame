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

  public static void move(Pool fromPool, Pool toPool) {
    fromPool.contents.forEach((bead, count) -> {
      for (int i = 0; i < count; i++) {
        toPool.add(bead);
      }
    });
    fromPool.contents.clear();
  }

  @Override
  public String toString() {
    return "Pool{" + type + ": " + contentDescription() + '}';
  }

  public String contentDescription() {
    return Utility.stringify(contents, c -> c.color().toString(), "=", Object::toString);
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
          "Cannot remove a bead if none of that type is contained in the pool: " + this);
    } else if (current == 1) {
      contents.remove(bead);
    } else {
      contents.put(bead, current - 1);
    }
  }

  public boolean hasBeads() {
    return count() > 0;
  }

  public Pool addAll(Bead... beads) {
    for (Bead bead : beads) {
      add(bead);
    }
    return this;
  }

  public Pool removeAll(Bead... beads) {
    for (Bead bead : beads) {
      remove(bead);
    }
    return this;
  }

  public Bead randomBead() {
    int n = count();
    if (n == 0) {
      throw new IllegalStateException("Pool has no beads");
    }
    int index = Utility.randomInt(n);
    for (Bead bead : contents.keySet()) {
      index -= contents.get(bead);
      if (index <= 0) {
        return bead;
      }
    }
    throw new IllegalStateException("Should not have got to here");
  }

  public PoolType type() {
    return type;
  }
}
