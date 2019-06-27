import javax.swing.*;
import java.awt.*;

public class GUIcanvas extends JPanel
{
	public GUIcanvas()
	{
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.fillRect(1,1,50,50);
	}
}