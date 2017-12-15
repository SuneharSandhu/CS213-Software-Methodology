/**
 * @author Anil Tilve
 * @author Ayush Joshi
 */

package controller;

import java.awt.Color;
import java.util.ArrayList;

import model.Board;
import model.Board.Square;
import model.ChessCoordinates;
import model.pieces.*;

public class GameController {

	/**
	 * Declaration of the variables.
	 */
	private Board board;
	public boolean isEndGame;
	private Color curPlayer;

	/**
	 * Constructor to Initialize the variables.
	 */
	public GameController() {
		this.board = new Board();
		this.isEndGame = false;
		this.curPlayer = Color.WHITE;
	}

	/**
	 * Returns the Current Board.
	 * 
	 * @return the current board
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Takes two coordinates, start and end from the user to move the piece. Also
	 * takes the third token for the promotion. Do the checks for the coordinates
	 * and perform the move. Alerts for the Check or final checkmate.
	 * 
	 * @param start
	 *            : The start coordinate of the piece to be moved.
	 * @param end
	 *            : The end coordinate for the piece to be moved at.
	 * @param promotion
	 *            : If promotion applied by the user, the token for the piece to be
	 *            promoted to.
	 */
	public void attemptMove(ChessCoordinates start, ChessCoordinates end, Piece promotion) {
		if (start.equals(end)) {
			System.out.println("Illegal move, try again");
			return;
		}

		Piece movingPiece = this.board.getSquare(start).getOccupier(),
				takenPiece = this.board.getSquare(end).getOccupier();
		SpecialCases specialCases = new SpecialCases();

		specialCases.isCapturing = takenPiece == null ? false : true;
		specialCases.isPromoting = promotion == null ? false : true;
		specialCases.pieceInPath = this.isPieceInPath(start, end);

		if (movingPiece == null || !movingPiece.getColor().equals(curPlayer)
				|| takenPiece != null && takenPiece.getColor().equals(curPlayer))
			System.out.println("Illegal move, try again");
		else if (movingPiece.isValidMove(start, end, specialCases)) {
			int startRank = start.getRank(), startFile = start.getFile(), endRank = end.getRank(),
					endFile = end.getFile();

			if (movingPiece instanceof Pawn && specialCases.canPromote)
				this.board.getSquare(endRank, endFile).setOccupier(promotion);
			else
				this.board.getSquare(endRank, endFile).setOccupier(movingPiece);

			this.board.getSquare(startRank, startFile).setOccupier(null);

			if (takenPiece instanceof King) {
				this.isEndGame = true;
				System.out.println("Checkmate");
			} else if (kingIsInCheck())
				System.out.println("Check");
			switchCurPlayer();
		} else {
			System.out.println("Illegal move, try again");
		}
	}

	/**
	 * Takes the starting and the ending coordinates for the move and Checks if
	 * there is any other piece in the part for the move to be performed.
	 * 
	 * @param start
	 *            : Starting coordinates from the user
	 * @param end
	 *            : Ending coordinate of the move from the user.
	 * @return : Returns the true if a piece is blocking the path of the current
	 *         player's piece.
	 */
	private boolean isPieceInPath(ChessCoordinates start, ChessCoordinates end) {
		int startRank = start.getRank(), startFile = start.getFile(), endRank = end.getRank(), endFile = end.getFile();

		if (start.isVerticalTo(end)) {
			if (startRank < endRank) {
				for (int curRank = startRank + 1; curRank < endRank; curRank++)
					if (this.board.getSquare(curRank, startFile).isOccupied())
						return true;
			} else {
				for (int curRank = startRank - 1; curRank > endRank; curRank--)
					if (this.board.getSquare(curRank, startFile).isOccupied())
						return true;
			}
		} else if (start.isHorizontalTo(end)) {
			if (startFile < endFile) {
				for (int curFile = startFile + 1; curFile < endFile; curFile++)
					if (this.board.getSquare(startRank, curFile).isOccupied())
						return true;
			} else {
				for (int curFile = start.getFile() - 1; curFile > endFile; curFile--)
					if (this.board.getSquare(startRank, curFile).isOccupied())
						return true;
			}
		} else if (start.isDiagonalTo(end)) {
			if (start.isAdjacentTo(end))
				return this.board.getSquare(endRank, endFile).isOccupied();
			else {
				int fileChange = endFile > startFile ? 1 : -1, rankChange = endRank > startRank ? 1 : -1;

				for (int curRank = startRank + rankChange; curRank != endRank; curRank += rankChange)
					for (int curFile = startFile + fileChange; curFile != endFile; curFile += fileChange)
						if (this.board.getSquare(curRank, curFile).isOccupied())
							return true;
			}
		}

		return false;
	}

	/**
	 * Checks if a piece at a given rank and file is colliding with the king
	 * 
	 * @param rank
	 *            : Starting coordinated from the user
	 * @param file
	 *            : Ending coordinate of the move from the user.
	 * @return : Returns the true if the piece is colliding with the king, false
	 *         otherwise.
	 */
	public boolean isPieceCollidingWithKing(int rank, int file) {
		Piece piece = this.board.getSquare(rank, file).getOccupier();
		ChessCoordinates start = new ChessCoordinates(rank, file);
		ArrayList<ChessCoordinates> farthestMoves = piece.farthestMovesFrom(start);

		if (farthestMoves != null)
			for (int curMove = 0; curMove < farthestMoves.size(); curMove++)
				if (this.isKingFirstCollision(start, farthestMoves.get(curMove)))
					return true;

		return false;
	}

	/**
	 * Checks if a king is in check by a piece
	 * 
	 * @return : Returns the true if the king is in check, false otherwise.
	 */
	private boolean kingIsInCheck() {
		for (int curRank = 0; curRank < 8; curRank++)
			for (int curFile = 0; curFile < 8; curFile++) {
				Square curSquare = this.board.getSquare(curRank, curFile);
				if (curSquare.isOccupied() && curSquare.getOccupier().getColor().equals(curPlayer))
					return isPieceCollidingWithKing(curRank, curFile);
			}

		return false;
	}

	/**
	 * Checks if a king is the first collision for given coordinates
	 * 
	 * @param start
	 *            : Starting coordinates
	 * @param end
	 *            : Ending coordinates
	 * @return : Returns the true if the king is the first collision, false otherwise.
	 */
	public boolean isKingFirstCollision(ChessCoordinates start, ChessCoordinates end) {
		int startRank = start.getRank(), startFile = start.getFile(), endRank = end.getRank(), endFile = end.getFile();

		if (start.isVerticalTo(end)) {
			for (int curRank = startRank + 1; curRank < endRank; curRank++) {
				Square curSquare = this.board.getSquare(curRank, startFile);
				if (curSquare.isOccupied())
					return curSquare.getOccupier() instanceof King;
			}
		} else if (start.isHorizontalTo(end)) {
			for (int curFile = startFile + 1; curFile < endFile; curFile++) {
				Square curSquare = this.board.getSquare(startRank, curFile);
				if (curSquare.isOccupied())
					return curSquare.getOccupier() instanceof King;
			}
		} else if (start.isDiagonalTo(end)) {
			int rankChange = endRank > startRank ? 1 : -1, fileChange = endFile > startFile ? 1 : -1;

			for (int curRank = startRank + rankChange; curRank != endRank; curRank += rankChange)
				for (int curFile = startRank + rankChange; curFile != endFile; curFile += fileChange) {
					Square curSquare = this.board.getSquare(curRank, curFile);
					if (curSquare.isOccupied())
						return curSquare.getOccupier() instanceof King;
				}
		}

		return false;
	}

	/**
	 * Gets the current pklayer
	 * @return the current player
	 */
	public Color getCurPlayer() {
		return this.curPlayer;
	}

	/**
	 * Switches the players from white to black or black to white.
	 */
	public void switchCurPlayer() {
		this.curPlayer = this.curPlayer.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
}
