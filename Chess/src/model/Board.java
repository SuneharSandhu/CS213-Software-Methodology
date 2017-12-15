/**
 * @author Anil Tilve
 * @author Ayush Joshi
 */

package model;

import java.awt.Color;
import model.pieces.*;

import model.pieces.Piece;

public class Board {

	/**
	 * 
	 * Inner class representing board square
	 *
	 */
	public class Square {
		private ChessCoordinates coordinates;
		private Piece occupier;

		/**
		 * Creates a new square
		 * 
		 * @param rank:
		 *            the row of this square, as an int
		 * @param file:
		 *            the column of this square, as an int
		 * @param occupier:
		 *            The piece that will occupy this square
		 */
		public Square(int rank, int file, Piece occupier) {
			this.coordinates = new ChessCoordinates(rank, file);
			this.occupier = occupier;
		}

		/**
		 * Creates a new square
		 * 
		 * @param rank:
		 *            the row of this square, as a char
		 * @param file:
		 *            the column of this square, as a char
		 * @param occupier:
		 *            The piece that will occupy this square
		 */
		public Square(char rank, char file, Piece occupier) {
			this.coordinates = new ChessCoordinates(rank, file);
			this.occupier = occupier;
		}

		/**
		 * returns the piece that occupies this square
		 * 
		 * @return the occupying piece if there is one, null otherwise
		 */
		public Piece getOccupier() {
			return this.occupier;
		}

		/**
		 * Changes the occupier of this square
		 * 
		 * @param occupier:
		 *            the piece that is to occupy this square
		 */
		public void setOccupier(Piece occupier) {
			this.occupier = occupier;
		}

		/**
		 * Checks if this square is occupied
		 * 
		 * @return true if the square is occupied, false otherwise
		 */
		public boolean isOccupied() {
			return (this.occupier == null) ? false : true;
		}

		/**
		 * Returns the coordinates of this square
		 * 
		 * @return the coordinates of this square
		 */
		public ChessCoordinates getCoordinates() {
			return this.coordinates;
		}

		/**
		 * Returns the row of this square
		 * 
		 * @return the row of this square
		 */
		public int getRank() {
			return this.coordinates.getRank();
		}

		/**
		 * Returns the column of this square
		 * 
		 * @return the column of this square
		 */
		public int getFile() {
			return this.coordinates.getFile();
		}

		/**
		 * converts the square into an ASCII string
		 * 
		 * @return a string representation of this square
		 */
		public String toString() {
			if (this.occupier == null) {
				if ((this.getRank() % 2 == 0 && this.getFile() % 2 == 0)
						|| (this.getRank() % 2 != 0 && this.getFile() % 2 != 0))
					return "  ";

				return "##";
			}
			return this.occupier.toString();
		}
	}

	public static final int RANK_AND_FILE_COUNT = 8;

	private Square[][] board;

	/**
	 * The Constructor for the Board, initialized all the pieces on the board.  
	 */
	public Board() {
		this.board = new Square[RANK_AND_FILE_COUNT][RANK_AND_FILE_COUNT];
		Color pieceColor = null;

		for (int curRank = 0; curRank < RANK_AND_FILE_COUNT; curRank++)
			for (int curFile = 0; curFile < RANK_AND_FILE_COUNT; curFile++) {
				Piece newPiece = null;

				if (curRank == 0 || curRank == 7) { // initialize non-pawns
					pieceColor = curRank == 0 ? Color.BLACK : Color.WHITE;

					if (curFile == 0 || curFile == 7)
						newPiece = new Rook(pieceColor);
					else if (curFile == 1 || curFile == 6)
						newPiece = new Knight(pieceColor);
					else if (curFile == 2 || curFile == 5)
						newPiece = new Bishop(pieceColor);
					else if (curFile == 3)
						newPiece = new Queen(pieceColor);
					else
						newPiece = new King(pieceColor);
				} else if (curRank == 1 || curRank == 6) // initialize pawns
					newPiece = new Pawn(curRank == 1 ? Color.BLACK : Color.WHITE);

				this.board[curRank][curFile] = new Square(curRank, curFile, newPiece);
			}
	}

	/**
	 * Returns a square given coordinates
	 * 
	 * @param coordinates:
	 *            the coordinates of the requested square
	 * @return the square
	 */
	public Square getSquare(ChessCoordinates coordinates) {
		return this.board[coordinates.getRank()][coordinates.getFile()];
	}

	/**
	 * Returns a square given a rank and file
	 * 
	 * @param rank:
	 *            the row of the requested square
	 * @param file:
	 *            the column of the requested square
	 * @return the square
	 */
	public Square getSquare(int rank, int file) {
		return this.board[rank][file];
	}

	/**
	 * 
	 * @param startRank
	 * @param startFile
	 * @param endRank
	 * @param endFile
	 */
	public void movePiece(int startRank, int startFile, int endRank, int endFile) {
		this.board[endRank][endFile].occupier = this.board[startRank][startFile].occupier;
		this.board[startRank][startFile].occupier = null;
	}

	/**
	 * Returns a string representation of this board
	 * 
	 * @return a string representation of this board
	 */
	public String toString() {
		String boardString = "";

		for (int curRank = 0; curRank < RANK_AND_FILE_COUNT; curRank++) {
			for (int curFile = 0; curFile < RANK_AND_FILE_COUNT; curFile++) {
				boardString += this.board[curRank][curFile].toString() + " ";
			}

			boardString += (curRank * -1 + 8) + "\n";
		}

		boardString += " a  b  c  d  e  f  g  h\n";

		return boardString;
	}
}
