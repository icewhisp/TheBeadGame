package beadgame;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import beadgame.actor.Actor;
import beadgame.actor.ActorInstances;
import beadgame.combat.Combat;

public class SimulateOneOnOne {


  private final Combat combat;
  private final int replications;

  private double teamAWins;
  private double averageRounds;

  public SimulateOneOnOne(Combat combat, int replications) {
    this.combat = combat;
    this.replications = replications;
  }

  public static void main(String[] args) throws FileNotFoundException {

    PrintStream out = new PrintStream(
        "simulations/" + SimulateOneOnOne.class.getSimpleName() + ".csv");

    writeHeader(out);
    List<Combat> combats = makeCombats();

    AtomicInteger n = new AtomicInteger();
    combats.stream()
        .peek(c -> progress(n))
        .forEach(combat -> runCombat(combat, out));
    System.out.println();

    out.close();
  }

  private static void progress(AtomicInteger n) {
    if (n.incrementAndGet() % 50 == 0) {
      System.out.println("[" + n + "]");
    } else {
      System.out.print('.');
    }
  }

  private static void runCombat(Combat combat, PrintStream out) {
    SimulateOneOnOne run = new SimulateOneOnOne(combat, 1000).run();
    Actor a = combat.A().actors().get(0);
    Actor b = combat.B().actors().get(0);
    if (run.teamAWins > 0.5) {
      a.outputFields().forEach(f -> out.print(f + ","));
      b.outputFields().forEach(f -> out.print(f + ","));
      out.format("%2.1f, %d%n", run.teamAWins * 100, Math.round(run.averageRounds));
    } else {
      {
        b.outputFields().forEach(f -> out.print(f + ","));
        a.outputFields().forEach(f -> out.print(f + ","));
        out.format("%2.1f, %d%n", (1.0 - run.teamAWins) * 100, Math.round(run.averageRounds));
      }
    }
  }

  private static List<Combat> makeCombats() {
    List<Actor> actors = ActorInstances.makeAll();
    List<Combat> combats = new ArrayList<>();
    for (int i = 0; i < actors.size(); i++) {
      for (int j = i + 1; j < actors.size(); j++) {
        combats.add(new Combat()
            .addToSideA(actors.get(i))
            .addToSideB(actors.get(j)));
      }
    }
    return combats;
  }

  private static void writeHeader(PrintStream out) {
    Actor.outputFieldNames().stream().map(actorField -> "Winner_" + actorField + ",")
        .forEach(out::print);
    Actor.outputFieldNames().stream().map(actorField -> "Loser_" + actorField + ",")
        .forEach(out::print);
    out.println("WinPercent,AverageRounds");
  }

  private SimulateOneOnOne run() {
    AtomicInteger totalAWins = new AtomicInteger();
    AtomicInteger totalRounds = new AtomicInteger();
    IntStream.range(0, replications)
        .parallel()
        .mapToObj(i -> combat.copy())
        .forEach(combat -> {
          if (combat.run() == combat.A()) {
            totalAWins.incrementAndGet();
          }
          totalRounds.addAndGet(combat.roundsTaken());
        });

    this.teamAWins = (double) totalAWins.get() / replications;
    this.averageRounds = (double) totalRounds.get() / replications;

    return this;
  }


}
