import java.util.Random;
/**
 * This class is a collection of beads and the basic operators you'd perform on them
 * 
 * @author Joshua Wills
 * @version 1
 */
public class Pool
{
    // instance variables - replace the example below with your own
    private Bead[] beadPool;
    public int turn = 1;
    private int DICE_FACE = 6;
    private int TARGET_NUMBER = 3;
    /**
     * Constructor for objects of class Pool
     * beads is used to create the colors and the plan is when to use the beads
     */
    public Pool(String[] colors, int[] plan)
    {
        beadPool = new Bead[colors.length];
        for (int x = 0; x< colors.length; x++)
        {
            beadPool[x] = new Bead(colors[x],plan[x]);
        }

    }

    public boolean advanceTurn() //Increases the turn unless it has no active beads
    {
        if(checkForActiveBeads())
        {
            turn += 1;
            return true;
        }
        return false;
    }

    public boolean checkForActiveBeads() //Returns true if any bead has a state that = 0
    {
        boolean active = false;
        for(Bead x: beadPool)
        {
            if(x.isUseable())
            {
                active = true;
            }
        }
        return active;
    }

    public void endTurn() //refreshs all beads
    {
        for(Bead x: beadPool)
        {
            x.refresh();
        }
        turn = 1;
    }

    public int getSpeed() //returns n where n is the number of green beads
    {
        int speed = 0;
        for(Bead x: beadPool)
        {
            if (x.getEffect() == 3 && x.inHand(turn)) //As a reminder 3 sigifies a green bead
            {
                speed ++;
            }

        }
        return speed;
    }

    public int attack() //Returns the number of hits
    {
        int blackBeads = 0;
        int blueBeads = 0;
        int hits = 0;
        boolean canHit = false;

        for(Bead x: beadPool){
            if (x.inHand(turn)){
                canHit = true;
            }
        }
        if (!canHit){
            return 0;
        }
        for(Bead x: beadPool)
        {
            if (x.getEffect() == 1 && x.inHand(turn)) //As a reminder 1 is a black bead and 2 is a blue beed
            {
                blackBeads += 1;
                x.takeDamage(); //taking a damage moves something from your pool to spent
            }
            else if (x.getEffect() == 2 && x.inHand(turn))
            {
                blueBeads += 1;
                x.takeDamage();
            }
            else if(x.inHand(turn)) 
            {
                x.takeDamage();
            }
        }
        for (int i = 0; i < blackBeads +1; i++)
        {
            int roll = (int)(Math.random()*6)+1 +blueBeads;
            if (roll > TARGET_NUMBER)
            {
                hits +=1;
            }
        }
        return hits;
    }

    public boolean takeHit(int hits) //Checks to see things that are in hand first then take damage assuming moving from spent to exhausted, then from exhausted to wounded, and finally from pool to exhausted
    {
        int[][] prio = new int[beadPool.length][2] ; //The main priority then the random weight
        for(int x = 0; x <hits; x++)
        {
            Bead currentHit;
            int[][] currentPrio = new int[1][2];
            for(int j = 0; j <beadPool.length;j++) //creating the array of priorities
            {

                if(beadPool[j].inHand(turn))
                {
                    prio[j][0] = 0;
                    prio[j][1] = (int)(Math.random()*10000);
                }
                else if(beadPool[j].getState() == 2)
                {
                    prio[j][0] = 1;
                    prio[j][1] = (int)(Math.random()*10000);
                }
                else if(beadPool[j].getState() == 1)
                {
                    prio[j][0] = 2;
                    prio[j][1] = (int)(Math.random()*10000);
                }
                else if(beadPool[j].getState() == 0)
                {
                    prio[j][0] = 3;
                    prio[j][1] = (int)(Math.random()*10000);
                }
                else
                {
                    prio[j][0] = 4;
                    prio[j][1] = (int)(Math.random()*10000);
                }

            }

            currentHit = beadPool[0];
            currentPrio[0][0] = prio[0][0];
            currentPrio[0][1] = prio[0][1];
            for(int j = 1; j <beadPool.length;j++){ //figuiring out what bead to take the hit

                if(prio[j][0] < currentPrio[0][0])
                {
                    currentHit = beadPool[j];
                    currentPrio[0][0] = prio[j][0];
                    currentPrio[0][1] = prio[j][1];
                }
                //if normal prio is equal then check random prio
                else if(prio[j][0] == currentPrio[0][0] && prio[j][1] < currentPrio[0][1])
                {
                    currentHit = beadPool[j];
                    currentPrio[0][0] = prio[j][0];
                    currentPrio[0][1] = prio[j][1];
                }

            }
            if(currentHit.getState() == 0) //if it's in your hand it needs to get hit "twice"
            {
                currentHit.takeDamage();
                currentHit.takeDamage();
            }
            else
            {
                currentHit.takeDamage();
            }
        }
        return checkLiving();
    }

    public boolean checkLiving() //checks to see a bead has a state that is either 0 or 1
    {
        boolean state = false;
        for(Bead x: beadPool){
            if (x.getState() < 2)
            {
                state = true;
            }
        }
        return state;
    }
    public void endBattle()
    {
        turn = 1;
         for(Bead x: beadPool){
             x.setState(0);
             
            }
    }
}
