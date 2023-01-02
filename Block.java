
/**
 * Author: Chris Yang
 * 
 */
 
import java.awt.*;

//a tile on the screen
public class Block extends Rectangle
{

    //sets the rectangle of this block to the given values
    public Block(int x, int y, int width, int height)
    {
        setBounds(x, y, width, height);
    }
    
    //draws the block
    public void draw(Graphics g)
    {
        g.drawImage(Screen.groundBlock[0], x, y, width, height, null);        
    }
}
