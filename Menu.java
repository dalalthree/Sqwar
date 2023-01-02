import java.awt.*;

public class Menu{

    private Rectangle playButton;
    private Rectangle helpButton;
    private Rectangle quitButton;


    //sets the cordinates of the paused button to the given values
    public Menu()
    {
        playButton = new Rectangle((Screen.myWidth / 2) - 60, 150, 100, 50);
        helpButton = new Rectangle((Screen.myWidth / 2) - 60, 250, 100, 50);
        quitButton = new Rectangle((Screen.myWidth / 2) - 60, 350, 100, 50);
    }

    //checks for clicks
    public void checkClick()
    {
        if(Screen.paused)
        {
            if(Screen.help)
            {
                Screen.help = false;
            }
            else if(playButton.contains(Screen.mse))
            {
                Screen.paused = false;
            }
            else if(helpButton.contains(Screen.mse))
            {
                Screen.help = true;
            }
            else if(quitButton.contains(Screen.mse))
            {
                System.exit(0);
            }
        }
    }

    //draws the paused menu or button
    public void draw(Graphics g)
    {
        //if the game is not paused draws the paused icon
        //otherwise draws the paused menu with the intrusctions
        if(Screen.help)
        {
            g.setColor(new Color(100, 100, 100));
            g.fillRect(0, 0, Screen.myWidth, Screen.myHeight);
            g.setColor(new Color(0, 0, 0));
            
            g.setColor(new Color(80, 150, 80));
            g.setFont(new Font("Courrier New", Font.BOLD, 20));

            g.drawString("The goal of the game is to kill all the attackers from all 15 waves", 20, 30);
            g.drawString("If a attacker reaches the right side of the screen you lose", 20, 65);
            g.drawString("Buy towers to shoot at the attackers, see health by the red box around them", 20, 100);
            g.drawString("Select a tower on the bottom and then click in a square on the field to build", 20, 135);
            g.drawString("Use the X to sell towers for half the price you bought it", 20, 170);
            g.drawString("For every killed attacker you get an average of 1 coin", 20, 205);
            g.drawString("If a attacker gets close it will destroy the tower", 20, 240);
            g.drawString("Attackers get stronger and faster each round and more spawn each round", 20, 275);
            
            g.setFont(new Font("Courrier New", Font.BOLD, 15));

            g.drawImage(Screen.towerBlock[0], 20, 310, 80, 80, null);
            g.drawString("Health: 100", 110, 330);
            g.drawString("Fires every: 300ms", 110, 360);
            g.drawString("Damage: 34", 110, 390);

            g.drawImage(Screen.towerBlock[1], 250, 310, 80, 80, null);
            g.drawString("Health: 150", 340, 330);
            g.drawString("Fires every: 150ms", 340, 360);
            g.drawString("Damage: 25", 340, 390);

            g.drawImage(Screen.towerBlock[2], 480, 310, 80, 80, null);
            g.drawString("Health: 500", 570, 330);
            g.drawString("Fires every: 3000ms", 570, 360);
            g.drawString("Damage: 250", 570, 390);

            g.setFont(new Font("Courrier New", Font.BOLD, 20));
            g.drawString("A purple headband on a attacker means he's faster", 20, 435);
            g.drawString("A black belt on a attacker means he does more damage", 20, 470);
            g.drawString("A blue shield means the attacker has more health", 20, 505);
            
            g.setFont(new Font("Courrier New", Font.BOLD, 15));
            g.drawString("Click anywhere to go back", 20, 540);
        }
        else if(Screen.paused)
        {
            g.setColor(new Color(100, 100, 100));
            g.fillRect(0, 0, Screen.myWidth, Screen.myHeight);
            g.setColor(new Color(0, 0, 0));
            
            Graphics2D g2d = (Graphics2D) g;
            

            Font fnt0 = new Font("Courrier New", Font.BOLD, 50);
            g.setFont(fnt0);
            g.setColor(Color.WHITE);
            g.drawString("Sqwar", (Screen.myWidth / 2) - 90, 100);
            Font fnt1 = new Font("Courrier New", Font.BOLD, 30);
            g.setFont(fnt1);
            g.drawString("Play", playButton.x + 19, playButton.y + 35);
            g2d.draw(playButton);
            g.drawString("Help", helpButton.x + 17, helpButton.y + 35);
            g2d.draw(helpButton);
            g.drawString("Quit", quitButton.x + 18, quitButton.y + 35);
            g2d.draw(quitButton);
        }
    }
}
