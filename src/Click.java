import java.awt.Point;

public class Click {
	private Point loc;
	private boolean cl = false;	
	
	public Click(Point l)
	{
		loc = l;
	}
	
	public Point getLoc() {
		return loc;
	}
	
	public int getX()
	{
		return (int)loc.getX();
	}
	
	public int getY()
	{
		return (int)loc.getY();
	}

	public void setClick(boolean c)
	{
		cl = c;
	}
	
	public Boolean click() {
		return cl;
	}	
}
