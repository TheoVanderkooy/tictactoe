import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


public class Tile extends JPanel implements MouseListener{
	private final static int size = 100;
	private final static int sSize = size * 17 / 20; //used for sizing circle if images don't load
	
	private TicTacToe game;
	private TileVal val = TileVal.EMPTY;
	private boolean win = false;
	
	public Tile(TicTacToe game){
		//initialize tile
		this.game = game;
		this.addMouseListener(this);
		this.setBackground(new Color(180, 200, 250));
	}
	
	/**
	 * Resets this tile for a new game
	 */
	public void reset(){
		this.val = TileVal.EMPTY;
		this.win = false;
		repaint();
	}
	
	/**
	 * Highlights this tile if it was part of the winning 3-in-a-row
	 */
	public void setWin(){
		this.win = true;
		repaint();
	}
	
	/**
	 * @return the value of this tile
	 */
	public TileVal value(){
		return this.val;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(new Color(80, 150, 200)); //draw background
		g.fill3DRect((this.getWidth() - size)/2, (this.getHeight()-size)/2, size, size, win);
		//draw piece 
		switch(val){
		case X:
			if(TicTacToe.Ximg != null){ //if image exists use it
				g.drawImage(TicTacToe.Ximg, (this.getWidth() - size)/2, (this.getHeight()-size)/2, size, size, this);
			} else{ //otherwise a green circle
				g.setColor(new Color(50, 200, 100));
				g.fillOval((this.getWidth() - sSize)/2, (this.getHeight()-sSize)/2, sSize, sSize);
			}
			break;
		case O:
			if(TicTacToe.Oimg != null){ //use image if possible
				g.drawImage(TicTacToe.Oimg, (this.getWidth() - size)/2, (this.getHeight()-size)/2, size, size, this);
			} else { //otherwise a red circle
				g.setColor(new Color(200, 80, 60));
				g.fillOval((this.getWidth() - sSize)/2, (this.getHeight()-sSize)/2, sSize, sSize);
			}
			break;
		case EMPTY: //if empty tile don't draw anything else
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(this.val == TileVal.EMPTY){ //if tile is currently empty place a piece and check for a winner
			this.val = game.getPlayer();
			game.checkWin();
			repaint();
		}
		else return;
	}

	//don't use these actions
	@Override
	public void mouseEntered(MouseEvent arg0) { }
	@Override
	public void mouseExited(MouseEvent arg0) { }
	@Override
	public void mousePressed(MouseEvent arg0) { }
	@Override
	public void mouseReleased(MouseEvent arg0) { }

}
