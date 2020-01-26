import java.util.List;

/**
 * An instance of this class controls the actual game flow of the Bricks game.  In a two player game,
 * the players take turn placing bricks at specified row,col grid coordinates.  
 * 
 */
public class Game {
	
	/**
	 * Keeps track of the number of turns played.  Can be used to see whose turn is it.
	 */
	protected int turnCtr = 0;
	
	/**
	 * Represents the real/actual game state being played by multiple players.
	 */
	protected GameBoard theBoard;


	/**
	 * Constructs a new game with a new board.
	 */
	public Game() {
		this.reset();
	}
	
	
	/**
	 * Resets the current game instance ot start all over.  
	 */
	public void reset() {
		turnCtr = 0;
		theBoard = new GameBoard(BricksApp.MAX_ROWS, BricksApp.MAX_COLS);
	}
	
	
	/**
	 * This method allows the current player to place a brick.
	 * The caller must provide a desired location of the head 
	 * of the brick. 
	 * 
	 * @param row grid coordinate row for the head of brick
	 * @param col grid coordinate col for the head of brick
	 * 
	 * @throws Exception when location is invalid 
	 * @see Gameboard
	 */
	public void placeBrick(int orientation, Placement location) throws Exception {
		
		/*
		 * Check if desired placement is valid
		 */
		if (!theBoard.isValid(orientation, location)) {
			throw new Exception("Invalid brick placement");
		}
		
		/*
		 * If we get here, it's a valid location.  So we 
		 * commit the placement.
		 */
		theBoard.placeBrickAt(orientation, location);

		/*
		 * keep track of turns.
		 */
		turnCtr++;    
	}
	
	
	/**
	 * Checks current board state and checks of placements are
	 * available given the orientation.

	 * @param orientation GameBoard HORIZONTAL or VERTICAL
	 * 
	 * @return true if placements are available; false otherwise
	 * @see Gameboard
	 */
	public boolean arePlacementsAvailable(int orientation) {

		List<Placement> placements = theBoard.availablePlacements(orientation);
		return placements.size()>0;  
	}
	
	
	/**
	 * Used to identify winner or NONE if game is still going (ie, both players have available
	 * placements). 
	 * 
	 * @return int value indicating winner.  Gameboard constants (HORIZONTAL, VERTICAL, or NONE)
	 * @see Gameboard
	 */
	public int winner () {
		// if we can't place any HORIZONTAL bricks, the vertical player is the winner.
		if (!this.arePlacementsAvailable(GameBoard.HORIZONTAL)) return GameBoard.VERTICAL;
		
		// if we can't place any VERTICAL bricks, the horizontal player is the winner.
		if (!this.arePlacementsAvailable(GameBoard.VERTICAL)) return GameBoard.HORIZONTAL;

		// if neither, return none.
		return GameBoard.NONE;
	}
	
	
	public GameBoard getTheBoard() {
		return theBoard;
	}


	public void setTheBoard(GameBoard theBoard) {
		this.theBoard = theBoard;
	}


	

}
