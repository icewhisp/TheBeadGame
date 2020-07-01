
/**
 * Simulates a fight between two pools
 * 
 * @author Joshua 
 * @version 1
 */
public class Fight
{
    private int DICE_FACE = 6;
    public void combat(Pool fighterFirst, Pool fighterSecond)
    {


        
        int aWin = 0;
        int bWin = 0;
        int tie = 0;
        for (int y= 0; y <100000; y++){
            boolean aliveA = true;
            boolean aliveB = true;
            Pool fighterA = fighterFirst;
            Pool fighterB = fighterSecond;
            while (aliveA && aliveB) //let the game begin
            {
                if (fighterA.getSpeed() > fighterB.getSpeed())
                {
                    int attackA = fighterA.attack();
                    aliveB = fighterB.takeHit(attackA);
                    int attackB = fighterB.attack();
                    aliveA = fighterA.takeHit(attackB);
                }
                else if (fighterA.getSpeed() == fighterB.getSpeed())
                {
 
                    int attackA = fighterA.attack();
                    int attackB = fighterB.attack();

                    aliveB = fighterB.takeHit(attackA);
                    aliveA = fighterA.takeHit(attackB);
                }
                else
                {
                    int attackB = fighterB.attack();
                    aliveA = fighterA.takeHit(attackB);
                    int attackA = fighterA.attack();
                    aliveB = fighterB.takeHit(attackA);
                }

                fighterB.advanceTurn();
                fighterA.advanceTurn();
                if ((fighterB.checkForActiveBeads() == false && fighterA.checkForActiveBeads() == false)|| (fighterB.turn > 6 && fighterA.turn >6 ))
                {
                    fighterA.endTurn();
                    fighterB.endTurn();
                }
            }
            if (fighterA.checkLiving())
            {
                aWin ++;
            }
            else if (fighterB.checkLiving())
            {
                bWin ++;
            }
            else
            {
                tie ++;
            }
            fighterA.endBattle();
            fighterB.endBattle();
        }
        System.out.println("Fighter A win Rate: "+ (float)(aWin/100000.000)*100 + "%" );
        System.out.println("Fighter B win Rate: "+ (float)(bWin/100000.000)*100 + "%" );
        System.out.println("Tie Rate Rate: "+ (float)(tie/100000.000)*100+ "%" ) ;
    }
}


