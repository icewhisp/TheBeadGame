package beadgame;

/**
 * Write a description of class run here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Run {
  // instance variables - replace the example below with your own


  public static void main(String[] args) {
    BeadColor[] FighterBlack = new BeadColor[]{
        BeadColor.black, BeadColor.black, BeadColor.black, BeadColor.black, BeadColor.black,
        BeadColor.black};
    BeadColor[] FighterBlue = new BeadColor[]{
        BeadColor.blue, BeadColor.blue, BeadColor.blue, BeadColor.blue, BeadColor.blue,
        BeadColor.blue};
    BeadColor[] FighterGreen = new BeadColor[]{
        BeadColor.green, BeadColor.green, BeadColor.green, BeadColor.green, BeadColor.blue,
        BeadColor.blue};
    int[] solo = new int[]{1, 2, 3, 4, 5, 6};
    int[] pair = new int[]{1, 1, 2, 2, 3, 3};
    int[] stratUseA = solo;
    int[] stratUseB = solo;
    BeadColor[] coloruseA = FighterBlack;
    BeadColor[] coloruseB = FighterBlack;
    Fight battle = new Fight();
    Pool fighterA = new Pool(coloruseA, stratUseA);
    Pool fighterB = new Pool(coloruseB, stratUseB);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {

        if (i == 0) {
          System.out.println("Both fighters use one bead");
          stratUseB = solo;
          stratUseA = solo;
        } else if (i == 1) {
          System.out.println("One fighterA uses a bead, FighterB uses two");
          stratUseB = pair;
          stratUseA = solo;
        } else {
          System.out.println("Both fighters use two beads");
          stratUseB = pair;
          stratUseA = pair;
        }
        if (j == 0) {
          System.out.println("FighterA: Black, FighterB: Black");
          coloruseA = FighterBlack;
          coloruseB = FighterBlack;
        } else if (j == 1) {
          System.out.println("FighterA: Black, FighterB: Blue");
          coloruseA = FighterBlack;
          coloruseB = FighterBlue;
        } else if (j == 2) {
          System.out.println("FighterA: Black, FighterB: Green");
          coloruseA = FighterBlack;
          coloruseB = FighterGreen;
        } else if (j == 3) {
          System.out.println("FighterA: Blue, FighterB: Blue");
          coloruseA = FighterBlue;
          coloruseB = FighterBlue;
        } else if (j == 4) {
          System.out.println("FighterA: Blue, FighterB: Green");
          coloruseA = FighterBlue;
          coloruseB = FighterGreen;
        } else {
          System.out.println("FighterA: Green, FighterB: Green");
          coloruseA = FighterGreen;
          coloruseB = FighterGreen;
        }

        fighterA = new Pool(coloruseA, stratUseA);
        fighterB = new Pool(coloruseB, stratUseB);
        battle.combat(fighterA, fighterB);
        System.out.println("\n\n");
      }

    }
  }
}