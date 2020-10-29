package beadgame.util;

import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utility {

  private static final Random random = new Random();

  public static <S, T> String stringify(
      Map<S, T> map,
      Function<S, String> keyDescription,
      String joiner, Function<T, String> valueDescription) {
    return map.entrySet().stream()
        .map(entry ->
            keyDescription.apply(entry.getKey())
                + joiner
                + valueDescription.apply(entry.getValue())
        ).collect(Collectors.joining(", "));
  }

  public static int randomInt(int n) {
    return random.nextInt(n);
  }

  public static int d6(int modifier) {
    return 1 + random.nextInt(6) + modifier;
  }
}
