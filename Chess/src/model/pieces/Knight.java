/**
 * @author Anil Tilve
 * @author Ayush Joshi
 */

package model.pieces;

import java.awt.Color;
import java.util.ArrayList;

import model.ChessCoordinates;

public class Knight extends Piece {

	/**
	 * Creates a new piece
	 * @param color: the color of this piece
	 */
	public Knight(Color color) {
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
		if (super.isValidMove(start, end, specialCases)) {
			int startRank = start.getRank(), startFile = start.getFile(), endRank = end.getRank(),
					endFile = end.getFile();

			if (startRank + 2 == endRank || startRank - 2 == endRank)
				return startFile + 1 == endFile || startFile - 1 == endFile;
			else if (startFile + 2 == endFile || startFile - 2 == endFile)
				return startRank + 1 == endRank || startRank - 1 == endRank;
		}
		return false;
	}

	/**
	 * Gets the farthest moves for this piece
	 * @param start: start coordinates
	 * @return the farthest moves for this piece
	 */
	public ArrayList<ChessCoordinates> farthestMovesFrom(ChessCoordinates start) {
		ArrayList<ChessCoordinates> farthestMoves = new ArrayList<ChessCoordinates>();

		int startRank = start.getRank(), startFile = start.getFile(), curRank = startRank + 2, curFile = startFile + 1;

		if (curRank < 8 && curRank >= 0 && curFile < 8 && curFile >= 0)
			farthestMoves.add(new ChessCoordinates(curRank, curFile));

		curRank = startRank - 2;
		curFile = startFile + 1;

		if (curRank < 8 && curRank > 0 && curFile < 8 && curFile >= 0)
			farthestMoves.add(new ChessCoordinates(curRank, curFile));

		curRank = startRank + 2;
		curFile = startFile - 1;

		if (curRank < 8 && curRank > 0 && curFile < 8 && curFile >= 0)
			farthestMoves.add(new ChessCoordinates(curRank, curFile));

		curRank = startRank - 2;
		curFile = startFile - 1;

		if (curRank < 8 && curRank >= 0 && curFile < 8 && curFile >= 0)
			farthestMoves.add(new ChessCoordinates(curRank, curFile));

		curRank = startRank + 1;
		curFile = startFile + 2;

		if (curRank < 8 && curRank >= 0 && curFile < 8 && curFile >= 0)
			farthestMoves.add(new ChessCoordinates(curRank, curFile));

		curRank = startRank - 1;
		curFile = startFile + 2;

		if (curRank < 8 && curRank >= 0 && curFile < 8 && curFile >= 0)
			farthestMoves.add(new ChessCoordinates(curRank, curFile));

		curRank = startRank + 1;
		curFile = startFile - 2;

		if (curRank < 8 && curRank >= 0 && curFile < 8 && curFile >= 0)
			farthestMoves.add(new ChessCoordinates(curRank, curFile));

		curRank = 0;
		curFile = 0;

		if (curRank < 8 && curRank >= 0 && curFile < 8 && curFile >= 0)
			farthestMoves.add(new ChessCoordinates(curRank, curFile));

		return farthestMoves;
	}

	/**
	 * Creates a string representation of this piece
	 * @return string representation of this piece
	 */
	public String toString() {
		return super.toString() + "N";
	}
}
