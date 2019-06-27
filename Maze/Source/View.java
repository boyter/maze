import javax.swing.*;
import java.awt.*;

public class View extends JFrame
{
	
	
	GUIcanvas guican;
	Maze maz;
	Control stc;
	DrawMaze dr;
	

	public View()
	{
		Container pane = getContentPane();
		pane.setLayout( new BorderLayout() );
		
		
		
		
		dr = new DrawMaze();
		
		
		getContentPane().add( dr, BorderLayout.CENTER);
		

		
		setSize( 424, 544 );
   	}
	
	public void setModel(Maze maz)
	{
		this.maz = maz;
		dr.addMaze(maz.getMaze());
	}
	
	public void setCont(Control stc)
	{
		this.stc = stc;
		this.addWindowListener( stc );
	}
	
	public void update()
	{
		dr.addMaze(maz.getMaze());
		getContentPane().add( dr, BorderLayout.CENTER);
	}
	
	public void generate()
	{
	}
	
	public void solve()
	{
	}		
	
}