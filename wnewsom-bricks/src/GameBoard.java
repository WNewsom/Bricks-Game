import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class encapsulates and manages the current game state.  Convenient methods
 * manage the state.   The interactive game can use an instance of this to represent the actual 
 * game between 2 players.   A minimax-based agent can also use different instances of this class
 * to represent hypothetical game states during the minimax search.
 * <br/>
 * When placing a brick, we consider locations in grid coordinates (row, col) provided to us by 
 * a placement object.   The locate the top of a vertical brick and the left of a horizontal brick.
 *
 */
public class GameBoard {
	
	/*
	 * The following constants are used to communicate the orientation of a brick.  We use them
	 * when placing bricks and check for possible locations,  
	 */
	public static final int HORIZONTAL = -1;
	public static final int VERTICAL = 1;
	public static final int NONE = 0;
	
	
	/**
	 * Configures a new game board with no bricks given the dimensionality of
	 * the game. 
	 * 
	 * @param rows
	 * @param cols
	 */
	public GameBoard (int rows, int cols) {
		// insert your code here		
	}
	
	
	/**
	 * Attempts to modify the board state by placing a
	 * brick of specified orientation at the specified location.
	 * 
	 * @param where
	 * @param orientation GameBoard HORIZONTAL or VERTICAL
	 */
	public void placeBrickAt(int orientation, Placement where) {
		// TODO insert your code here
	}
	
	
	
	/**
	 * Checks current board state and returns all possible valid
	 * placements given the brick orientation.   The placements
	 * specify where the brick head would be placed.   The must
	 * also be room for the brick tail.
	 * 
	 * @param orientation GameBoard HORIZONTAL or VERTICAL
	 * 
	 * @return A list of possible placement objects.
	 */
	public List<Placement> availablePlacements(int orientation) {
		// TODO insert your code here		
		return new ArrayList<Placement>();
	}
	
	
	
	
	
	/**
	 * Checks the current state to communicate if a brick placement
	 * would be valid given its orientation.  
	 * 
	 * @param orientation  See GameBoard constants
	 * @param location A hypothetical placement.
	 * 
	 * @return true if a brick of specific orientation can be placed
	 * at the specified location; false otherwise.
	 */
	public boolean isValid(int orientation, Placement location) {
		
		// TODO insert your code here		
		return true;
	}
	
	
	/**
	 * Returns the location of all of the current bricks given the 
	 * specified orientation. 
	 * 
	 * @param orientation   See GameBoard constants.
	 * @return Returns the location of all of the current bricks given the 
	 * specified orientation. 
	 *  
	 */
	public List<Placement> currentBricks(int orientation) {
		// TODO insert your code here					
		return null;
	}
	
	/**
	 * Returns a copy of the current board especially convenient for hypothetical
	 * state space searching.   Make sure you return a "deep" copy.
	 * 
	 * @return  a deep copy of the current object (game board).
	 */
	public GameBoard boardCopy() {
		// TODO insert your code here				
		return null;
	}
	
	
	
	/*
	 * Other methods might be convenient for you..
	 */
	
	
}
