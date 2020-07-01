
/**
 * A subsect of fighter designed to simulate the racial boon of a dwarf
 * 
 * @author Joshua
 * @version 
 */
public class Dwarf extends Pool
{
    // Everything will be the same but they have two white "beads"
    private int wCount = 2;


    /**
     * Uses the same constructor
     */
    public Dwarf(String[] colors, int[] plan)
    {
        super(colors,plan);
    }
    public boolean takeHit(int hits)
    {
        int temp = hits;
        boolean alive = true;
        if (wCount >0){
            wCount -= hits;
            if (wCount < 0)
            {
                alive = super.takeHit(Math.abs(wCount));
            }
            else
            {
                return true;
            }
        }
        else
        {
            alive = super.takeHit(Math.abs(hits));
        }
        return alive;
    }
    
}
