/**
 * @author Anil Tilve
 * @author Ayush Joshi
 */

package model.pieces;

import java.awt.Color;
import java.util.ArrayList;

import model.ChessCoordinates;

public class Pawn extends Piece {

	/**
	 * Creates a new piece
	 * @param color: the color of this piece
	 */
	public Pawn(Color color) {
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
		boolean isBlack = this.getColor().equals(Color.BLACK);
		if (isBlack) {
			specialCases.isFirstMove = start.getRank() == 1 ? true : false;
			specialCases.canPromote = end.getRank() == 7 ? true : false;
		} else {
			specialCases.isFirstMove = start.getRank() == 6 ? true : false;
			specialCases.canPromote = end.getRank() == 0 ? true : false;
		}

		if (super.isValidMove(start, end, specialCases)) {
			int startRank = start.getRank(), startFile = start.getFile(), endRank = end.getRank(),
					endFile = end.getFile();

			if (specialCases.isCapturing) {
				if (startFile - 1 == endFile || startFile + 1 == endFile) {
					if (isBlack)
						return (startRank + 1 == endRank);
					else
						return (startRank - 1 == endRank);
				} else
					return false;
			} else if (start.isVerticalTo(end)) {
				if (specialCases.isFirstMove)
					return isBlack ? startRank + 1 == endRank || startRank + 2 == endRank
							: startRank - 1 == endRank || startRank - 2 == endRank;
				else if (specialCases.isPromoting)
					return isBlack ? startRank + 1 == endRank && specialCases.canPromote
							: startRank - 1 == endRank && specialCases.canPromote;
				else
					return isBlack ? startRank + 1 == endRank : startRank - 1 == endRank;
			}
		}
		return false;
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
		return super.toString() + "p";
	}
}
