
/**
 * Author: Chris Yang
 * Purpose: loads all levels
 */

import java.awt.*;

public class Room
{
    //the amount of tiles on the x and y axis
    public static final int mapWidth = 12;
    public static final int mapHeight = 7;

    //array list of tiles in this room
    private Block[][] tiles;


    //creates a new room
    public Room()
    {
        //sets the size of the tiles array to the number of tiles that should be in x and y axis
        tiles = new Block[mapWidth][mapHeight];

        //loops through each of the indexes in the tiles array and adds a new block
        for(int x = 0; x < tiles.length; x++)
        {
            for(int y = 0; y < tiles[0].length; y++)
            {               
                if(y == 12)
                {
                    tiles[x][y] = new Block((Screen.myWidth/2) - ((mapWidth*Screen.tileSize)/2) + (x * Screen.tileSize), y * Screen.tileSize, Screen.tileSize, Screen.tileSize);   
                }
                tiles[x][y] = new Block((Screen.myWidth/2) - ((mapWidth*Screen.tileSize)/2) + (x * Screen.tileSize), y * Screen.tileSize, Screen.tileSize, Screen.tileSize);   
            }
        }
    }

    //returns the list of tiles in this room
    public Block[][] getTiles()
    {
        return this.tiles;
    }

    //draws all of the tiles in this room
    public void draw(Graphics g)
    {
        for(int x = 0; x < tiles.length; x++)
        {
            for(int y = 0; y < tiles[0].length; y++)
            {
                tiles[x][y].draw(g);
            }
        }
    }
}
