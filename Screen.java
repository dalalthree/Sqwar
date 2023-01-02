
/**
 * Author: Chris Yang
 * Purpose: Paint on screen
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.io.File;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable{
    
    //this thread is used for the timing of the game
    public Thread gameLoop = new Thread(this); 
    
    //creates arrays to store all the images being imported to be used in the game
    //even the types of objects where there is only one image uses arrays for expandabilty and uniformness
    public static Image[] groundBlock = new Image[1];
    public static Image[] StoreBlock = new Image[1];
    public static Image[] CoinBlock = new Image[1];
    public static Image[] attacker = new Image[4];
    public static Image[] bullet = new Image[1];
    public static Image[] TowerBlock = new Image[4];

    //store the width and height of the screen
    public static int myWidth, myHeight;
    
    //sets the amount of cash by default to 10
    public static float cash = 5f; 

    //creates a default tile size of 62
    public static final int tileSize = 62;
    
    //variable used to make sure the game is set up before trying to run methods and checks
    public static boolean isFirst = true;
    
    //stores the x and y cords of the mouse
    public static Point mse = new Point(0, 0);
    
    //stores the room store and pause paused button and menu
    public static Room room;   
    public static Store store; 
    public static PauseButton pauseButton;
    public static Menu menu;
    
    //playing is used to make sure the game is not over
    public static boolean playing = true;
    //paused is used when the game isn't running but is still not over
    public static boolean paused = true;
    //lost is used to know that when the game is over whether to say win or lose
    public static boolean lost;
    //help is used to know when the instructions should be displayed
    public static boolean help;
    
    //sets up lists for the attacker towers and bullets
    public static ArrayList<Attacker> attackers = new ArrayList<Attacker>();
    public static ArrayList<Tower> towers = new ArrayList<Tower>();
    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    //the current round number
    public static int round = 0;

    //used to keep track of when the next round should start
    private static int roundTimer = 1850;

    //music sound controller
    private static Clip music;

    //round is 0 and round Timer is a big number to give the user a little bit of time before actually starting the first round

    

    public Screen(Frame frame)
    {
        //adds the mouse movment and click trackers       
        frame.addMouseListener(new KeyHandle());
        frame.addMouseMotionListener(new KeyHandle());
        //starts the thread for timing
        gameLoop.start();

        //imports all of the pictures
        groundBlock[0] = new ImageIcon(Value.image_groundtile).getImage();
        groundBlock[0] = createImage(new FilteredImageSource(groundBlock[0].getSource(), new CropImageFilter(0, 0, 31, 31)));

        StoreBlock[0] = new ImageIcon(Value.image_storepiece).getImage();
        CoinBlock[0] = new ImageIcon(Value.image_coin).getImage();
        bullet[0] = new ImageIcon(Value.image_bullet).getImage();
        
        attacker[0] = new ImageIcon(Value.image_attacker2).getImage();
        attacker[1] = new ImageIcon(Value.image_attacker3).getImage();
        attacker[2] = new ImageIcon(Value.image_attacker4).getImage();
        attacker[3] = new ImageIcon(Value.image_attacker1).getImage();
        TowerBlock[0] = new ImageIcon(Value.image_towerblock1).getImage();
        TowerBlock[1] = new ImageIcon(Value.image_towerblock2).getImage();
        TowerBlock[2] = new ImageIcon(Value.image_towerblock3).getImage();
        TowerBlock[3] = new ImageIcon(Value.image_towerblockX).getImage();
    }

    public void define()
    {
        //sets the round to 0
    	round = 0;
        //sets the starting cash to 10
    	cash = 5;
        //creates a new room and store and menu
        room = new Room();
        store = new Store();
        menu = new Menu();
        //sets playing to true becasue game isn't over
        playing = true;
        //pasued it true to show start menu / insctructions
        paused = true;
        //sets lost to true by default and unless user wins stays true
        lost = true;
        //sets help to false so that the help screen doesnt show up
        help = false;
        //sets the round timer to 1850
        roundTimer = 1850;

        //resets the towers attacker and bullets list
        towers = new ArrayList<Tower>();
        attackers = new ArrayList<Attacker>();
        bullets = new ArrayList<Bullet>();

        //creates a new paused button
        pauseButton = new PauseButton((int)(myWidth/5*4) + 15, (Screen.room.getTiles()[0][Room.mapHeight - 1].y) + tileSize + 36, 30, 40);

        try {
            // Open an audio input stream.
            File soundFile = new File(Value.audio_song);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a sound clip resource.
            music = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            music.open(audioIn);
            music.loop(Clip.LOOP_CONTINUOUSLY);
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
    }
    
    //updates the window and draws everything
    public void paintComponent(Graphics g)
    {        
        //checks if this is the first time through the game
        if(isFirst)
        {
            //sets up the game and calls define method
            myWidth = getWidth();
            myHeight = getHeight();
            define();
            
            isFirst = false;
        }

        //draws a grey background
        g.setColor(new Color(80, 80, 80));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        
        room.draw(g); //Drawing the room
        
        //draws all attackrs towers and bullets
        for(int i = 0; i < attackers.size(); i++)
        {
            attackers.get(i).draw(g);
        }
        for(int i = 0; i < towers.size(); i++)
        {
            towers.get(i).draw(g);
        }
        for(int i = 0; i < bullets.size(); i++)
        {
            bullets.get(i).draw(g);
        }
       
        //displays the round numberr
        g.setFont(new Font("Courrier New", Font.BOLD, 14));
        g.setColor(Color.WHITE);
        g.drawString("Round: " + round, 30, (Screen.room.getTiles()[0][Room.mapHeight - 1].y) + tileSize + 80);
        
        //draws the tower on the mouse if its in the room area of the screen
        if(mse.y < Room.mapHeight * tileSize)
            g.drawImage(Screen.TowerBlock[Store.selected], mse.x - tileSize / 2, mse.y - tileSize / 2, tileSize, tileSize, null); 

        //draws the storee
        store.draw(g);

        //draws the paused button and menu
        pauseButton.draw(g);
        menu.draw(g);

        //draws a win or lost message if the game is over, over the top of evertything else
        if(!playing && !lost)
        {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, myWidth, myHeight);
            g.setFont(new Font("Courrier New", Font.BOLD, 100));
            g.setColor(Color.WHITE);
            g.drawString("Winner", 100, 400);
        }
        if(!playing && lost)
        {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, myWidth, myHeight);
            g.setFont(new Font("Courrier New", Font.BOLD, 100));
            g.setColor(Color.WHITE);
            g.drawString("Loser", 100, 400);
        }

    }
    
    //starts the next round
    public void newRound()
    {
        //increases the current round by 1
        round ++;
        //the first 3 rounds have the same attackers each game and use the more controlled constructor
        if (round == 1)
        {
            attackers.add(new Attacker(3, 0));
            attackers.add(new Attacker(3, 1));
            attackers.add(new Attacker(3, 2));
        }
        else if(round == 2)
        {
            attackers.add(new Attacker(3, 0));
            attackers.add(new Attacker(0, 1));
            attackers.add(new Attacker(3, 2));
            attackers.add(new Attacker(0, 3));
            attackers.add(new Attacker(3, 4));
        }
        else if(round == 3)
        {
            attackers.add(new Attacker(3, 0));
            attackers.add(new Attacker(1, 1));
            attackers.add(new Attacker(2, 2));
        }
        else
        {
            //creates a certain number of attackers using the current round number and uses the random attacker constructor.
            for (int i = 0; i < (round - 3) * 6; i ++)
            {
                attackers.add(new Attacker(i));
            }
        }
        //checks if the user passed round 15
        if (round == 16)//to change number of rounds make this 1 bigger than the wanted number of rounds
        {
            try {
                music.stop();
                // Open an audio input stream.
                File soundFile = new File(Value.audio_win);
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
            //sets playing and false to lost and redraws the window
            playing = false;
            lost = false;
            repaint();
        }
    }

    //the current threads run method
    public void run()
    {
        //loops forever
        while(true)
        {
            //waits 25 ms
            try
            {
                Thread.sleep(25);
            } catch(Exception e) {System.out.println(e);}
            //calls the actions method 
            actions();
        }
    }
    
    //movement and collision detection for everything
    public void actions()
    {
        //checks that it should move things
        if(!isFirst && !paused && playing)
        {
            //checks if it is time for a new round 
            if(roundTimer < 2000)
            {
                //since its not new round time increases the round timer
                roundTimer ++;
            }
            else
            {
                //calls the new round method
                newRound();
                //resets the round timer
                roundTimer = 0;
            }
            //checks if there are no attackeers left in current round
            if (attackers.size() == 0)
            {
                //waits 1 second then starts new round and resets round timer
                try{Thread.sleep(1000);} catch(Exception e) {System.out.println(e);}
                newRound();
                roundTimer = 0;
            }
            //loops through attackers
            for (int i = 0; i < attackers.size(); i ++)
            {
                //calls the move method and checks whether the attacker reached the right side
                if(attackers.get(i).physic())
                {
                    try {
                        music.stop();
                        // Open an audio input stream.
                        File soundFile = new File(Value.audio_end);
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
                    //sets the game to over and lost to true
                    playing = false;
                    lost = true;
                }
            }
            //list of bullets that need to be removed
            //makes sure not removing from list while looping through list
            ArrayList<Bullet> cull = new ArrayList<Bullet>();
            //loops through bullets
            for (int i = 0; i < bullets.size(); i ++)
            {
                //calls the move method for bullets and checks if they off screen
                if (bullets.get(i).physic())
                {
                    //adds the bullet to the removal list
                    cull.add(bullets.get(i));
                }
            }
            

            //loops through the towerrs
            for (int i = 0; i < towers.size(); i ++)
            {
                //makes all the towers shoot
                towers.get(i).shoot();
            }

            //loops through the bullets
            for (int i = 0; i < bullets.size(); i ++)
            {
                //checks for a collision with an attacker
                if(bullets.get(i).checkCollision())
                    //adds the bullet to the removal list
                    cull.add(bullets.get(i));
            }

            //gets rid of all bullets in the removal list
            for (int i = 0; i < cull.size(); i ++)
            {
                bullets.remove(cull.get(i));
            }

            //redraws the screen
            repaint();
        }
        //if the game is currently over
        else if (!playing)
        {
            //waits 5 seconds
            try
            {
                Thread.sleep(5000);
            } catch(Exception e) {System.out.println(e);}
            //starts a new game
            define();
        }
        //redraws the window
        repaint();
    }
}
