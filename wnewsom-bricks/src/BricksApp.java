import java.awt.Dimension;

/**
 * Bricks App
 * 
 * It all starts here.
 * 
 * @author ?
 *
 */
public class BricksApp {

	/*
	 * Game board dimensions.  Public so other classes in this application 
	 * can access easily.
	 */
	public static final int MAX_ROWS = 3;
	public static final int MAX_COLS = 5;
	
	/*
	 * The size (number of pixels) of short side of brick.   The long
	 * side of brick is assumed to be twice as big.   This value determines
	 * the grid size of the board.   Bricks will occupy 2 grid spaces.
	 */
	public static final int BRICK_SIZE = 100;
	
	
	public static void main(String[] args) {

		MainFrame mf = new MainFrame();
		mf.setVisible(true);
		
	}

}
