package beadgame.actor;

import static beadgame.bead.Bead.*;

import java.util.Arrays;
import java.util.List;

import beadgame.bead.Bead;

public class ActorInstances {

  public final static List<Actor> all() {
    return Arrays.asList(
        new Actor("Throg(1)", black),
        new Actor("Throg(2)", black, black),
        new Actor("Throg(3)", black, black, black),
        new Actor("Throg(4)", black, black, black, black),
        new Actor("Throg(5)", black, black, black, black, black),

        new Actor("Swiftblade(1)", green),
        new Actor("Swiftblade(2)", green, green),
        new Actor("Swiftblade(3)", green, green, green),
        new Actor("Swiftblade(4)", green, green, green, green),
        new Actor("Swiftblade(5)", green, green, green, green, green),

        new Actor("TheFox(1)", blue),
        new Actor("TheFox(2)", blue, blue),
        new Actor("TheFox(3)", blue, blue, blue),
        new Actor("TheFox(4)", blue, blue, blue, blue),
        new Actor("TheFox(5)", blue, blue, blue, blue, blue),

        new Actor("LeroyJenkins(2)", green, black),
        new Actor("LeroyJenkins(3)", green, black, green),
        new Actor("LeroyJenkins(4)", green, black, green, black),
        new Actor("LeroyJenkins(5)", green, black, green, black, green),

        new Actor("Duelist(2)", green, blue),
        new Actor("Duelist(3)", green, blue, green),
        new Actor("Duelist(4)", green, blue, green, blue),
        new Actor("Duelist(5)", green, blue, green, blue, green),

        new Actor("NoRush(2)", black, blue),
        new Actor("NoRush(3)", black, blue, black),
        new Actor("NoRush(4)", black, blue, black, blue),
        new Actor("Duelist(5)", black, blue, black, blue, black),

        new Actor("Balanced(3)", black, blue, green),
        new Actor("Balanced(4)", black, blue, green, black),
        new Actor("Balanced(5)", black, blue, green, black, blue)

        );
  }



}
