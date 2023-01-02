
import java.awt.*;

public class Store
{
    //the currently selected tower to be placed
	public static int selected = 0;
    //the number of options in the shop
    public static final int shopWidth = 4;
    public static final int cellSpace = 7; //space in between shop cells
    public static final int iconSize = 20; // dimensions of the coin "icon"
    //the coin icons rectngle
    private Rectangle buttonCoins;
    //the buttons in the shop
    private Rectangle[] button;
    //the prices in order, off the options in the shop
    public static final int[] prices = {1, 3, 7};

    
    public Store()
    {
        //creates a new set of buttons in the shop
        button = new Rectangle[shopWidth];
        for(int i = 0; i < button.length; i++)
        {
            button[i] = new Rectangle((Screen.myWidth/2) - ((shopWidth*(Screen.tileSize+cellSpace))/2) + ((Screen.tileSize+cellSpace)*i), (Screen.room.getTiles()[0][Room.mapHeight - 1].y) + Screen.tileSize + 26 , Screen.tileSize, Screen.tileSize);
        }
        
        //creates a new coin icon rectnagle
        buttonCoins = new Rectangle(Screen.room.getTiles()[0][0].x - 1, button[0].y, iconSize, iconSize);
    }
    
    //checks the current selection in the shop
    public void checkSelection()
    {
        //checks if the player clicked a tower in the shop, if so sets the selected tower to that one
    	for(int i = 0; i < this.button.length; i ++)
    	{
    		if(button[i].contains(Screen.mse))
    		{
    			Store.selected = i;
    		}
    	}
        //checks if the user is selected on a  tower and they have enough money to purchase it
    	if (selected < 3 && Screen.cash >= prices[selected])
    	{
            //loops through all the possible places to put a tower
    		for(int i = 0; i < Room.mapWidth; i ++)
	    	{
    			for(int j = 0; j < Room.mapHeight; j ++)
    			{
                    //checks if the user clicked this block
    				if(Screen.room.getTiles()[i][j].contains(Screen.mse))
    				{
                        //keeps track of whether there is already a tower there
                        boolean place = true;
                        //loops through all the towers
    					for(int k = 0; k < Screen.towers.size(); k ++)
	    				{
                            //checks if the tower is in the spot the user clicked
    						if(Screen.towers.get(k).x == Screen.room.getTiles()[i][j].x && Screen.towers.get(k).y == Screen.room.getTiles()[i][j].y)
	    					{
                                //sets placed to false and breaks out of the loop
                                place = false;
    							break;
    						}
    					}
                        //checks whether it should place a tower
                        if(place)
                        {
                            //places the tower and takes the money
                            Screen.towers.add(new Tower(Screen.room.getTiles()[i][j].x, Screen./*7*/room.getTiles()[i][j].y, selected));
                            Screen.cash -= prices[selected];
                        }
    				}
	    		}
    		}
    	}
        //checks whether the user wanted to remove a tower
        else if(selected == 3)
        {
            //loops through all the possible tower places
            for(int i = 0; i < Room.mapWidth; i ++)
	    	{
    			for(int j = 0; j < Room.mapHeight; j ++)
    			{
                    //checks if the current block contains the mouse
    				if(Screen.room.getTiles()[i][j].contains(Screen.mse))
    				{
                        //loops through the towers
                        for(int k = 0; k < Screen.towers.size(); k ++)
	    				{
                            //checks if the user clicked on the current tower
    						if(Screen.towers.get(k).x == Screen.room.getTiles()[i][j].x && Screen.towers.get(k).y == Screen.room.getTiles()[i][j].y)
	    					{
                                //calls the sell method for a tower
                                Screen.towers.get(k).sell();
                                //removes the tower
                                Screen.towers.remove(k);
                                //causes the program to not do the outer loops again and then breaks out of this one
                                i = Room.mapWidth;
                                j = Room.mapHeight;
                                break;
    						}
    					}
                    }
                }
            }
        }
    
    }

    //draws the store
    public void draw(Graphics g)
    {        
        //draws all of the buttons
        for(int i = 0; i < button.length; i++)
        {   
            g.setColor(new Color(255, 255, 255, 255));
        	if(i == selected){
        		g.setColor(new Color(255, 255, 255, 255));
                g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
        	}
            //draws a slighlty shaded background if the user if hovering over a button
            else if(button[i].contains(Screen.mse))
            {
                g.setColor(new Color(255, 255, 255, 100));
                g.fillRect(button[i].x, button[i].y, Screen.tileSize, Screen.tileSize);
                
            }
            //draws the prices
            if(i < 3)
                g.drawString("$"+prices[i], button[i].x + Screen.tileSize / 3, button[i].y + button[i].height + 20);
                //draws the image of the tower
            g.drawImage(Screen.storeBlock[0], button[i].x, button[i].y, button[i].width, button[i].height, null);
            g.drawImage(Screen.towerBlock[i], button[i].x, button[i].y, button[i].width, button[i].height, null);
        }
        
        //draws the users money and the coin icon
        g.drawImage(Screen.coin[0], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null); 
        g.setFont(new Font("Courrier New", Font.BOLD, 14));
        g.setColor(Color.WHITE);
        g.drawString("" + (int)Screen.cash, buttonCoins.x + buttonCoins.width + 6, buttonCoins.y + 15);
    }
}

