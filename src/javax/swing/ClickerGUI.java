import javax.swing.*;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

public class ClickerGUI extends JFrame implements ActionListener{
	private static JTextField celciusField;
	private JSpinner celText;
	private final int WIDTH = 200, HEIGHT = 100;
	
	public ClickerGUI() {
		setTitle("Cliker");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocation(700, 300);
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(2, 1));
        contentPane.setEnabled(true);
        contentPane.setFocusable(true);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1, 1));
        
        JPanel celciusPanel = new JPanel();
        celciusPanel.setLayout(new BorderLayout()); 
        
        celText = new JSpinner(new SpinnerNumberModel(10,Integer.MIN_VALUE,Integer.MAX_VALUE,1));
        
        JLabel celLabel = new JLabel ( "Clicker Delay" );
        celciusPanel.add(celLabel, BorderLayout.NORTH);
        celciusPanel.add(celText, BorderLayout.SOUTH);
        celciusPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        textPanel.add(celciusPanel);
        
        JButton celEnter = new JButton("Enter");
        celEnter.addActionListener(this);
        celEnter.setBackground(Color.blue);
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        
        buttons.add(celEnter);
        
        contentPane.add(textPanel);
        contentPane.add(buttons);
        
	}
	
	public void actionPerformed(ActionEvent e)
    {
    	if(e.getActionCommand().equals("Enter"))
    	{
    		Clicker.setDelay((int)celText.getValue());
    		Clicker.stopTimer();
    		Clicker.startTimer();
    		Clicker.gui.setVisible(false);
    	}
    }
}
