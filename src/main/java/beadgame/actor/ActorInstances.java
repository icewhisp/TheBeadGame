package beadgame.actor;

import static beadgame.bead.Bead.black;
import static beadgame.bead.Bead.blue;
import static beadgame.bead.Bead.green;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ActorInstances {

  public static List<Actor> makeAll() {
    ArrayList<Actor> list = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      Strategy strategy = Strategy.preferMultipleDifferent(i);
      actorsWithStrategy(strategy).forEach(list::add);
    }
    return list;
  }

  private static Stream<Actor> actorsWithStrategy(Strategy strategy) {
    return Stream.of(
        new Actor("Throg", strategy, black),
        new Actor("Throg", strategy, black, black),
        new Actor("Throg", strategy, black, black, black),
        new Actor("Throg", strategy, black, black, black, black),
        new Actor("Throg", strategy, black, black, black, black, black),

        new Actor("Swiftblade", strategy, green),
        new Actor("Swiftblade", strategy, green, green),
        new Actor("Swiftblade", strategy, green, green, green),
        new Actor("Swiftblade", strategy, green, green, green, green),
        new Actor("Swiftblade", strategy, green, green, green, green, green),

        new Actor("TheFox", strategy, blue),
        new Actor("TheFox", strategy, blue, blue),
        new Actor("TheFox", strategy, blue, blue, blue),
        new Actor("TheFox", strategy, blue, blue, blue, blue),
        new Actor("TheFox", strategy, blue, blue, blue, blue, blue),

        new Actor("LeroyJenkins", strategy, green, black),
        new Actor("LeroyJenkins", strategy, green, black, green),
        new Actor("LeroyJenkins", strategy, green, black, green, black),
        new Actor("LeroyJenkins", strategy, green, black, green, black, green),

        new Actor("Duelist", strategy, green, blue),
        new Actor("Duelist", strategy, green, blue, green),
        new Actor("Duelist", strategy, green, blue, green, blue),
        new Actor("Duelist", strategy, green, blue, green, blue, green),

        new Actor("NoRush", strategy, black, blue),
        new Actor("NoRush", strategy, black, blue, black),
        new Actor("NoRush", strategy, black, blue, black, blue),
        new Actor("Duelist", strategy, black, blue, black, blue, black),

        new Actor("Balanced", strategy, black, blue, green),
        new Actor("Balanced", strategy, black, blue, green, black),
        new Actor("Balanced", strategy, black, blue, green, black, blue)
    ).filter(Actor::hasValidStrategy);
  }


}
