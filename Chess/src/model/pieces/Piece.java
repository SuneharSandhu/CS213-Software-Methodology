/**
 * @author Anil Tilve
 * @author Ayush Joshi
 */

package model.pieces;

import java.awt.Color;
import java.util.ArrayList;

import model.ChessCoordinates;

public class Piece {
		
	private Color color;
		
	/**
	 * Creates a new piece
	 * @param color: the color of this piece
	 */
	public Piece(Color color)
	{
		this.color = color;
	}
	
	/**
	 * Returns the color of this piece
	 * @return the color of this piece
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Checks if this piece is making a legal move
	 * @param start: start coordinates
	 * @param end: end coordinates
	 * @param specialCases: special cases regarding this piece
	 * @return true if the piece can make this move, false if it can't
	 */
	public boolean isValidMove(ChessCoordinates start, ChessCoordinates end, SpecialCases specialCases)
	{
		return start.distanceFrom(end) <= 7;
	}
	
	/**
	 * Gets the farthest moves for this piece
	 * @param start: start coordinates
	 * @return the farthest moves for this piece
	 */
	public ArrayList<ChessCoordinates> farthestMovesFrom(ChessCoordinates start) {
		return null;
	}
 	
	/**
	 * Creates a string representation of this piece
	 * @return string representation of this piece
	 */
	public String toString() {
		return this.color.equals(Color.WHITE)? "w" : "b";
	}
}