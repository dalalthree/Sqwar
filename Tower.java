
/**
 * Write a description of class Tower here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;

public class Tower extends Rectangle
{ 
    //health is the curent health of the tower and is 100 by default
    private int health = 100;
    //starting health is used to calculate the percetnage of health the tower has left
    private int startingHealth = 100;
    //tracks the current tick befrore firing
    private int tick = 0;
    //the amount of time befroe firing
    private int wait = 120;
    //the amount of damage the towers bullets do
    private int damage = 34;
    //the type of tower this is
    private int type = 0;
    
    public Tower(int x, int y, int type)
    {
        //sets the rect of this tower
        setBounds(x, y, Screen.tileSize, Screen.tileSize);
        //sets up the tower based on the type
        if (type == 1)
        {
        	health = 150;
        	wait = 60;
        	damage = 25;
        }
        else if(type == 2)
        {
        	health = 500;
        	wait = 200;
        	damage = 250;
        }
        //sets the startign health to this towers current health
        startingHealth = health;
        //sets the instance variable type to the paramater type
        this.type = type;
    }

    public void takeDamage(int d)
    {
        //decreases the health of the tower by the given amount
        this.health -= d;
        //if the health is to low then it removes the tower
        if (this.health <= 0)
        {
            try {
                // Open an audio input stream.
                File soundFile = new File(Value.audio_tdeath);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                // Get a sound clip resource.
                Clip clip = AudioSystem.getClip();
                // Open audio clip and load samples from the audio input stream.
                clip.open(audioIn);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
                System.out.print(e);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
                System.out.print(e);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print(e);
            }
            Screen.towers.remove(this);
        }
    }
    
    //gives the user money when this tower is sold
    public void sell()
    {
        Screen.cash += (float)(Store.prices[type]) / 2;
    }

    //shoots bullets
    public void shoot()
    {
        //checks if its time to shoot
    	if(tick - (int)(Math.random() * 15) > wait)
        {
            //loops through the attackers
            for(int i = 0; i < Screen.attackers.size(); i ++)
            {
                //checks for any attackers in this towers row
                if(Screen.attackers.get(i).y == this.y)
                {
                    try {
                        // Open an audio input stream.
                        File soundFile = new File(Value.audio_shoot);
                        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                        // Get a sound clip resource.
                        Clip clip = AudioSystem.getClip();
                        // Open audio clip and load samples from the audio input stream.
                        clip.open(audioIn);
                        clip.start();
                    } catch (UnsupportedAudioFileException e) {
                        e.printStackTrace();
                        System.out.print(e);
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                        System.out.print(e);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.print(e);
                    }
                    //makes a new bullet a little to the right of this towers cords, and provides the amount of damage
                    Screen.bullets.add(new Bullet(this.x - 10, this.y, this.damage));
                    //resets the timer
                    tick = 0;
                    break;
                }
            }
        }
        //increases the timer by 1
        else
            tick ++;
    }
    
    
    public void draw(Graphics g)
    {
        //draws the tower
    	g.drawImage(Screen.TowerBlock[type], x, y, width, height, null);
        //draws a health bar
        g.setColor(new Color(155, 0, 0));
        g.drawRect(x + width - 10, y, 10, height);
        g.fillRect(x + width - 10, y + height - (int)((float)health/(float)startingHealth * height), 10, (int)((float)health/(float)startingHealth * height));
    }
}
