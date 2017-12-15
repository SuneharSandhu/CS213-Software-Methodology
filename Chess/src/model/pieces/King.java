package model.pieces;

import java.awt.Color;
import java.util.ArrayList;

import model.ChessCoordinates;

public class King extends Piece {

	/**
	 * Creates a new piece
	 * @param color: the color of this piece
	 */
	public King(Color color) {
		super(color);
	}
	
	/**
	 * Returns the color of this piece
	 * @return the color of this piece
	 */
	public Color getColor() {
		return super.getColor();
	}
	
	/**
	 * Checks if this piece is making a legal move
	 * @param start: start coordinates
	 * @param end: end coordinates
	 * @param specialCases: special cases regarding this piece
	 * @return true if the piece can make this move, false if it can't
	 */
	public boolean isValidMove(ChessCoordinates start, ChessCoordinates end, SpecialCases specialCases) {
		return super.isValidMove(start, end, specialCases) && start.isAdjacentTo(end);
	}
	
	/**
	 * Gets the farthest moves for this piece
	 * @param start: start coordinates
	 * @return the farthest moves for this piece
	 */
	public ArrayList<ChessCoordinates> farthestMovesFrom() {
		return null;
	}

	/**
	 * Creates a string representation of this piece
	 * @return string representation of this piece
	 */
	public String toString() {
		return super.toString() + "K";
	}
}
