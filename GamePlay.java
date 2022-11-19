import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
//KeyLister -> is used for detecting the arrow key and ActionListener is used to moving the boll
public class GamePlay extends JPanel implements KeyListener,ActionListener
{
	private boolean play = false;
	private int score = 0;
	private int max=0;
	private int totalbricks = 9;
	private Timer timer;
	private int speed=8;
	private int playerX=310;
	private int ballposX=120;
	private int ballposY=350;
	private int ballXdir=2;
	private int ballYdir=2;
	private Map_Gnenerator map;
	public GamePlay()
	{
		map = new Map_Gnenerator(3,3);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(speed,this);
		timer.start();
		 
	}
	public void paint(Graphics g)
	{
//		backgroud
		g.setColor(Color.black);
//		create the recangle for the background
		g.fillRect(1,1,692,592);
//		drowing map
		map.draw((Graphics2D)g);// (x,y,width,height)
//		borders
		g.setColor(Color.GREEN);
		g.fillRect(0,0,3,592);
		g.fillRect(0,0,692,3);
		g.fillRect(691,0,3,592);
//		score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD,25));
		g.drawString(""+score,590,30);
//		paddle
		g.setColor(Color.yellow);
		g.fillRect(playerX,550,100,8);
		
//		the ball
		g.setColor(Color.blue);
		g.fillOval(ballposX, ballposY, 20,20);
		
		if(totalbricks <= 0)
		{
			play=false;
			ballXdir=0;
			ballYdir=0;
//			int max=score;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("YOU WON THE GAME",260,300);
			g.setColor(Color.red);
			g.setFont(new Font("serief",Font.BOLD,20));
			g.drawString("Your Score is"+max,290,325);
			
			g.setColor(Color.RED);
			g.drawString("press Enter to play again",260,350);
			score=0;
		}
		if(ballposY > 570)
		{
//			int max=score;
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("GAME OVER",260,300);
			
			g.setColor(Color.red);
			g.setFont(new Font("serief",Font.BOLD,20));
			g.drawString("Your Score is "+max,250,330);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("press Enter to Restart",230,360);
			score=0;
		}
		g.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		timer.start();
		if(play)
		{
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8)))
			{
				ballYdir=-ballYdir;
			}
			for(int i=0;i<map.map.length;i++)
			{
				for(int j=0;j<map.map[0].length;j++)
				{
					if(map.map[i][j]>0)
					{
						int brickx=j*map.brickwidth+80;    
						int bricky=i*map.brickheight+50;
						int brickwidth=map.brickwidth;
						int brickheight=map.brickheight;
						
						Rectangle rect = new Rectangle(brickx,bricky,brickwidth,brickheight);
						Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{
							 map.setBrickValue(0,i,j);
							 totalbricks--;
							 score+=5;
							 max=score;
							 if(ballposX+19<=brickRect.x || ballposX+1 >= brickRect.x+brickRect.width)
						     {
						     	 ballXdir = - ballXdir;
							 }
						     else
						     {
						    	 ballYdir= - ballYdir;
						     }
						}
					}
				}
			}
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			if(ballposX<0)  // left border
			{
				ballXdir=-ballXdir;
			}
			if(ballposY<0)   //top border
			{
				ballYdir=-ballYdir;
			}
			if(ballposX>670)  //right border
			{
				ballXdir=-ballXdir;
			}
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e){}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT )
		{
			if(playerX>=600)
			{
				playerX=600;
			}
			else
			{
				moveRight();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode()== KeyEvent.VK_LEFT)
		{
			if(playerX<=10)
			{
			   playerX=10;
			}
			else
			{
			   moveLeft();
			}	
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			if(!play)
			{
				play=true;
			}
			ballposX=120;
			ballposY=350;
			ballXdir= 2;
			ballYdir= 2;
			playerX=310;
			totalbricks = 21;
			map=new Map_Gnenerator(3,7);	
				
			repaint();	
			}
	}
	public void moveRight()
	{
		play=true;
		playerX+=20;		
	}
	public void moveLeft()
	{
		play=true;
		playerX-=20;
	}
}