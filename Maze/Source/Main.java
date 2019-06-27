public class Main
{
	public static void main(String[]argv)	
	{
		View view 		= new View();
		Control cont 	= new Control();
		Maze maz		= new Maze(40,50);
		
		view.setModel(maz);
		view.setCont(cont);
		cont.setView(view);
		cont.setModel(maz);
				
		view.setVisible( true );
		
	}
}