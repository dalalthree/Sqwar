
/**
 * Write a description of class KeyHandle here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.event.*;
import java.awt.*;

public class KeyHandle implements MouseMotionListener, MouseListener //gives mouse position
{
    //checks for a mouse click
    public void mouseClicked(MouseEvent e)
    {
        //checks if the screen not paused
        if(!Screen.paused)
            //tells the store to check if it needs to place or select a new tower
            Screen.store.checkSelection();
        //checks the paused button if it needs to paused or unpause
        Screen.pauseButton.checkClick();
        Screen.menu.checkClick();
    }
    
    public void mouseEntered(MouseEvent e)
    {
        
    }
    
    public void mouseExited(MouseEvent e)
    {
        
    }
    
    public void mousePressed(MouseEvent e)
    {
    
    }
    
    public void mouseReleased(MouseEvent e)
    {
        
    }

    //updates the position of the mouse when it is moved
    public void mouseDragged(MouseEvent e)
    {
        Screen.mse = new Point((e.getX()) + ((Frame.size.width - Screen.myWidth)/2), (e.getY()) + ((Frame.size.height - (Screen.myHeight)) - (Frame.size.width - Screen.myWidth)/2));
    }
    
    public void mouseMoved(MouseEvent e)
    {
        Screen.mse = new Point((e.getX()) - ((Frame.size.width - Screen.myWidth)/2), (e.getY()) - ((Frame.size.height - (Screen.myHeight)) - (Frame.size.width - Screen.myWidth)/2));
    }
}
