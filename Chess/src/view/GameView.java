/**
 * @author Anil Tilve
 * @author Ayush Joshi
 */

package view;

import java.awt.Color;
import java.util.Scanner;
import java.util.regex.Pattern;

import controller.GameController;
import model.ChessCoordinates;
import model.pieces.*;

public class GameView {

	public GameController gameController;
	Scanner userInput = new Scanner(System.in);
	final Pattern movePattern = Pattern.compile("([a-h][1-8]){1} ([a-h][1-8]){1}( [QNRB])?");
	public boolean drawOffered = false;

	/*
	 * Creates new Game view
	 */
	public GameView(GameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * figures out what to do with user input
	 */
	public void processInput() {

		String input = userInput.nextLine().trim();
		
		System.out.println();

		if (input.toLowerCase().contains("resign")) {
			gameController.isEndGame = true;
			gameController.switchCurPlayer();
		} else if (input.toLowerCase().contains("draw?")) {
			drawOffered = true;
			gameController.switchCurPlayer();
		} else if (input.toLowerCase().compareTo("draw") == 0 && drawOffered) {
			System.exit(0);
		}else if (movePattern.matcher(input).matches()) {
			ChessCoordinates start = new ChessCoordinates(input.charAt(1), input.charAt(0)),
					end = new ChessCoordinates(input.charAt(4), input.charAt(3));
			
			Piece promotion = null;
			
			if (input.length() == 7) {
				char promotionChar = input.charAt(6);
				Color pieceColor = gameController.getCurPlayer() == Color.WHITE ? Color.WHITE : Color.BLACK;
				switch (promotionChar) {
				case 'Q': promotion = new Queen(pieceColor); break;
				case 'N': promotion = new Knight(pieceColor); break;
				case 'B': promotion = new Bishop(pieceColor); break;
				default: promotion = new Rook(pieceColor); break;
				}
			}
			
			gameController.attemptMove(start, end, promotion);
		} else
			System.out.println("Illegal move, try again.");
		
		System.out.println();
	}

	/**
	 * prints prompt for user
	 */
	public void printPromt() {
		System.out.print((gameController.getCurPlayer() == Color.WHITE ? "White" : "Black") + "'s move: ");
	}

	/**
	 * Draws the board
	 */
	public void drawBoard() {
		System.out.println(this.gameController.getBoard().toString());
	}
}
