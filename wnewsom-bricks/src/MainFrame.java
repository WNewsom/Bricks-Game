import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * An instance of this class organizes our presentation layer.   We present a simple game board
 * and provide the controls barely sufficient for alternating placement of bricks.   We randomly decide
 * who starts.   The agent button will be disabled if the human user (the operator) starts.  Otherwise,
 * the agent button will be enabled.   
 * <br/>
 * We enable/disable the agent button to enforce alternative turns. 
 * <br/>
 * The human player initiates a turn by clicking on the board.   A mouse event listener (MouseHelper) 
 * responds by attempting to place a brick at the equivalent grid coordinate on the board.
 * 
 */
public class MainFrame extends JFrame {

	
	private static final String AGENT_TURN_MSG = "It's agent's turn.  Click the agent button.";

	private static final String HUMAN_TURN_MSG = "It's your turn.  Click on the board to place a brick.";

	private final Color HORIZONTAL_COLOR = Color.BLUE;    // horizontal bricks

	private final Color VERTICAL_COLOR = Color.RED;	// vertical bricks

	private final Color BOARD_COLOR = new Color(1.0f, 1.0f, 0.8f);    // soft pale yellow
	
	
	private Game theGame;		// handle on current game.
	
	private GameView theView;	// handle on game view for rendering refresh
	
	private JButton btnAgent;	// button to launch smart AI player to choose a brick placement
	
	private JLabel lblInstruct;

	private JButton btnRestart;
	
	
	/**
	 * The primary constructor will build and configure our human interface.  We present 
	 * a graphical representation of our bricks game in the center of the frame with a button
	 * panel on the south.
	 */
	public MainFrame() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
		 * business logic through the game object.
		 */
		theGame = new Game();
		

		/*
		 * Configure an instruction text panel top-side.
		 */
		JPanel pnlInstructions = new JPanel();
		lblInstruct = new JLabel("Welcome");
		lblInstruct.setHorizontalAlignment(SwingConstants.CENTER);
		pnlInstructions.add(lblInstruct);
		this.getContentPane().add(pnlInstructions, BorderLayout.NORTH);

		
		/*
		 * The game view object will present/paint the current game state.  We make it 
		 * just big enough given the dimension constants defined in our BricksApp class. 
		 */
		theView = new GameView();
		theView.setBackground(BOARD_COLOR);
		
		Dimension pnlSize = new Dimension(BricksApp.MAX_COLS*BricksApp.BRICK_SIZE+1, BricksApp.MAX_ROWS*BricksApp.BRICK_SIZE+1);
		theView.setMinimumSize(pnlSize);
		theView.setMaximumSize(pnlSize);
		theView.setPreferredSize(pnlSize);
		
		// configure the game view to respond to mouse clicks
		theView.addMouseListener(new MouseHelper());
		
		this.getContentPane().add(theView, BorderLayout.CENTER);
		
		
		
		/*
		 * Now we prepare the UI controls.   A 2 button interface is sufficient.   We will keep one 
		 */
		JPanel pnlButtons = new JPanel();
		
	
		btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onRestartButtonClick();
			}});
		pnlButtons.add(btnRestart);
		

		btnAgent = new JButton("Agent Turn");
		btnAgent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onAgentButtonClick();
			}});
		pnlButtons.add(btnAgent);
		
		
		this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);
		
		restart();
		
		this.pack();  // force frame to adjust to content size
		
		this.setLocationRelativeTo(null);   // center on screen
	}


	/**
	 * Starts the current game by choosing 
	 */
	private void restart() {
		
		theGame.reset();
		
		if (((int)(Math.random()*2) == 1)) {
			btnAgent.setEnabled(false);     // agent button starts disabled to keep him from going out of turn.	
			lblInstruct.setText(HUMAN_TURN_MSG);
		} else {
			btnAgent.setEnabled(true);     // agent button starts disabled to keep him from going out of turn.
			lblInstruct.setText(AGENT_TURN_MSG);
		}
	}
	
	
	/**
	 * Called when the operator player is indicating a brick placement
	 * by clicking on our view panel.   The mouse position provided is 
	 * in pixel coordinates.  So we need to convert to game grid coordinates 
	 * and cause 
	 * 
	 * @param mx
	 * @param my
	 */
	protected void onMouseClick(int mx, int my) {

		if (theGame.winner() != GameBoard.NONE) return; 
		
		if (btnAgent.isEnabled()) {
			JOptionPane.showMessageDialog(this, "It's not your turn.", "Oops...", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		try {
			

			/*
			 * Based on your user's mouse click, derive the grid row,col coorindates
			 */
			int c = mx / BricksApp.BRICK_SIZE;
			int r = my / BricksApp.BRICK_SIZE;
			
			System.out.println(String.format("User clicked on brick row=%d and col= %d", r,c));
			
			/*
			 * Bundle coordinates into a placement object
			 */
			Placement brickLocation = new Placement(r,c);
			
			/*
			 * commit the brick placement for this player
			 */
			theGame.placeBrick(GameBoard.HORIZONTAL, brickLocation);
			
			btnAgent.setEnabled(true);
			lblInstruct.setText(AGENT_TURN_MSG);
			
			this.checkForGameOver();
			
			
		} catch (Exception e) {

			JOptionPane.showMessageDialog(this, e.getMessage(), "Brick Placement Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}


		
		theView.repaint();
	
	}
	
	
	/**
	 * Informs the user of the game results if game is over. 
	 */
	private void checkForGameOver() {
		
		int player = theGame.winner(); 
				
		if (player == GameBoard.VERTICAL) {
			lblInstruct.setText("Agent wins!");
		} 

		if (player == GameBoard.HORIZONTAL) {
			lblInstruct.setText("You win!");
		} 
	}
	
	
	/**
	 * AI Agent Turn.  When the "Agent" button is clicked, we need to get the help of an AI agent in order to 
	 * establish the non-player character's placement of a brick.
	 */
	protected void onAgentButtonClick() {

		System.out.println("Launching AI agents move");
		
		// TODO finish this method

		try {
			
			Placement brickLocation = null;
			
			// Here you need to work with a smart minimax agent to identify a suitable brick placement here.
			// Make a copy of the state so you are not tempted to change the real/actual board.
			//
			// brickLocation = agent.nextPlacement( theGame.getTheBoard().boardCopy() );
			// 
			
			theGame.placeBrick(GameBoard.VERTICAL, brickLocation);     // commit the placement to the board.
			
			btnAgent.setEnabled(false);   // don't allow the agent to go out of turn 
			lblInstruct.setText(HUMAN_TURN_MSG);
			
			this.checkForGameOver();
			
						
		} catch (Exception e) {

			JOptionPane.showMessageDialog(this, e.getMessage(), "Brick Placement Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		theView.repaint();
	}

	
	

	/**
	 * Restarts the game.
	 */
	protected void onRestartButtonClick() {
		System.out.println("Restarting Game");
		this.restart();
		theView.repaint();
	}

	
	

	
	/**
	 * An instance of this class renders/presents the current game state.  This method is 
	 * called eventually by repaint() when needed.
	 *
	 */
	private class GameView extends JPanel {
		
		@Override
		public void paint(Graphics g) {
			
			/*
			 * first erase prior graphics if any by filling with
			 * background color.
			 */
			g.setColor(this.getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			/*
			 * Now draw the board grid.
			 */
			g.setColor(Color.DARK_GRAY);
			for(int r=0; r<BricksApp.MAX_ROWS; r++) {
				for (int c=0; c<BricksApp.MAX_COLS; c++  ) {
					int x = c * BricksApp.BRICK_SIZE;
					int y = r * BricksApp.BRICK_SIZE;
					g.drawRect(x, y, BricksApp.BRICK_SIZE, BricksApp.BRICK_SIZE);
				} // for each col
			} // for each row
			
			
			/*
			 * Now paint all the horizontal bricks on top of the grid.
			 */
			g.setColor(HORIZONTAL_COLOR);
			List<Placement> hbricks = theGame.getTheBoard().currentBricks(GameBoard.HORIZONTAL);
			if (hbricks != null) {
				for (Placement loc : hbricks) {
					g.fillRect(loc.col*BricksApp.BRICK_SIZE, loc.row*BricksApp.BRICK_SIZE, BricksApp.BRICK_SIZE*2, BricksApp.BRICK_SIZE);
				}
			}

			/*
			 * Now paint all the vertical bricks
			 */
			g.setColor(VERTICAL_COLOR);
			List<Placement> vbricks = theGame.getTheBoard().currentBricks(GameBoard.VERTICAL);
			if (vbricks != null) {
				for (Placement loc : vbricks) {
					g.fillRect(loc.col*BricksApp.BRICK_SIZE, loc.row*BricksApp.BRICK_SIZE, BricksApp.BRICK_SIZE, BricksApp.BRICK_SIZE*2);
				}
			}

			
			
			
		}
	}
	
	
		
	/**
	 * An instance of this class invokes the call back method to allow the
	 * user to place a brick.
	 *
	 */
	private class MouseHelper extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			
			int mx = e.getX();
			int my = e.getY();
			
			onMouseClick(mx,my);
		}
		
	}
	
	
}
