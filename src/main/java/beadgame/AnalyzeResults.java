package beadgame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnalyzeResults {

  public AnalyzeResults(List<Result> results) {
    this.results = results;
  }

  public static void main(String[] args) throws IOException {
    AnalyzeResults engine = new AnalyzeResults(Paths.get("simulations/SimulateOneOnOne.csv"));

    engine.filter(r -> r.winnerLevel == r.loserLevel)
        .compareGroups("beads")
        .compareGroups("strategy");
  }

  private AnalyzeResults compareGroups(String groupName) {
    Map<String, Summary> map = new TreeMap<>(AnalyzeResults::compareByLengthFirst);
    results.forEach(r -> r.addToSummary(groupName, map));
    System.out.println("\nComparison for " + groupName);
    map.forEach((k,v) -> System.out.printf("%8s: %s%n", k, v));
    return this;
  }

  private static int compareByLengthFirst(String a, String b) {
    int d = a.length() - b.length();
    return d == 0 ? a.compareTo(b) : d;
  }

  private final List<Result> results;

  private AnalyzeResults filter(Predicate<Result> predicate) {
    return new AnalyzeResults(results.stream().filter(predicate).collect(Collectors.toList()));
  }


  public AnalyzeResults(Path file) throws IOException {
    List<String[]> collection = Files.lines(file)
        .map(s -> s.split(" *, *"))
        .collect(Collectors.toList());

    String[] names = collection.remove(0);
    this.results = collection.stream()
        .map(data -> parseRow(names, data))
        .collect(Collectors.toList());

  }

  private Result parseRow(String[] names, String[] data) {
    Result result = new Result();
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      switch (name) {
        case "Winner_level":
          result.winnerLevel = Integer.parseInt(data[i]);
          break;
        case "Loser_level":
          result.loserLevel = Integer.parseInt(data[i]);
          break;
        case "AverageRounds":
          result.averageRounds = Integer.parseInt(data[i]);
          break;
        case "WinPercent":
          result.winPercent = Double.parseDouble(data[i]);
          break;
        default:
          result.factors.put(name, data[i]);
      }
    }
    return result;
  }

  private static class Summary {
    int n;
    int wins;
    int losses;
    double sumPercent;
    int sumRoundsWin;
    int sumRoundsLoss;

    public void add(boolean wasWin, double winPercent, int averageRounds) {
      n++;
      if (wasWin) {
        wins++;
        sumPercent += winPercent;
        sumRoundsWin += averageRounds;
      } else {
        losses++;
        sumPercent -= winPercent;
        sumRoundsLoss += averageRounds;
      }
    }

    @Override
    public String toString() {
      int aWin = wins == 0 ? 0 : sumRoundsWin / wins;
      int aLoss = losses == 0 ? 0 : sumRoundsLoss / losses;
      return String.format("%2.2f%% (%d/%d), average rounds (%d,%d)",
          sumPercent /n, wins, losses, aWin, aLoss);
    }
  }

  private static class Result {
    int winnerLevel;
    int loserLevel;
    int averageRounds;
    double winPercent;

    final Map<String, String> factors;

    private Result() {
      factors = new HashMap<>();
    }

    public void addToSummary(String fieldEnding, Map<String, Summary> map) {
      String winKey = factors.get("Winner_" + fieldEnding);
      map.computeIfAbsent(winKey, k -> new Summary())
          .add(true, winPercent, averageRounds);
      String lossKey = factors.get("Loser_" + fieldEnding);
      map.computeIfAbsent(lossKey, k -> new Summary())
          .add(false, winPercent, averageRounds);

    }
  }


}
