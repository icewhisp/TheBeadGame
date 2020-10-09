package beadgame.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import beadgame.actor.Actor;
import beadgame.util.Utility;

public class Team {

  private final String name;
  private final List<Actor> actors;

  public Team(String name) {
    this.name = name;
    actors = new ArrayList<>();
  }

  public static List<Actor> allActors(Team... teams) {
    ArrayList<Actor> result = new ArrayList<>();
    for (Team team : teams) {
      result.addAll(team.actors());
    }
    return result;
  }

  public Team add(Actor a) {
    actors.add(a);
    return this;
  }

  public String name() {
    return name;
  }

  public List<Actor> actors() {
    return actors;
  }

  @Override
  public String toString() {
    return name;
  }

  public boolean anyActive() {
    return actors.stream().anyMatch(Actor::isActive);
  }

  public void forEach(Consumer<Actor> consumer) {
    actors.forEach(consumer);
  }

  public boolean contains(Actor actor) {
    return actors.contains(actor);
  }

  public Actor randomActor() {
    int n = actors.size();
    return actors.get(Utility.randomInt(n));
  }

  public void fullHeal() {
    actors.forEach(Actor::fullHeal);
  }
}
