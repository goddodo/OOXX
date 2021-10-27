import java.awt.event.*;
import java.awt.*;
import java.awt.Graphics2D;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

public class OOXX extends JApplet
{
	private char whoseturn='X';
	private Cell[][][] cell=new Cell[9][3][3];
	private JLabel jlblStatus=new JLabel(" X  turn  to play");
	private JLabel jRule=new JLabel("<html><body>" + "<div style=text-align: center>" + "遊戲玩法" + "<div style=text-align:left>"
								+ "<br>" + " 1.	大井字中有9個小井字"
								+ "<br>" + " 2.	起始玩家在任意小井字的格子畫X，假設畫在小井字的右下，O就必須下在大井字右下的小井字中"
								+ "<br>" + " 3. 小井字3格連成一線時，大井字會被上色(X橘色O水藍色)"
								+ "<br>" + " 4. 最後，當大井字3格相同顏色連一線時，遊戲結束。" + "<body></html>");

	char Token='X';
	public void init()
	{
		Panel p=new Panel();
		p.setLayout(new GridLayout(9,9,1,1));
		p.setBorder(new LineBorder(Color.black,1));
		
		for(int i=0;i<9;i++)
			for(int j=0;j<3;j++)
				for(int k=0;k<3;k++)
				p.add(cell[i][j][k]=new Cell());

		jlblStatus.setFont(new java.awt.Font("Dialog", 1, 25));
	    jlblStatus.setBorder(new LineBorder(Color.black,1));
	    jRule.setFont(new java.awt.Font("Dialog", 1, 25));
	    jRule.setBorder(new LineBorder(Color.black,1));
	    jRule.setPreferredSize(new Dimension(700, 900));
		this.getContentPane().add(p);
		this.getContentPane().add(jlblStatus,BorderLayout.SOUTH);
		this.getContentPane().add(jRule,BorderLayout.EAST);
	}
 
	public static void main(String[] args) 
	{
		JFrame frame=new JFrame("超級OOXX");
		OOXX applet=new OOXX();
		frame.getContentPane().add(applet,BorderLayout.CENTER);
		applet.init();
		frame.setSize(1593,898);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public class Panel extends JPanel
	{
    	protected void paintComponent(Graphics g) 
    	{
        	super.paintComponent(g);
        	g.fillRect(290,0,3,900);
        	g.fillRect(581,0,3,900);
        	g.fillRect(872,0,3,900);
        	g.fillRect(0,269,900,3);
        	g.fillRect(0,539,900,3);
        	g.fillRect(0,809,900,3);
    	}
	}

	public int[] find()
	{
		for(int i=0;i<9;i++)
			for(int j=0;j<3;j++)
				for(int k=0;k<3;k++)
				if(cell[i][j][k].getClick()==true)
				{
					int num[]={i,j,k};
					cell[i][j][k].setClick(false);
					return num;
				}		
		int a[]={0,0};		
		return a;
	}			
	
	//limit block location
	public void setFalse(int[] num)
	{
		checkFull(num);
		checkLine();
		for(int i=0;i<9;i++)
			for(int j=0;j<3;j++)
				for(int k=0;k<3;k++){
					if(cell[i][j][k].getBackground()!=Color.ORANGE&&cell[i][j][k].getBackground()!=Color.CYAN&&cell[i][j][k].getBackground()!=Color.PINK)
					{
						cell[i][j][k].setCheck(false);
						cell[i][j][k].setBackground(Color.GRAY);
					}
				}
		//top 
		if(num[0]%3==0)
			for(int i=0;i<3;i++)
				for(int k=0;k<3;k++){
					if(cell[i][num[2]][k].getBackground()!=Color.ORANGE&&cell[i][num[2]][k].getBackground()!=Color.CYAN&&cell[i][num[2]][k].getBackground()!=Color.PINK){
						cell[i][num[2]][k].setCheck(true);
						cell[i][num[2]][k].setBackground(Color.white);
					}
					else
						changeWhite();
				}
		//middle	
		if(num[0]%3==1)
			for(int i=3;i<6;i++)
				for(int k=0;k<3;k++){
					if(cell[i][num[2]][k].getBackground()!=Color.ORANGE&&cell[i][num[2]][k].getBackground()!=Color.CYAN&&cell[i][num[2]][k].getBackground()!=Color.PINK){
						cell[i][num[2]][k].setCheck(true);
						cell[i][num[2]][k].setBackground(Color.white);
					}
					else
						changeWhite();
				}
			
		//bottom
		if(num[0]%3==2)
			for(int i=6;i<9;i++)
				for(int k=0;k<3;k++){
					if(cell[i][num[2]][k].getBackground()!=Color.ORANGE&&cell[i][num[2]][k].getBackground()!=Color.CYAN&&cell[i][num[2]][k].getBackground()!=Color.PINK){
						cell[i][num[2]][k].setCheck(true);
						cell[i][num[2]][k].setBackground(Color.white);
					}
					else
						changeWhite();
				}
					
				
	}
	// check TTT full
	public void checkFull(int num[])
	{
	if(0<=num[0]&&num[0]<=2)
		for(int i=0;i<3;i++)
			for(int k=0;k<3;k++)
				if(cell[i][num[1]][k].getToken()==' ')
					return;
	if(3<=num[0]&&num[0]<=5)
		for(int i=3;i<6;i++)
			for(int k=0;k<3;k++)
				if(cell[i][num[1]][k].getToken()==' ')
					return;
	if(6<=num[0]&&num[0]<=8)
		for(int i=6;i<9;i++)
			for(int k=0;k<3;k++)
				if(cell[i][num[1]][k].getToken()==' ')
					return;
	if(0<=num[0]&&num[0]<=2)
		for(int i=0;i<3;i++)
			for(int k=0;k<3;k++){
				cell[i][num[1]][k].setBackground(Color.PINK);
				cell[i][num[1]][k].setCheck(false);
			}
	if(3<=num[0]&&num[0]<=5)
		for(int i=3;i<6;i++)
			for(int k=0;k<3;k++){
				cell[i][num[1]][k].setBackground(Color.PINK);
				cell[i][num[1]][k].setCheck(false);
			}
	if(6<=num[0]&&num[0]<=8)
		for(int i=6;i<9;i++)
			for(int k=0;k<3;k++){
				cell[i][num[1]][k].setBackground(Color.PINK);
				cell[i][num[1]][k].setCheck(false);
			}
		return;
			
	}
	

	public void checkLine()
	{
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				for(int k=0;k<3;k++){
					if(cell[i*3][j][k].getToken()==cell[i*3+1][j][k].getToken()&&cell[i*3+1][j][k].getToken()==cell[i*3+2][j][k].getToken()&&cell[i*3][j][k].getToken()!=' '&&cell[i*3+1][j][k].getToken()!=' '&&cell[i*3+2][j][k].getToken()!=' '){
						Token=cell[i*3][j][k].getToken();
						changeColor(i*3,j,Token);
					}
					if(cell[i*3+k][j][0].getToken()==cell[i*3+k][j][1].getToken()&&cell[i*3+k][j][1].getToken()==cell[i*3+k][j][2].getToken()&&cell[i*3+k][j][0].getToken()!=' '&&cell[i*3+k][j][1].getToken()!=' '&&cell[i*3+k][j][2].getToken()!=' '){	
						Token=cell[i*3+k][j][0].getToken();
						changeColor(i*3+k,j,Token);
					}
				}	
				if(cell[i*3][j][0].getToken()==cell[i*3+1][j][1].getToken()&&cell[i*3+1][j][1].getToken()==cell[i*3+2][j][2].getToken()&&cell[i*3][j][0].getToken()!=' '&&cell[i*3+1][j][1].getToken()!=' '&&cell[i*3+2][j][2].getToken()!=' '){
					Token=cell[i*3][j][0].getToken();
					changeColor(i*3,j,Token);
				}
				if(cell[i*3+2][j][0].getToken()==cell[i*3+1][j][1].getToken()&&cell[i*3+1][j][1].getToken()==cell[i*3][j][2].getToken()&&cell[i*3+2][j][0].getToken()!=' '&&cell[i*3+1][j][1].getToken()!=' '&&cell[i*3][j][2].getToken()!=' '){
					Token=cell[i*3+2][j][0].getToken();
					changeColor(i*3+2,j,Token);
				}
			}		
		}	
	}

	public void changeWhite()
	{
		for(int i=0;i<9;i++)
			for(int j=0;j<3;j++)
				for(int k=0;k<3;k++){
					if(cell[i][j][k].getBackground()!=Color.ORANGE&&cell[i][j][k].getBackground()!=Color.CYAN&&cell[i][j][k].getBackground()!=Color.PINK){
						cell[i][j][k].setCheck(true);
						cell[i][j][k].setBackground(Color.white);
					}
				}
	}

	public void changeColor(int i,int j,char token)
	{
		if(0<=i&&i<=2)
		for(int n=0;n<3;n++)
			for(int h=0;h<3;h++){
				cell[n][j][h].setCheck(false);
				if(token=='X')
				cell[n][j][h].setBackground(Color.ORANGE);
				else if(token=='O')
				cell[n][j][h].setBackground(Color.CYAN);
			}
		if(3<=i&&i<=5)
			for(int n=3;n<6;n++)
				for(int h=0;h<3;h++){
					cell[n][j][h].setCheck(false);
					if(token=='X')
					cell[n][j][h].setBackground(Color.ORANGE);
					else if(token=='O')
					cell[n][j][h].setBackground(Color.CYAN);
			}
		if(6<=i&&i<=8)
			for(int n=6;n<9;n++)
				for(int h=0;h<3;h++){
					cell[n][j][h].setCheck(false);
					if(token=='X')
					cell[n][j][h].setBackground(Color.ORANGE);
					else if(token=='O')
					cell[n][j][h].setBackground(Color.CYAN);
			}
	}

	public void isWon()
	{
		int white=0;
			for(int j=0;j<3;j++){
				if(cell[0][j][0].getBackground()==Color.CYAN){
					if(cell[0][j][0].getBackground()==cell[3][j][0].getBackground()&&cell[3][j][0].getBackground()==cell[6][j][0].getBackground()){
						jlblStatus.setText(" O won!");
						JOptionPane pane = new JOptionPane();
						UIManager.put("OptionPane.messageFont", new Font("微軟正黑體", Font.BOLD, 20));
						UIManager.put("OptionPane.buttonFont", new Font("微軟正黑體", Font.BOLD, 20));
						int a=JOptionPane.showConfirmDialog(pane,"Game Over. O獲勝\n      再來一局?","Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);  
						if(a==JOptionPane.YES_OPTION){  
    				 		resetGame();
						}else
						System.exit(0);
						//JOptionPane.showMessageDialog(pane, "Game Over. O wins.");
						//System.exit(0);
						return;
					}
				}
				else if(cell[0][j][0].getBackground()==Color.ORANGE){
					if(cell[0][j][0].getBackground()==cell[3][j][0].getBackground()&&cell[3][j][0].getBackground()==cell[6][j][0].getBackground()){
						jlblStatus.setText(" X won!");
						JOptionPane pane = new JOptionPane();
						UIManager.put("OptionPane.messageFont", new Font("微軟正黑體", Font.BOLD, 20));
						UIManager.put("OptionPane.buttonFont", new Font("微軟正黑體", Font.BOLD, 20));
						int a=JOptionPane.showConfirmDialog(pane,"Game Over. X獲勝\n      再來一局?","Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);  
						if(a==JOptionPane.YES_OPTION){  
    				 		resetGame();
						}else
							System.exit(0);
						//JOptionPane.showMessageDialog(pane, "Game Over. X wins.");
						//System.exit(0);
						return;
					}
				}	
			}
			for(int i=0;i<3;i++){
				if(cell[i*3][0][0].getBackground()==Color.CYAN){
					if(cell[i*3][0][0].getBackground()==cell[i*3][1][0].getBackground()&&cell[i*3][1][0].getBackground()==cell[i*3][2][0].getBackground()){
						jlblStatus.setText("O won!");
						JOptionPane pane = new JOptionPane();
						UIManager.put("OptionPane.messageFont", new Font("微軟正黑體", Font.BOLD, 20));
						UIManager.put("OptionPane.buttonFont", new Font("微軟正黑體", Font.BOLD, 20));
						int a=JOptionPane.showConfirmDialog(pane,"Game Over. O獲勝\n      再來一局?","Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);  
						if(a==JOptionPane.YES_OPTION){  
    				 		resetGame();
						}else
							System.exit(0);
						//JOptionPane.showMessageDialog(pane, "Game Over. O wins.");
						//System.exit(0);
						return;
					}
				}
				else if(cell[i*3][0][0].getBackground()==Color.ORANGE){
					if(cell[i*3][0][0].getBackground()==cell[i*3][1][0].getBackground()&&cell[i*3][1][0].getBackground()==cell[i*3][2][0].getBackground()){
						jlblStatus.setText("X won!");
						JOptionPane pane = new JOptionPane();
						UIManager.put("OptionPane.messageFont", new Font("微軟正黑體", Font.BOLD, 20));
						UIManager.put("OptionPane.buttonFont", new Font("微軟正黑體", Font.BOLD, 20));
						int a=JOptionPane.showConfirmDialog(pane,"Game Over. X獲勝\n      再來一局?","Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);  
						if(a==JOptionPane.YES_OPTION){  
    				 		resetGame();
						}else
							System.exit(0);
						//JOptionPane.showMessageDialog(pane, "Game Over. X wins.");
						//System.exit(0);
						return;
					}
				}
			}
			if(cell[0][0][0].getBackground()==Color.CYAN){
				if(cell[0][0][0].getBackground()==cell[3][1][0].getBackground()&&cell[3][1][0].getBackground()==cell[6][2][0].getBackground()){
					jlblStatus.setText("O won!");
					JOptionPane pane = new JOptionPane();
					UIManager.put("OptionPane.messageFont", new Font("微軟正黑體", Font.BOLD, 20));
					UIManager.put("OptionPane.buttonFont", new Font("微軟正黑體", Font.BOLD, 20));
					int a=JOptionPane.showConfirmDialog(pane,"Game Over. O獲勝\n      再來一局?","Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);  
					if(a==JOptionPane.YES_OPTION){  
    				 	resetGame();
					}else
						System.exit(0);
					//JOptionPane.showMessageDialog(pane, "Game Over. O wins.");
					//System.exit(0);
					return;
				}
			}
			if(cell[0][0][0].getBackground()==Color.ORANGE){
				if(cell[0][0][0].getBackground()==cell[3][1][0].getBackground()&&cell[3][1][0].getBackground()==cell[6][2][0].getBackground())
				{
					jlblStatus.setText("X won!");
					JOptionPane pane = new JOptionPane();
					UIManager.put("OptionPane.messageFont", new Font("微軟正黑體", Font.BOLD, 20));
					UIManager.put("OptionPane.buttonFont", new Font("微軟正黑體", Font.BOLD, 20));
					int a=JOptionPane.showConfirmDialog(pane,"Game Over. X獲勝\n      再來一局?","Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);  
					if(a==JOptionPane.YES_OPTION){  
    				 	resetGame();
					}else
						System.exit(0);
					//JOptionPane.showMessageDialog(pane, "Game Over. X wins.");
					//System.exit(0);
					return;
				}
			}			
			if(cell[6][0][0].getBackground()==Color.CYAN){
				if(cell[6][0][0].getBackground()==cell[3][1][0].getBackground()&&cell[3][1][0].getBackground()==cell[0][2][0].getBackground())
				{
					jlblStatus.setText("O won!");
					JOptionPane pane = new JOptionPane();
					UIManager.put("OptionPane.messageFont", new Font("微軟正黑體", Font.BOLD, 20));
					UIManager.put("OptionPane.buttonFont", new Font("微軟正黑體", Font.BOLD, 20));
					int a=JOptionPane.showConfirmDialog(pane,"Game Over. O獲勝\n      再來一局?","Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);  
					if(a==JOptionPane.YES_OPTION){  
    				 	resetGame();
					}else
						System.exit(0);
					//JOptionPane.showMessageDialog(pane, "Game Over. O wins.");
					//System.exit(0);
					return;
				}
			}
			if(cell[6][0][0].getBackground()==Color.ORANGE){
				if(cell[6][0][0].getBackground()==cell[3][1][0].getBackground()&&cell[3][1][0].getBackground()==cell[0][2][0].getBackground())
				{
					jlblStatus.setText("X won!");
					JOptionPane pane = new JOptionPane();
					UIManager.put("OptionPane.messageFont", new Font("微軟正黑體", Font.BOLD, 20));
					UIManager.put("OptionPane.buttonFont", new Font("微軟正黑體", Font.BOLD, 20));
					int a=JOptionPane.showConfirmDialog(pane,"Game Over. X獲勝\n      再來一局?","Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);  
					if(a==JOptionPane.YES_OPTION){  
    				 	resetGame();
					}else
						System.exit(0);
					//JOptionPane.showMessageDialog(pane, "Game Over. X wins.");
					//System.exit(0);
					return;
				}
			}	
			for(int i=0;i<9;i++)
				for(int j=0;j<3;j++)
					for(int k=0;k<3;k++)
						if(cell[i][j][k].getBackground()==Color.white){
							white++;
							break;
						}
			
			if(white==0){
				jlblStatus.setText("Draw!");
				JOptionPane pane = new JOptionPane();
				UIManager.put("OptionPane.messageFont", new Font("微軟正黑體", Font.BOLD, 20));
				UIManager.put("OptionPane.buttonFont", new Font("微軟正黑體", Font.BOLD, 20));
				int a=JOptionPane.showConfirmDialog(pane,"Game Over. 平手\n      再來一局?","Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);  
				if(a==JOptionPane.YES_OPTION){  
    				 resetGame();
				}else
					 System.exit(0); 
				//JOptionPane.showMessageDialog(pane, "Game Over. Draw.");
				//System.exit(0);
			}	
	}

	
	public void resetGame()
	{
		for(int i=0;i<9;i++)
			for(int j=0;j<3;j++)
				for(int k=0;k<3;k++)
				{
					cell[i][j][k].setBackground(null);
					cell[i][j][k].setClick(false);
					cell[i][j][k].setCheck(true);
					cell[i][j][k].resetGame();
				}
	}

	class Cell extends JPanel implements MouseListener
	{
		int[] number;
		boolean check=true;
		private boolean click=false;
		private char token=' ';
		public Cell()
		{
			setBorder(new LineBorder(Color.black));
			addMouseListener(this);
		}
		public char getToken()
		{
			return token;
		}
		public void setToken(char c)
		{
			token=c;
			repaint();
		}
		public void setCheck(boolean b)
		{
			check=b;
		}
		public boolean getClick()
		{
			return click;
		}
		public void setClick(boolean b)
		{
			click=b;
		}

		public void resetGame()
		{
			setToken(' ');
			whoseturn='X';
			jlblStatus.setText(" X turn to play");
			click=false;
		}
		
		public void paintComponent(Graphics g)
		{	
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			if(token=='X')
			{	
				g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2d.drawLine( 10, 10, getSize().width-10, getSize().height-10);
				g2d.drawLine( getSize().width-10, 10, 10, getSize().height-10);
				
			}
			else if(token=='O')
			{
				g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2d.drawOval( 10, 10,getSize().width-20, getSize().height-20);	
			}
		}
		
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		
		public void mouseClicked(MouseEvent e)
		{
			if(check==true)
			{
				if(token==' ')
				{
					if(whoseturn=='X')
					{
						setToken('X');
						whoseturn='O';
						jlblStatus.setText(" O turn to play");
						click=true;
						number=find();
						setFalse(number);
						isWon();
					}
					else if(whoseturn=='O')
					{
						setToken('O');
						whoseturn='X';
						jlblStatus.setText(" X turn to play");
						click=true;
						number=find();
						setFalse(number);
						isWon();
					}
				}
			}
		}
	}
}