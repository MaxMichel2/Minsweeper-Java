import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class Minesweeper extends JFrame{
	
	public int length = 8;
    public int width = 8;
    public int nMines = 10;
    public int nFlags;
    private final int mine = -1;
    private double ratio = 6.4;
    private boolean inProgress;
    private static MineButton[][] buttons;
    private static int[][] count;
    private static JMenuBar topMenu = new JMenuBar();
    private JMenu gameMenu = new JMenu("Game");
    private JMenuItem newGame= new JMenuItem("New game");
    private JMenuItem easy = new JMenuItem("Easy (8x8 board, 10 mines)");
    private JMenuItem medium = new JMenuItem("Medium (16x16 board, 20 mines)");
    private JMenuItem hard = new JMenuItem("Hard (24x24 board, 90 mines)");
    private JMenuItem exit=new JMenuItem("Exit");
    private JButton smiley = new JButton();
    public URL displaynineIcon = Minesweeper.class.getResource("/displaynine.png");
    public URL displayeightIcon = Minesweeper.class.getResource("/displayeight.png");
    public URL displaysevenIcon = Minesweeper.class.getResource("/displayseven.png");
    public URL displaysixIcon = Minesweeper.class.getResource("/displaysix.png");
    public URL displayfiveIcon = Minesweeper.class.getResource("/displayfive.png");
    public URL displayfourIcon = Minesweeper.class.getResource("/displayfour.png");
    public URL displaythreeIcon = Minesweeper.class.getResource("/displaythree.png");
    public URL displaytwoIcon = Minesweeper.class.getResource("/displaytwo.png");
    public URL displayoneIcon = Minesweeper.class.getResource("/displayone.png");
    public URL displayzeroIcon = Minesweeper.class.getResource("/displayzero.png");
    public URL negativeIcon = Minesweeper.class.getResource("/negative.png");
    public URL deadsmileyIcon = Minesweeper.class.getResource("/deadsmiley.png");
    public URL smileyIcon = Minesweeper.class.getResource("/smiley.png");
    public URL sunglassesIcon = Minesweeper.class.getResource("/sunglasses.png");
    public ImageIcon displaynine = new ImageIcon(displaynineIcon, "displaynine");
	public ImageIcon displayeight = new ImageIcon(displayeightIcon, "displayeight");
	public ImageIcon displayseven = new ImageIcon(displaysevenIcon, "displayseven");
	public ImageIcon displaysix = new ImageIcon(displaysixIcon, "displaysix");
	public ImageIcon displayfive = new ImageIcon(displayfiveIcon, "displayfive");
	public ImageIcon displayfour = new ImageIcon(displayfourIcon, "displayfour");
	public ImageIcon displaythree = new ImageIcon(displaythreeIcon, "displaythree");
	public ImageIcon displaytwo = new ImageIcon(displaytwoIcon, "displaytwo");
	public ImageIcon displayone = new ImageIcon(displayoneIcon, "displayone");
	public ImageIcon displayzero = new ImageIcon(displayzeroIcon, "displayzero");
	public ImageIcon negative = new ImageIcon(negativeIcon, "negative");
	public ImageIcon deadsmiley = new ImageIcon(deadsmileyIcon, "deadsmiley");
	public ImageIcon smileyface = new ImageIcon(smileyIcon, "smiley");
	public ImageIcon sunglasses = new ImageIcon(sunglassesIcon, "sunglasses");
	private final JLabel timerHundreds = new JLabel(displayzero);
    private final JLabel timerTens = new JLabel(displayzero);
    private final JLabel timerOnes = new JLabel(displayzero);
    private final JLabel mineHundreds = new JLabel(displayzero);
    private final JLabel mineTens = new JLabel(displayzero);
    private final JLabel mineOnes = new JLabel(displayzero);
    private int seconds = 0;
    private boolean timerStarted = false;
    Timer mineTimer = new Timer(true);
    public Container pane = new Container();
    
    public Minesweeper() {
        super("Minesweeper");
        this.setResizable(false);
    }
    
    public void addComponentsToPane(Container pane) {
    	nFlags = nMines;
        final JPanel timer = new JPanel();
        final JPanel minesCount = new JPanel();
        
        timer.setLayout(new BorderLayout());
        timer.add(timerHundreds, BorderLayout.WEST);
        timer.add(timerTens,BorderLayout.CENTER);
        timer.add(timerOnes,BorderLayout.EAST);
        
        minesCount.setLayout(new BorderLayout());
        minesCount.add(mineHundreds, BorderLayout.WEST);
        minesCount.add(mineTens, BorderLayout.CENTER);
        minesCount.add(mineOnes, BorderLayout.EAST);
        
        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        JPanel mineGrid = new JPanel();
        mineGrid.setLayout(new GridLayout(length,width));
        
        Minesweeper.buttons = new MineButton[length][width];
        Minesweeper.count = new int[length][width];
                
        for(int i = 0; i < length; i++) {
			for(int j = 0; j < width; j++) {
				buttons[i][j] = new MineButton(i, j);
				buttons[i][j].setBorder(null);
				buttons[i][j].setSize(17, 17);
				buttons[i][j].setOpaque(false);
				buttons[i][j].addMouseListener(new MouseListener()
						{
							@Override
							public void mousePressed(MouseEvent e) {
								MineButton current = (MineButton)e.getSource();
								if(SwingUtilities.isLeftMouseButton(e)){//if left mouse click
									if(inProgress == true){
										if(!timerStarted){//start timer if not yet started
											timerStarted = true;
											mineTimer.scheduleAtFixedRate(new updateTimerTask(), 1000,1000);//start timer updater
										}
										if(current.isCovered() && !(current.isFlagged())){//if button is not covered or is flagged do nothing
											if(current.isMine()){//if it's a mine user loses
												lose();
												current.uncover();
											}else{
												current.uncover();
												if(current.getMineCount() == 0){
													cascade(current);//cascade through emptys, described below
												}
												if(hasWon()){//if only squares left are mines user wins
													win();
												}
		                            
											}
										}
									}
		                        }else {
		                            if(SwingUtilities.isRightMouseButton(e)){//if right mouse clicked
		                                if(inProgress == true){//do nothing if between games
		                                    if(current.isCovered()){
		                                        current.rightClick();
		                                        if(current.isFlagged()){
		                                            nFlags--;
		                                        }else{
		                                            nFlags++;
		                                        }
		                                        updateMineCount();
		                                    }
		                                }
		                            }
		                        }
							}
					
							@Override
							public void mouseClicked(MouseEvent e) {}

							@Override
							public void mouseReleased(MouseEvent e) {}

							@Override
							public void mouseEntered(MouseEvent e) {}

							@Override
							public void mouseExited(MouseEvent e) {}
					
						}
						);
				mineGrid.add(buttons[i][j]);
			}
        }
        placeMines();
        countMines();
        
        newGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                startNewGame();
            }
        });
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        easy.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		resetDifficulty(8,8,10);
        	}
    
        });
        medium.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		resetDifficulty(16,16,20);
        	}
    
        });
        hard.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		resetDifficulty(24,24,90);
        	}
        });
        
        gameMenu.add(newGame);
        gameMenu.add(easy);
        gameMenu.add(medium);
        gameMenu.add(hard);
        gameMenu.add(exit);
        topMenu.add(gameMenu);
        topMenu.setVisible(true);
        this.setJMenuBar(topMenu);
        
        smiley.setIcon(smileyface);
        smiley.setBorder(null);
        smiley.setMaximumSize(new Dimension(smileyface.getIconHeight(),smileyface.getIconWidth()));
        smiley.setOpaque(false); 
        smiley.setContentAreaFilled(false);
        smiley.setBorderPainted(false);
        smiley.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e){
                        startNewGame();
                    }
                });
        topPanel.add(smiley, BorderLayout.CENTER);//build top panel using BorderLayout
        topPanel.add(timer, BorderLayout.EAST);
        topPanel.add(minesCount, BorderLayout.WEST);
        pane.add(topPanel, BorderLayout.NORTH);
        pane.add(new JSeparator(), BorderLayout.CENTER);
        pane.add(mineGrid, BorderLayout.SOUTH);
        inProgress = true;
    }
    
    public void resetDifficulty(int newLength, int newWidth, int newnMines){
        this.length = newLength;
        this.width = newWidth;
        this.nMines = newnMines;
        this.setContentPane(new JPanel(new BorderLayout()));
        this.addComponentsToPane(this.getContentPane());
        this.pack();
        this.revalidate();
        this.repaint();
        this.startNewGame();
    }
    
    public void placeMines() {
    	ArrayList<Integer> list = new ArrayList<Integer>();
    	for(int i = 0; i < Minesweeper.count.length; i++) {
    		for(int j = 0; j < Minesweeper.count[0].length; j++){
    			list.add(i*100+j); // for modulus and remainder
    		}
    	}
    	Minesweeper.count = new int[length][width];
    	for(int i = 0; i < nMines; i++) {
    		int choice = (int) (Math.random()*list.size());
    		Minesweeper.count[list.get(choice) / 100][list.get(choice) % 100] = mine;
    		list.remove(choice);
    	}
    	
    	for(int i = 0; i < length; i++) {
			for(int j = 0; j < width; j++) {
				if(count[i][j] == mine) {
					buttons[i][j].makeMine();	
				}
			}
    	}
    }
    
    public void countMines() {
        for(int x=0;x<length;x++){
            for(int y=0;y<width;y++){
               int nmines = 0;
               if((x-1) != -1){
                   if((y-1) != -1){
                       if(buttons[x-1][y-1].isMine()){//check button top-left
                           nmines++;
                       }
                   }
                   if(buttons[x-1][y].isMine()){//check button mid-left
                       nmines++;
                   }
                   if(y+1 < width){
                       if(buttons[x-1][y+1].isMine()){//check button bottom left
                           nmines++;
                       }
                   }
               }
               if((y-1) != -1){
                   if(buttons[x][y-1].isMine()){//check button top-middle
                       nmines++;
                   }
                   if((x+1) < length){
                       if(buttons[x+1][y-1].isMine()){//check button top-right
                           nmines++;
                       }
                   }
               }
               if((x+1) < length){
                   if(buttons[x+1][y].isMine()){//check button mid-right
                       nmines++;
                   }
                   if(y+1 < width){
                       if(buttons[x+1][y+1].isMine()){//check button bottom-right
                           nmines++;
                       }
                   }
               }
               if((y+1) < width){
                   if(buttons[x][y+1].isMine()){//check mid-bottom
                       nmines++;
                   }
               }
               buttons[x][y].setCount(nmines);
               count[x][y] = nmines;
            }
        }
    }
    
    public class updateTimerTask extends TimerTask{//Task to keep timer updating
        public void run(){
                seconds += 1;
                updateTimer();
        }
    }
    
    public void updateTimer(){//update timer graphically
        char hundreds, tens, ones;
        if(seconds < 999){
            hundreds = (char)(((seconds / 100) % 10)+30);
            tens = (char)(((seconds / 10) % 10)+30);
            ones = (char)((seconds % 10) + 30);
        }else{
            hundreds = tens = ones = '9';
        }
        setDisplayLabel(timerHundreds, hundreds);
        setDisplayLabel(timerTens, tens);
        setDisplayLabel(timerOnes, ones);
    }
    
    public void updateMineCount(){//set mine counter graphically
        if(nFlags >= 0){
            char hundreds = (char)(((nFlags / 100) % 10)+30);
            char tens = (char)(((nFlags / 10) % 10)+30);
            char ones = (char)((nFlags % 10) + 30);
            setDisplayLabel(mineHundreds, hundreds);
            setDisplayLabel(mineTens, tens);
            setDisplayLabel(mineOnes, ones);
        }else{
            int tmpFlags = nFlags * -1;
            char tens = (char)(((tmpFlags / 10) % 10)+30);
            char ones = (char)((tmpFlags % 10) + 30);
            mineHundreds.setIcon(negative);
            setDisplayLabel(mineTens, tens);
            setDisplayLabel(mineOnes, ones);
        }
    }
    
    public void setDisplayLabel(JLabel label, char num){//used by updateMines and updateTimer to determine icons for labels
        switch(num) {//this seems weird but is determining label based on ASCII value
            case 30:
                label.setIcon(displayzero);
                break;
            case 31:
                label.setIcon(displayone);
                break;
            case 32:
                label.setIcon(displaytwo);
                break;
            case 33:
                label.setIcon(displaythree);
                break;
            case 34:
                label.setIcon(displayfour);
                break;
            case 35:
                label.setIcon(displayfive);
                break;
            case 36:
                label.setIcon(displaysix);
                break;
            case 37:
                label.setIcon(displayseven);
                break;
            case 38:
                label.setIcon(displayeight);
                break;
            default://default to label 9
                label.setIcon(displaynine);
                break; 
        }
    }
    
    public void win(){//stops game and timer upon user win
        inProgress = false;
        mineTimer.cancel();
        smiley.setIcon(sunglasses);
        for(int x=0;x<length;x++){//display all hidden mines as grey mines
            for(int y=0;y<width;y++){
                if(buttons[x][y].isMine() && buttons[x][y].isCovered()){
                    buttons[x][y].setNoMine();
                }
            }
        }
    }
    
    public void lose(){//stops game and timer upon user lose
        mineTimer.cancel();
        inProgress = false;
        smiley.setIcon(deadsmiley);//smiley has died :(
        for(int x=0;x<length;x++){//display all hidden mines as grey mines
            for(int y=0;y<width;y++){
                if(buttons[x][y].isMine() && buttons[x][y].isCovered()){
                    buttons[x][y].setGreyMine();
                }
            }
        }
    }
    
    public boolean hasWon(){//check if user has won - true if only squares left uncovered are mines
        if(!(inProgress)){//do nothing if game is not in progress
            return false;
        }
        for(int x=0;x<length;x++){
            for(int y=0;y<width;y++){
                if(!(buttons[x][y].isMine()) && buttons[x][y].isCovered()){
                    return false;
                }
            }
        }
        return true;
    }
    
    public void startNewGame(){//resets all objects to beginning of game states, redistributes mines and recalculates minecounts
        mineTimer.cancel();
        mineTimer = new Timer(false);
        nFlags = nMines;
        seconds = 0;
        updateTimer();
        updateMineCount();
        for(int x = 0; x < length; x++){
            for(int y = 0; y < width; y++){
                buttons[x][y].resetProps();
            }
        }
        placeMines();
        countMines();
        smiley.setIcon(smileyface);
        timerStarted = false;
        inProgress = true;
    }
    
    public void cascade( MineButton target) {
        if(target.getVisited() == true || target.isFlagged()){//do nothing if already visited this square
            return;
        }
        target.visit();//mark visited
        target.uncover();//uncover
        int x = target.getXcoord();//get x and y coordinates to cascade through adjacent squares
        int y = target.getYcoord();
        if(target.getMineCount() == 0){
        	if((x-1) != -1){
        		if((y-1) != -1){
        			if(!(buttons[x-1][y-1].isMine()) && !(buttons[x-1][y-1].getMineCount() == 0)){//check button top-left
        				cascade(buttons[x-1][y-1]);
        			}
        		}
        		if(!(buttons[x-1][y].isMine())){//check button mid-left
        			cascade(buttons[x-1][y]);
        		}
        		if(y+1 < width){
        			if(!(buttons[x-1][y+1].isMine()) && !(buttons[x-1][y+1].getMineCount() == 0)){//check button bottom left
                    	   cascade(buttons[x-1][y+1]);
        			}
        		}
        	}
        	if((y-1) != -1){
        		if(!(buttons[x][y-1].isMine())){//check button top-middle
        			cascade(buttons[x][y-1]);
        		}
        		if((x+1) < length){
        			if(!(buttons[x+1][y-1].isMine()) && !(buttons[x+1][y-1].getMineCount() == 0)){//check button top-right
        				cascade(buttons[x+1][y-1]);
        			}
        		}
        	}
        	if((x+1) < length){
        		if(!(buttons[x+1][y].isMine())){//check button mid-right
        			cascade(buttons[x+1][y]);
        		}
        		if(y+1 < width)
        		{
        			if(!(buttons[x+1][y+1].isMine()) && !(buttons[x+1][y+1].getMineCount() == 0)){//check button bottom-right
        				cascade(buttons[x+1][y+1]);
        			}
        		}
        	}
        	if((y+1) < width){
        		if(!(buttons[x][y+1].isMine())){//check button bottom-middle
        			cascade(buttons[x][y+1]);
        		}
        	}
        }
    }
    
    public static void main(String[] args) {
    	Minesweeper frame = new Minesweeper();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.addComponentsToPane(frame.getContentPane());
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}