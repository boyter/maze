/**
 * Maze Class.
 * Holds and generates a maze randomly when appropiate methods are called.
 * @author Ben Boyter
 */
public class Maze
{
	private int mazerepresented[][]; //used to represent the maze
	private DisjointSets ds; 		 // used for maze generation
	
	private final int MAZE_WALL = 0;
	private final int MAZE_GAP 	= 1;
	private final int ENTRY 	= 3;
	private final int EXIT 		= 4;
	private final int MARKED	= 5;

	private final int NORTH		= 0;
	private final int EAST		= 1;
	private final int SOUTH		= 2;
	private final int WEST		= 3;
	
	private int xsize;
	private int ysize;
	
	/**
	 * Default No-Argument Constructor.
	 */
	public Maze()
	{
		ds = null;
		mazerepresented = null;
		xsize = 0;
		ysize = 0;
	}
	
	/**
	 * Construct the maze according to the sizes given.
	 * @param xsize The X width of the maze to be generated.
	 * @param ysize The Y height of the maze to be generated.
	 */
	public Maze(int xsize, int ysize)
	{
		this();
		setMazeSize(xsize, ysize);
		generateMaze();
	}
	
	/**
	 * Constructs the maze if not already constructed.
	 * @param xsize The X width of the maze to be generated. Must not be less then 3.
	 * @param ysize The Y height of the maze to be generated. Must not be less then 3.
	 */
	private void setMazeSize(int xsize, int ysize)
	{
		this.xsize = xsize;
		this.ysize = ysize;
		
		if(xsize < 3 || ysize < 3) //if the size given is somthing like 0 or minus fix it
		{
			this.xsize = 3;
			this.ysize = 3;
		}
		
		if(mazerepresented == null)
			mazerepresented = new int[(this.xsize * 2) + 1][(this.ysize * 2) + 1];
			// * 2 + 1 because the maze uses 3x3 to represent a room by using
			// the internal part as the room and the external as walls
	}
	
	/**
	 * Generates the maze. First builds the maze with empty rooms in a 
	 * multidirectional array. Then randomly removes walls to create a maze.
	 * The entry is always located in the top left hand corner and the exit
	 * is always located on the bottom right. 
	 */
	public void generateMaze()
	{
		if(mazerepresented != null)
		{
			
			makeMazeBlank();  // make the maze all walls
			setUpMazeRooms(); // add rooms into the maze			
		      	  
		    // Add the exit points of the maze.
		    mazerepresented[(mazerepresented.length - 2)][(mazerepresented[0].length - 2)] = EXIT;
			
			//long time1 = 0; // Used to measure the speed of the diffrent solutions
			//time1 = System.currentTimeMillis();
			randomizeMazeFast();
			//randomizeMazeSlow();
			//System.out.println("Generation Time - " + (System.currentTimeMillis() - time1));
						
			solveMaze(1,1);
			
		    mazerepresented[0 + 1][0 + 1] = ENTRY;
		
		}
	}
	
	
	/**
	 *Recursive method that solves the maze and marks its path.
	 *@param x The x position to start the solution from
	 *@param y The y position to start the solution from
	 *@return Boolean value of True if the maze is solved, or false if it is unsolvable
	 */
	private boolean solveMaze(int x, int y)
	{
		if(mazerepresented[x][y] == EXIT)
			return true;
		if(mazerepresented[x][y] == MARKED)
			return false;
		
		mazerepresented[x][y] = MARKED;
		
		for(int i=0;i<4;i++)
		{
			if(!wallExists(x,y,i))
			{
				if(i == NORTH)
					if(solveMaze(x,(y -1)))
						return true;
				if(i == EAST)
					if(solveMaze((x+1),y))
						return true;
				if(i == SOUTH)
					if(solveMaze(x,(y+1)))
						return true;
				if(i == WEST)
					if(solveMaze((x-1),y))
						return true;
			}
		}
		
		mazerepresented[x][y] = MAZE_GAP;
		return(false);
			
	}
	
	/**
	 *Determines if a wall is in next to the cordinates given and the
	 *direction given.
	 *@param x The x position of the room
	 *@param y The y position of the room
	 *@param direction The direction to look
	 *@return True if a wall exists in the direction, otherwise false
	 */
	private boolean wallExists(int x,int y,int direction)
	{
		if(direction == NORTH)
			if(mazerepresented[x][y-1] == MAZE_WALL)
				return true;
		if(direction == EAST)
			if(mazerepresented[x+1][y] == MAZE_WALL)
				return true;
		if(direction == SOUTH)
			if(mazerepresented[x][y+1] == MAZE_WALL)
				return true;
		if(direction == WEST)
			if(mazerepresented[x-1][y] == MAZE_WALL)
				return true;
			
		return false;
	}
	
	/**
	 *Makes the multidimensional arry full of 0's, hence making the 
	 *maze blank.
	 */
	private void makeMazeBlank()
	{
		// Makes the representation of the entire maze into walls
		for(int i = 0; i < mazerepresented.length; i++)
		  for(int j = 0; j < mazerepresented[0].length; j++)
		   	mazerepresented[i][j] = MAZE_WALL;
	}
	
	/**
	 *Adds spaces in between walls of the maze.
	 */
	private void setUpMazeRooms()
	{
		// Loops through the maze and for every even number in the array
		// for both transversals converts it to a room
		for(int i = 0; i < mazerepresented.length; i++)
		  for(int j = 0; j < mazerepresented[0].length; j++)
		   	if(i%2 != 0 && j%2 != 0)
		   	  mazerepresented[i][j] = MAZE_GAP;
	}
	
	/**
	 * Randomly removes walls in the maze. This method places the walls into an array
	 * which is then randomized. These walls are then removed if the rooms next to them
	 * are not in the same set.
	 */
	private void randomizeMazeFast()
	{
		ds = new DisjointSets(this.xsize * this.ysize);
		
		wallStruct[] wallSt = new wallStruct[((xsize - 1)*ysize)+(xsize * (ysize -1))]; // holds walls
		//read a walls position and put in the array in new object
		
		int count = 0;  // used to count the total amount of objects
		int ycount = 0; // counts down the maze
		Random rand = new Random(); // used to permute the array
		
		while(count != wallSt.length)
		{
			
			//System.out.println("YCount - " + ycount + " - " +(ycount %2));
			if(ycount % 2 == 0 ) //if even
			{
				for(int i = 0;i<(xsize -1);i++)
				{
					//System.out.println("EVEN - "+(i * xsize) + " " + count + " " + ycount);
					wallSt[count] = new wallStruct((i+1)*2,(ycount+1));
					count++;
				}
			}
			else
			{
				for(int i = 0;i<xsize;i++)
				{
					//System.out.println("ODD - " + i + " " + count + " " + ycount);
					wallSt[count] = new wallStruct(((i+1)*2)-1,(ycount+1));
					count++;
				}
			}
			ycount++;
		}
		
		// randomize the array
		rand.permute(wallSt);

		// read over the array
		// for each wall check if the rooms on either side are of the same set
		// if yes, ignore and get next wall in the array
		// if not union them and change the wall to a space

		for(int i=0;i<wallSt.length;i++)
		{
			if(mazerepresented[wallSt[i].getX()][wallSt[i].getY() -1] == MAZE_GAP) //north and south
			{
				//if rooms not in the same set
				//union them and change the current wall to a gap
				if(!ds.sameSet(convertXYtoRoom(wallSt[i].getX(),(wallSt[i].getY() -1)),convertXYtoRoom(wallSt[i].getX(),(wallSt[i].getY() +1))))
				{
					ds.union(ds.find(convertXYtoRoom(wallSt[i].getX(),(wallSt[i].getY() -1))),ds.find(convertXYtoRoom(wallSt[i].getX(),(wallSt[i].getY() +1))));
					mazerepresented[wallSt[i].getX()][wallSt[i].getY()] = MAZE_GAP;
				}				
			}
			else // they are east and west
			{
				//if rooms not in the same set
				//union them and change the current wall to a gap
				if(!ds.sameSet(convertXYtoRoom((wallSt[i].getX() -1),wallSt[i].getY()),convertXYtoRoom((wallSt[i].getX() +1), wallSt[i].getY())))
				{
					ds.union(ds.find(convertXYtoRoom((wallSt[i].getX() -1),wallSt[i].getY())),ds.find(convertXYtoRoom((wallSt[i].getX() +1), wallSt[i].getY())));
					mazerepresented[wallSt[i].getX()][wallSt[i].getY()] = MAZE_GAP;
				}
			}
			
		}
						
	
	}
	
	/**
	 * Randomly removes walls in the maze. This method randomly selects rooms and directions
	 * until it finds a place to remove the wall.
	 */
	private void randomizeMazeSlow()
	{
		ds = new DisjointSets(this.xsize * this.ysize);
			
		Random rand = new Random();
		int randomroom = 0;
		int ydirection = 0;
		int xdirection = 0;
		int direction  = 0;
	
			
		while(!ds.allSameSet()) //see if all sets are merged
		{
			
			randomroom = rand.nextInt(ds.getSet().length); //randomly select a room
			
			// converts the position of room in the array into where it is in the
			// multidimensional array
			ydirection = randomroom / xsize;
			xdirection = randomroom - (ydirection * xsize);
			xdirection = (xdirection * 2) + 1;
			ydirection = (ydirection * 2) + 1;
			
								
			direction = rand.nextInt(4); //select a random direction
			
			
			if(direction == NORTH && ydirection != 1)
			{
				if(!ds.sameSet(randomroom - xsize, randomroom ))
				{
					mazerepresented[xdirection][ydirection - 1] = MAZE_GAP;
					ds.union(ds.find(randomroom - xsize), ds.find(randomroom));
				}
			}
			if(direction == EAST && xdirection != xsize * 2 -1)
			{
				if(!ds.sameSet(randomroom + 1, randomroom ))
				{
					mazerepresented[xdirection + 1][ydirection] = MAZE_GAP;
					ds.union(ds.find(randomroom +1), ds.find(randomroom));
				}
			}
			if(direction == SOUTH && ydirection != ysize * 2 -1)
			{
				if(!ds.sameSet(randomroom + xsize, randomroom ))
				{
					mazerepresented[xdirection][ydirection + 1] = MAZE_GAP;
					ds.union(ds.find(randomroom + xsize), ds.find(randomroom));
				}
			}
			if(direction == WEST && xdirection != 1)
			{
				if(!ds.sameSet(randomroom - 1, randomroom ))
				{
					mazerepresented[xdirection - 1][ydirection] = MAZE_GAP;
					ds.union(ds.find(randomroom -1), ds.find(randomroom));
				}
			}
		
		}

	}

	
	/**
	 * Converts the XY position of a room into its room
	 * position in the disjoint sets class.
	 * @param x The X position
	 * @param y The Y position
	 * @return The room number
	 */
	private int convertXYtoRoom(int x, int y)
	{
		return ((x/2) + (((y-1)/2)*xsize));
	}
	
	
	/**
	 * Debug method. Display's the maze to stdio.
	 * Simply iterates over the maze array and prints it.
	 */
	public void printMaze()
	{
		System.out.println("Xsize - " + xsize);
		System.out.println("Ysize - " + ysize);
		
		if(mazerepresented != null)
		{
			for(int i = 0; i < mazerepresented[0].length; i++)
			{
				System.out.println("");
			
		  		for(int j = 0; j < mazerepresented.length; j++)
		    		System.out.print( mazerepresented[j][i]);
			}
		}
	}
	
	/**
	 * Simple getter method to return the maze.
	 * @return The maze created, or null if the maze has not been created.
	 */
	public int[][] getMaze()
	{
		return mazerepresented;
	}
		
	/**
	 * Wall Struct.
	 * Used to hold Wall Values
	 * @author Ben Boyter
	 */
	public class wallStruct 
	{
		private int x,y;
		
		public wallStruct()
		{
			x = 0;
			y = 0;
		}
		
		public wallStruct(int x, int y)
		{
			this();
			this.x = x;
			this.y = y;
		}
		
		public int getX()
		{
			return x;
		}
		
		public int getY()
		{
			return y;
		}
		
		public String toString()
		{
			return "X - " + x + " Y - " + y;
		}
	}
	
	
}

