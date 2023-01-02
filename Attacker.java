
/**
 * Author: Chris Yang
 */

import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;

public class Attacker extends Rectangle
{
    //defaults a new attackers health to 100
    private int health = 100; 
    //keeps track of the original health to show what percetnage of the health is left
    private int startingHealth;
    
    //defaults the damage to 50
    private int damage = 50;

    //defaults the speed to .5
    private float speed = .5f;

    /*
    Since rectangle uses integers and speed is .5,
    when moving changes actX by speed and then sets the rectangles x to (int)actX
    */
    private float actX = 0f;

    //defaults the attacker to basic type
    private int type = 4;

    //destorying is used to keep track of which tower its attacking
    private Tower destroying;
    //the current tick of the attackers eat cycle
    private int eatTick = 0;
    //the number of ticks before doing damage
    private int eatWait = 60;

    /*
    Randomly assigns a type to the attacker and sets up the attacker
    place is used to keep track of how far back the attacker should spawn
        this prevents the attackers from all spawning on the same line and makes rounds longer
    */
    public Attacker(int place)
    {
        //sets up the rectangle of the attacker 
        //the x is off the screen by 300 pixils plus a random additional amount based on the the number this attacker is in the wave
        //the y is randomly chosen between the 7 rows
        //the width and height are the default tile size for the game
        setBounds(-(int)(Math.random()*30 + 20)*place - 300, Screen.tileSize*(int/*7*/)(Math.random()*7), Screen.tileSize, Screen.tileSize);
        //sets the actual x cordinate to the current x cordinate
        actX = x;
        //randomly generates a number to choose the type of attacker
		int r = (int)(Math.random()*4);
        //sets up the attacker based on its type
		if (r == 0)
		{
			this.speed = 1;
		}
		else if(r == 1)
		{
			this.health = 250;
		}
		else if(r == 2)
		{
			this.damage = 100;
			this.speed = 1;
		}
        //sets this attackers type to the randomly generated number
		type = r;
        //increases the health by 25 for every round
		health = health + Screen.round * 25;
        //increases the speed in later rounds
        this.speed = this.speed + (int)((Screen.round-3) / 5);
        //sets the original health of the attacker variable to the current health
        startingHealth = health;
    }
    /*
    Creates a attacker based on the type provided
    place is used to keep track of how far back the attacker should spawn
        this prevents the attackers from all spawning on the same line and makes rounds longer
    */
    public Attacker(int r, int place)
    {
        //sets up the rectangle for the attacker
        //the x is 100 pixils of the screen plus additional pixils based on the number this attacker is in the wave
        //y is randomly one of the seven rows
        //widht and height are the games default square size
        setBounds(-(20*place) - 100, Screen.tileSize*(int/*7*/)(Math.random()*7), Screen.tileSize, Screen.tileSize);
        //stes the actual x value to the current x
        actX = x;
        //sets up the attacker based on the type provided
		if (r == 0)
		{
			this.speed = 1;
		}
		else if(r == 1)
		{
			this.health = 250;
		}
		else if(r == 2)
		{
			this.damage = 100;
			this.speed = 1;
		}
        //sets the zombies type to the type provided
		type = r;
        //increases the health by 1.75 for every round
		health = health + (int)(Screen.round * 1.75);
        //sets the starting health of the zombie to the current health
        startingHealth = health;
    }

    //reduces the attackers health by the amount given
    //and checks if its dead
    public void takeDamage(int d)
    {
        //reduces the health by the amount given
        this.health -= d;
        //checks if attackers dead
        if (this.health <= 0)
        {
            try {
                // Open an audio input stream.
                File soundFile = new File(Value.audio_adeath);
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
            //removes this attacker from the attackers list
            Screen.attackers.remove(this);
            //gives money to the player based on the type of zombie
            if (type == 4)
            {
                Screen.cash += .5;
            }
            else if(type == 1 || type == 2)
            {
                Screen.cash += 1.5;
            }
            else
            {
                Screen.cash += .75;
            }
        }
    }
    
    //moves the attacker or attacks
    public boolean physic()
    {
        //checks if it is currently within range of a tower
        if (checkCollision())
        {
            //checks if it is time to attack
            if(eatTick == eatWait)
            {
                try {
                    // Open an audio input stream.
                    File soundFile = new File(Value.audio_punch);
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
                //causes damage to the tower it is within range of
                destroying.takeDamage(damage);
                //resets the counter for attacking
                eatTick = 0;
            }
            else
            {
                //increases the attack counter
                eatTick ++;
            }
        }
        else
        {
            //increases the actual x value by the speed
            actX += this.speed;
            //sets the x of the attacker to the integer version of actx
            x = (int)actX;
            //checks if the attacker is off the screen, therefor causing the player to lose
            if (x >= Screen.myWidth)
            {
                //returns true to tell the game to end
                return true;
            }
        }
        //unless it goes off the screen, it does not end the game
        return false;
    }
    //checks if the attack is in range of a tower
    private boolean checkCollision(){
        //loops through the towers
        for(int i = 0; i < Screen.towers.size(); i ++)
        {
            //gets the current tower in the list
            Tower t = Screen.towers.get(i);
            //checks if the attacekr is in range
            if (this.y == t.y && this.x >= t.x - Screen.tileSize)
            {
                //sets the current tower in range to the tower just checked
                this.destroying = t;
                //returns that it should attack and not move
                return true;
            }
        }
        //sets the current tower in range to nothing
        this.destroying = null;
        //returns that the tower should move and not try to attack
        return false;
    }

    //draws the attacker
    public void draw(Graphics g)
    {       
        //draws a rectangle that is darker if there is a lot of health and lighter if there is left
        g.setColor(new Color(255, 0, 0, (int)((float)health/(float)startingHealth*255)));
        g.fillRect(x - 2, y - 2, width + 4, height + 4);
        //draws the attacker based on its type, and on top of the rectangle so that the rect appears as a border
        g.drawImage(Screen.attacker[type], x, y, width, height, null);
    }
}
