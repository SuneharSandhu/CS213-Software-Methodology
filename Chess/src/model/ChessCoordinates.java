/**
 * @author Anil Tilve
 * @author Ayush Joshi
 */

package model;

public class ChessCoordinates {

	private int rank, file;
	/**
	 * Sets the rank and file as Int.
	 * @param rank
	 * @param file
	 */
	public ChessCoordinates(int rank, int file) {
		this.rank = rank;
		this.file = file;
	}

	/**
	 * sets the rank and file as Char.
	 * @param rank
	 * @param file
	 */
	public ChessCoordinates(char rank, char file) {
		this.rank = rank - '1';
		this.rank = this.rank * -1 + 7;
		this.file = file - 'a';
		
	}

	/**
	 * Sets the rank.
	 * @param rank
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * Sets the rank.
	 * @param file
	 */
	public void setFile(int file) {
		this.file = file;
	}
	/**
	 * Returns the Rank.
	 * @return
	 */
	public int getRank() {
		return this.rank;
	}

	/**
	 * Returns the File.
	 * @return
	 */
	public int getFile() {
		return this.file;
	}

	/**
	 * Returns the Rank as Char.
	 * @return
	 */
	public char getRankChar() {
		return (char) (this.rank + '1');
	}
	
	/**
	 * Returns the File as Char.
	 * @return
	 */
	public char getFileChar() {
		return (char) (this.file + 'a');
	}

	/**
	 * Checks if the coordinates is Diagonal and returns boolean answer.
	 * @param other :  Passed as Coordinates. 
	 * @return
	 */
	public boolean isDiagonalTo(ChessCoordinates other) {
		return this.isVerticalTo(other) ? false
				: (int) Math.abs((other.file - this.file) / (other.rank - this.rank)) == 1;
	}

	/**
	 * Checks if the coordinates is Horizontal and returns boolean answer.
	 * @param other : Passed as Coordinates.
	 * @return
	 */
	public boolean isHorizontalTo(ChessCoordinates other) {
		return this.rank - other.rank == 0;
	}

	/**
	 * Checks if the coordinates is Vertical and returns boolean answer.
	 * @param other : Passed as Coordinates.
	 * @return
	 */
	public boolean isVerticalTo(ChessCoordinates other) {
		return this.file - other.file == 0;
	}

	/**
	 * Checks if the coordinates is Adjacent and returns boolean answer.
	 * @param other : Passed as Coordinates.
	 * @return
	 */
	public boolean isAdjacentTo(ChessCoordinates other) {
		return (this.rank <= other.rank + 1 && this.rank >= other.rank - 1)
				&& (this.file <= other.file + 1 && this.file >= other.file - 1);
	}

	/**
	 * Returns the distance between Coordinates.
	 * @param other : Passed as Coordinates.
	 * @return
	 */
	public int distanceFrom(ChessCoordinates other) {
		return (int) Math.sqrt(Math.pow(other.file - this.file, 2) - Math.pow(other.rank - this.rank, 2));
	}

	/**
	 * Checks if the two ranks are equal and if two files are equal. Returns boolean answer.
	 * @param other : Passed as Coordinates.
	 * @return
	 */
	public boolean equals(ChessCoordinates other) {
		return this.rank == other.rank && this.file == other.file;
	}

	/**
	 * Returns the String after concatenating the File and Rank.
	 */
	public String toString() {
		return Character.toString(getFileChar()) + Character.toString(getRankChar());
	}

	/**
	 * Checks for the Valid Promotion, if the Token for Promoting pieces are valid or not and return the a boolean answer.
	 * @param promote : Passed as a String
	 * @return
	 */
	public static boolean isValidPromotion(String promote) {
		if (promote.equals("N") || promote.equals("Q") || promote.equals("B") || promote.equals("R")
				|| promote.equals("P"))
			return true;
		else
			return false;

	}
}
