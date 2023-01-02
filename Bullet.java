
/**
 * Author: Chris Yang
 */

import java.awt.*;

public class Bullet extends Rectangle
{
    //the amount of damage this bullet does
    private int damage;
    private boolean penetration;

    public Bullet(int x, int y, int damage, boolean penetration)
    {
        //sets the rectangle and damage of this bullet based on what is given
		    setBounds(x-10, y, Screen.tileSize, Screen.tileSize);
        this.damage = damage;
        this.penetration = penetration;
    }

    //moves the bullet
    public boolean physic() 
    {
        //moves the bullet left 10 pixils
        x -= 10;
        //checks if the bullet is off the scrreen
        if (x <= -40)
        {
            //return that the bullet should be removed
            return true;
        }
        //returns that the bullet is still on the screen
        return false;
    }

    //checks if the bullet hit a attacker
    public boolean checkCollision()
    {
        //loops through the attackers
        for (int i = 0; i < Screen.attackers.size(); i ++)
        {
            //gets the current attacker
            Attacker a = Screen.attackers.get(i);
            //checks for collision
            if (a.y == this.y && a.x >= this.x)
            {
                //causes damage to the attacker
                Screen.attackers.get(i).takeDamage(this.damage);
                //returns that it did damage
                return !this.penetration;
            }
        }
        //returns that it didn't do damage
        return false;
    }

    //draws the bullet
    public void draw(Graphics g)
    {
        g.drawImage(Screen.bullet[0], x, y, width, height, null);
    }
}
