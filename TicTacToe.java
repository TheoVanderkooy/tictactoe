import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.imageio.*;

/**
 * Simple game of Tic-Tac-Toe
 * @author Theodore Vanderkooy
 */
public class TicTacToe extends JPanel {

	private Tile[][] tiles = new Tile[3][3];
	private TileVal player;
	private JPanel boardArea;
	private JButton replay;
	private JLabel topText;
	static BufferedImage Ximg, Oimg;
	
	public TicTacToe(){
		//configure graphics
		this.setBackground(new Color(150, 180, 250));
		this.setLayout(new BorderLayout());
		
		//configure new game button
		replay = new JButton("New Game");
		replay.addActionListener((ActionEvent evt) -> {
			this.newGame();
		});
		this.add(replay, BorderLayout.SOUTH);
		
		//configure/initialize text at top of screen
		topText = new JLabel("");
		topText.setHorizontalAlignment(SwingConstants.CENTER);
		topText.setFont(new Font("Arial", Font.PLAIN, 18));
		this.add(topText, BorderLayout.NORTH);
		
		//initialize tiles
		for (int i = 0; i < 9; i++){
			tiles[i/3][i%3] = new Tile(this);
		}
		newGame(); //initialize game state
		boardArea = new JPanel(); //assemble tiles in a grid
		boardArea.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 9; i++){
			boardArea.add(tiles[i%3][i/3]);
		}
		this.add(boardArea, BorderLayout.CENTER);
	}
	
	/**
	 * Resets game state before starting a new game
	 */
	public void newGame(){
		if(Ximg == null) topText.setText("Error loading images. Green goes first.");
		else topText.setText("Click in a tile to place a piece. X goes first.");		
		
		player = TileVal.X;
		for (int i = 0; i < 9; i++){
			tiles[i/3][i%3].reset();
		}
		repaint();
	}
	
	/**
	 * Changes current player when previous player makes a move, and returns
	 * 	previous player to change tile value.
	 * @return the player who last clicked on an empty tile
	 */
	protected TileVal getPlayer(){ 
		switch(player){
		case EMPTY: //player is set to EMPTY when the game has been won, placing empty does nothing
			return TileVal.EMPTY;
		case X:
			player = TileVal.O;
			topText.setText(player + "'s turn.");
			return TileVal.X;
		case O:
			player = TileVal.X;
			topText.setText(player + "'s turn.");
			return TileVal.O;
		}
		return TileVal.EMPTY;
	}
	
	/**
	 * Checks is either player has won yet, or if a stalemate has been reached.
	 * If someone has won, highlight how they won and display a message. 
	 */
	public void checkWin(){
		//check row wins
		for(int row = 0; row < 3; row++){
			if(tiles[row][0].value() != TileVal.EMPTY //check all tiles in row are same and not empty
					&& tiles[row][0].value() == tiles[row][1].value()
					&& tiles[row][0].value() == tiles[row][2].value()){
				for(Tile t : tiles[row]){ //highlight row
					t.setWin();
				}
				doWin(tiles[row][0].value());
				return;
			}
		}
		//check col wins
		for(int col = 0; col < 3; col++){
			if(tiles[0][col].value() != TileVal.EMPTY //check if all tiles in col are the same and not empty
					&& tiles[0][col].value() == tiles[1][col].value()
					&& tiles[0][col].value() == tiles[2][col].value()){
				for(Tile[] t : tiles){ //highlight column
					t[col].setWin();
				}
				doWin(tiles[0][col].value());
				return;
			}
		}
		//check diagonal wins
		if(tiles[0][0].value() != TileVal.EMPTY //check if all tiles in diagonal are the same and not empty
				&& tiles[0][0].value() == tiles[1][1].value()
				&& tiles[0][0].value() == tiles[2][2].value()){
			for(int i = 0; i < 3; i++){ //highlight diagonal
				tiles[i][i].setWin();
			}
			doWin(tiles[0][0].value());
			return;
		}
		if(tiles[0][2].value() != TileVal.EMPTY //check if all tiles in diagonal are the same and not empty
				&& tiles[0][2].value() == tiles[1][1].value()
				&& tiles[0][2].value() == tiles[2][0].value()){
			for(int i = 0; i < 3; i++){ //highlight diagonal
				tiles[i][2-i].setWin();
			}
			doWin(tiles[0][2].value());
			return;
		}
		//check for stalemate
		for(int i = 0; i < 9; i++){
			if(tiles[i/3][i%3].value() == TileVal.EMPTY) 
				return; //if there are empty spaces it is not a stalemate
		}
		doWin(TileVal.EMPTY);
	}
	
	/**
	 * Stops the current game and displays a message saying who won.
	 * @param winner : which player won. If EMPTY, a stalemat was reached.
	 */
	private void doWin(TileVal winner){
		player = TileVal.EMPTY; //stop pieces from being placed
		topText.setText(winner != TileVal.EMPTY ? 
						winner + " wins!" : //display winner
						"Stalemate!"); //display a stalemate
	}
	
	public static void main(String[] args) {
		
		try { //load images
			Ximg = ImageIO.read(TicTacToe.class.getResource("/X.png"));
			Oimg = ImageIO.read(TicTacToe.class.getResource("/O.png"));
		} catch (IOException e) {
			Ximg = null;
			Oimg = null;
		}
		
		
		//create and initialize/configure game window
		JFrame window = new JFrame();
		TicTacToe content = new TicTacToe();
		window.setContentPane(content);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocation(100,100);
		window.setSize(360, 400);
		window.setResizable(false);
		window.setVisible(true);	
	}

}
