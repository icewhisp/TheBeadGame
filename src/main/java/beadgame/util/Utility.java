package beadgame.util;

import java.util.Map;
import java.util.stream.Collectors;

import beadgame.bead.Bead;

public class Utility {

  public static String stringify(Map<Bead, ?> map) {
    return map.keySet().stream()
        .map(bead -> bead.color() + "=" + map.get(bead))
        .collect(Collectors.joining(", "));
  }
}
