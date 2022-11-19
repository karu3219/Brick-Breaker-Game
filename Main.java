//import java.util.;
import javax.swing.JFrame;
public class Main 
{
	public static void main(String args[])
	{
		JFrame frm = new JFrame();
		GamePlay gameplay = new GamePlay();
		frm.setBounds(10,10,700,600);
		frm.setTitle("ball game");
		frm.setResizable(false);
		frm.setVisible(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.add(gameplay);
	}
}
