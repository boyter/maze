import javax.swing.*;
import java.awt.*;

public class DrawMaze extends JPanel
{
	int[][] maze = null;
	
	private final int MAZE_WALL = 0;
	private final int MAZE_GAP 	= 1;
	private final int ENTRY 	= 3;
	private final int EXIT 		= 4;
	private final int MARKED	= 5;
	private int mazeSize 		= 5;
	
	public void paintComponent(Graphics g) 
    {
		super.paintComponent(g);
		
   		if(maze != null)
		{
			for(int i = 0; i < maze.length; i++)
			{
				for(int j = 0; j < maze[0].length; j++)
				{
					if(maze[i][j] == MAZE_WALL)
		    			g.setColor(new Color(50,50,50));
		    		if(maze[i][j] == MAZE_GAP)
		    			g.setColor(new Color(255,255,255));
		    		if(maze[i][j] == ENTRY)
		    			g.setColor(new Color(0,255,0));
		    		if(maze[i][j] == EXIT)
		    			g.setColor(new Color(255,0,0));
		    		if(maze[i][j] == MARKED)
		    			g.setColor(new Color(255,255,0));

		    		
		    		g.fillRect(i * mazeSize + 5,j * mazeSize + 5,mazeSize,mazeSize);
		    	}
			}
		}
        
    }
   
	public void addMaze(int[][] maze)
	{
		this.maze = maze;
	}
}
