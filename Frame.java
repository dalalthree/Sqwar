
/**
 * Author: Chris Yang
 * Purpose: Creates the window
 */

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame
{
    //sets up the window and the gui

    
    public static String title = "Sqwar";
    public static Dimension size = new Dimension(800, 600);
    
    public Frame()
    {
        setTitle(title);
        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               
        display();
    }
    
    public void display()
    {
        setLayout(new GridLayout(1, 1, 0, 0));
        
        Screen screen = new Screen(this);
        add(screen);
        
        setVisible(true);
    }
    
    public static void main(String[] args)
    {
        new Frame();
    }
}
