import java.awt.event.*;

public class Control implements ActionListener ,WindowListener
{	

	View view;
	Maze maz;
		
	public Control()
	{
	}
	
	public void setView( View view )
  	{
    	this.view = view;
  	}
  	
	public void setModel(Maze maz)
	{
		this.maz = maz;
	}
	
	public void actionPerformed( ActionEvent e )
  	{
 	
	}
  	
  	public void windowOpened( WindowEvent we ){}
  	public void windowClosing( WindowEvent we )
    {
   		view.dispose();
    	System.exit(0);
    }
  	public void windowClosed( WindowEvent we ){}
  	public void windowIconified( WindowEvent we ){}
  	public void windowDeiconified( WindowEvent we ){}
  	public void windowActivated( WindowEvent we ){}
  	public void windowDeactivated( WindowEvent we ){}
}