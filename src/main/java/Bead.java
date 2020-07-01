
/**
 * The bead class is used to represent the different stats and pools a character has
 * 
 * @author Joshua Wills
 * @version 1
 */
public class Bead
{
    // instance variables
    private String color; //What of the four colors a bead should be black,white,blue,or green
    private int use;  //What round the A.I. will attempt to use the bead to perform an action
    private int state = 0; //0 = in pool, 1 = spent, 2 = exhausted, 3 = wounded

    /**
     * Constructor for objects of class Bead
     */
    public Bead(String color, int use)
    {
        // initialise instance variables
        this.color = color.toLowerCase();
        this.use = use;
    }
    public void takeDamage() //Used when a bead takes damage
    {
        state += 1;
    }
     /**Used when you need to check if a bead can be used, both for action damage 
      * when going second and for use when choosing what beads you want to use
      */ public boolean isUseable()
    {
        if (state == 0)
        {
            return true;
        }
        return false;
    }
    public void refresh() //If a bead is just spent then it returns to the in pool state
    {
        if (state == 1)
        {
            state = 0;
        }
    }
    public boolean inHand(int round) //Checks to see if a bead if used this roud
    {
        if(round == use && state == 0)
        {
            return true;
        }
        return false;
    }
    public int getEffect() //1 = black bead, 2 = blue bead, 3 = green bead, anything else returns 4
    {
        if (color.equals("black"))
        {
            return 1;
        }
        else if (color.equals("blue"))
        {
            return 2;
        }
        else if (color.equals("green"))
        {
            return 3;
        }
        return 4;
    }
    public int getState()
    {
        return state;
    }
    public void setState(int num)
    {
        state = num;
    }
}
