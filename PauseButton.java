import java.awt.*;

public class PauseButton extends Rectangle{

    //sets the cordinates of the paused button to the given values
    public PauseButton(int x, int y, int width, int height)
    {
        setBounds(x, y, width, height);
    }

    //checks for clicks
    public void checkClick()
    {
        //checks if the pause icon was clicked
        if (this.contains(Screen.mse) && !Screen.paused)
        {
            //pauses
            Screen.paused = true;
        }
    }

    //draws the paused menu or button
    public void draw(Graphics g)
    {
        //if the game is not paused draws the paused icon
    	if(!Screen.paused)
        {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(x, y, width / 2 - 5, height);
            g.fillRect(x + width/2 + 5, y, width / 2 - 5, height);
        }
    }
}
