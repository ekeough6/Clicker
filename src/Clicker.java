import java.awt.*; 
import java.awt.event.*;
import java.util.*;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.*;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class Clicker implements NativeKeyListener, NativeMouseListener{
    public static boolean on = false;
    public static boolean track = false;
    public static int times = 0;
    public static int time = 0;
    public static int count = 0;
    public static ArrayList<Click> locations = new ArrayList<Click>();
    public static Robot bot;
    public static int X = 200, Y = 350;
    public static int delay = 8;
    public static ClickerGUI gui;
    public static Timer timer;
    public static PrintWriter writer;
    public static void main(String[] args) {
    	gui = new ClickerGUI();
    	gui.setVisible(true);
    	//String filename = "track"+(int)Math.random()*100+".txt";
    	//writer = new PrintWriter(filename, "UTF-8");
    	
    	try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
 		logger.setLevel(Level.OFF);
    	
    	GlobalScreen.addNativeKeyListener(new Clicker());
    	GlobalScreen.addNativeMouseListener(new Clicker());
    	try
		{
	    	bot = new Robot();
		}
    	
    	catch (AWTException a)
	    {
	    	a.printStackTrace();
	    }
    	startTimer();
    }
    
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_SPACE)
        {
            on = !on;
        }
        
        else if(e.getKeyCode() == NativeKeyEvent.VC_ESCAPE)
        {
        	System.exit(0);
        }
        
        else if(e.getKeyCode() == NativeKeyEvent.VC_ENTER)
        {
        	X = MouseInfo.getPointerInfo().getLocation().x;
        	Y = MouseInfo.getPointerInfo().getLocation().y;
        }
        else if(e.getKeyCode() == NativeKeyEvent.VC_BACK_SLASH)
        {
        	gui.setVisible(true);
        }
        
        else if(e.getKeyCode() == NativeKeyEvent.VC_T)
        {
        	track = !track;
        	if(track)
        		while(locations.size()>0)
        			locations.remove(0);
        }
        
        else if(e.getKeyCode() == NativeKeyEvent.VC_P)
        {
        	startPlayTimer();
        }
        
	}

    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }
    
    public void nativeMouseClicked(NativeMouseEvent e)
    {
    	if(e.getButton() == NativeMouseEvent.BUTTON1 && track)
    		locations.get(locations.size()-1).setClick(true);
    }
    
    public void nativeMouseReleased(NativeMouseEvent e)
    {
    }
    
    public void nativeMousePressed(NativeMouseEvent e)
    {
    }
    
    public static void setDelay(int x)
    {
    	delay = x;
    }
    
    public static void startTimer() {
    	timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
  		public void run() 
  		{
  			int myX = MouseInfo.getPointerInfo().getLocation().x;
  			int myY = MouseInfo.getPointerInfo().getLocation().y;
  			if(Math.abs(myX-X)<=100 && Math.abs(myY - Y)<=100 && on)
  			{
  				bot.mousePress(InputEvent.BUTTON1_MASK);
  				bot.mouseRelease(InputEvent.BUTTON1_MASK);
  				times = 0;
  			}
  			else
  			{
  				times++;
  			}
  			
  			if(track)
  			{
  				locations.add(new Click(MouseInfo.getPointerInfo().getLocation()));
  				count++;
  			}
  			
  			if(times >= 10000)
  			{
  				int reply = JOptionPane.showConfirmDialog(null, "Continue?", "Clicker", JOptionPane.YES_NO_OPTION);
  		        if(reply == JOptionPane.NO_OPTION)
  		        {
  		           System.exit(0);
  		        }
  		        else
  		        	count = 0;
  			}
  		}
  		}, 100, delay);
	}
    
    public static void stopTimer()
    {
    	timer.cancel();
    }
    
    public static void startPlayTimer() {
    	track = false;
    	time = 0;
    	timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
  		public void run() 
  		{
  			if(time<locations.size())
  			{
  				bot.mouseMove((int)locations.get(time).getX(), (int)locations.get(time).getY());
  				if(locations.get(time).click())
  				{
  					bot.mousePress(InputEvent.BUTTON1_MASK);
  					bot.mouseRelease(InputEvent.BUTTON1_MASK);
  				}
  				time++;
  			}
  			else
  				stopTimer();
  		}
  		}, 100, delay);
	}
    
    
    
    
}
    