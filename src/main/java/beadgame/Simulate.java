package beadgame;

import java.util.Arrays;
import java.util.stream.IntStream;

import beadgame.actor.Actor;
import beadgame.bead.Bead;
import beadgame.combat.Combat;
import beadgame.combat.Team;

public class Simulate {


  private final Combat combat;
  private final int replications;

  private double teamAWins;
  private double averageRounds;

  public Simulate(Combat combat, int replications) {
    this.combat = combat;
    this.replications = replications;

  }

  public static void main(String[] args) {
    Actor a = new Actor("Barbarian", Bead.black, Bead.black);
    Actor b = new Actor("Thief", Bead.green, Bead.green);

    Combat combat = new Combat()
        .addToSideA(a)
        .addToSideB(b);

    Simulate run = new Simulate(combat, 1000).run();

    System.out.printf("%s vs %s: %s won %1.1f%% of the time in an average of %1.1f rounds",
        a.name(), b.name(), a.name(), run.teamAWins*100, run.averageRounds);

  }

  private Simulate run() {
    Team[] winner = new Team[replications];
    int[] rounds = new int[replications];

    for (int i = 0; i < replications; i++) {
      combat.reset();
      winner[i] = combat.run();
      rounds[i] = combat.roundsTaken();
    }

    this.teamAWins = (double) Arrays.stream(winner).filter(t -> t == combat.A()).count()
        / replications;
    this.averageRounds = (double) IntStream.of(rounds).sum() / replications;


    return this;
  }


}
