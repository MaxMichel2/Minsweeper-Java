import java.net.URL;
import javax.swing.*;

public class MineButton extends JButton{
	
	private boolean btnHasMine = false;
    private int mineCount = 0;
    private boolean isCovered = true;
    private boolean visited = false;
    private boolean flagged = false;
	public int x;
	public int y;
	private final static URL blankIcon = MineButton.class.getResource("/blank.png");
	private final static URL coveredIcon = MineButton.class.getResource("/covered.png");
	private final static URL oneIcon = MineButton.class.getResource("/one.png");
	private final static URL twoIcon = MineButton.class.getResource("/two.png");
	private final static URL threeIcon = MineButton.class.getResource("/three.png");
	private final static URL fourIcon = MineButton.class.getResource("/four.png");
	private final static URL fiveIcon = MineButton.class.getResource("/five.png");
	private final static URL sixIcon = MineButton.class.getResource("/six.png");
	private final static URL sevenIcon = MineButton.class.getResource("/seven.png");
	private final static URL eightIcon = MineButton.class.getResource("/eight.png");
	private final static URL mineIcon = MineButton.class.getResource("/mine.png");
	private final static URL greymineIcon = MineButton.class.getResource("greymine.png");
	private final static URL flagIcon = MineButton.class.getResource("/flag.png");
	private final static URL nomineIcon = MineButton.class.getResource("/nomine.png");
	private final static ImageIcon blank = new ImageIcon(blankIcon, "blank");
	private final static ImageIcon covered = new ImageIcon(coveredIcon, "covered");
	private final static ImageIcon one = new ImageIcon(oneIcon, "one");
	private final static ImageIcon two = new ImageIcon(twoIcon, "two");
	private final static ImageIcon three = new ImageIcon(threeIcon, "three");
	private final static ImageIcon four = new ImageIcon(fourIcon, "four");
	private final static ImageIcon five = new ImageIcon(fiveIcon, "five");
	private final static ImageIcon six = new ImageIcon(sixIcon, "six");
	private final static ImageIcon seven = new ImageIcon(sevenIcon, "seven");
	private final static ImageIcon eight = new ImageIcon(eightIcon, "eight");
	private final static ImageIcon mine = new ImageIcon(mineIcon, "mine");
	private final static ImageIcon greymine = new ImageIcon(greymineIcon, "greymine");
	private final static ImageIcon flag = new ImageIcon(flagIcon, "flag");
	private final static ImageIcon nomine = new ImageIcon(nomineIcon, "nomine");
	//private final static ImageIcon question = new ImageIcon("/Users/Maxime/Pictures/Minesweeper/src/images/question.png", "question");

	public MineButton(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.setIcon(covered);
	}
	
	@Override
    protected int checkHorizontalKey(int i, String string) {
        return super.checkHorizontalKey(i, string);
    }
	
	public void visit() {
		this.visited = true;
	}
	
	public void setGreyMine(){
        this.setIcon(greymine);
    }
	
	public void setNoMine() {
		this.setIcon(nomine);
	}
	
	public void resetProps(){
		this.flagged = false;
		this.visited = false;
		this.btnHasMine = false;
		this.mineCount = 0;
		this.isCovered = true;
        this.setIcon(covered);
    }
	
	public boolean getVisited(){
		return visited;
	}
	
	public void rightClick(){
        if(flagged){
        	this.flagged = false;
            this.setIcon(covered);
        }else{
        	this.flagged = true;
            this.setIcon(flag);
        }
    }
	
    public boolean isFlagged(){
        return this.flagged;
    }
    
    public void unvisit(){
        this.visited = false;
    }
    
    public void makeMine(){
        this.btnHasMine=true;
    }
    
    public int getXcoord(){
        return this.x;
    }
    
    public int getYcoord(){
        return this.y;
    }
    
    public int getMineCount(){
        return this.mineCount;
    }
    
    public boolean isCovered(){
        return this.isCovered;
    }
    
    public boolean isMine(){
        return this.btnHasMine;
    }
    
    public void setCount(int count){
        this.mineCount = count;
    }
    
    public void uncover(){
        this.isCovered = false;
        if(this.flagged){
            return;
        }
        if(this.btnHasMine){
            this.setIcon(mine);
        }else{
            switch(mineCount) {
                case 0:
                    this.setIcon(blank);
                    break;
                case 1:
                    this.setIcon(one);
                    break;
                case 2:
                    this.setIcon(two);
                    break;
                case 3:
                    this.setIcon(three);
                    break;
                case 4:
                    this.setIcon(four);
                    break;
                case 5:
                    this.setIcon(five);
                    break;
                case 6:
                    this.setIcon(six);
                    break;
                case 7:
                    this.setIcon(seven);
                    break;
                case 8:
                    this.setIcon(eight);
                    break;
            }
        }
        
    }
}