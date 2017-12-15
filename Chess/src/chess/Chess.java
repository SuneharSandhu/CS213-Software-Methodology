/**
 * @author Anil Tilve
 * @author Ayush Joshi
 */

package chess;
import java.awt.Color;
import controller.GameController;
import view.GameView;


public class Chess {
	/**
	 * Starts the chess game. Keeps the game going and when the game is ended, the player who wins is printed out. 
	 * @param args : Takes the String input.
	 */
	public static void main(String[] args) {
		GameController gameController = new GameController();
		GameView gameView = new GameView(gameController);
		
		while (!gameController.isEndGame) {
			gameView.drawBoard();
			gameView.printPromt();
			gameView.processInput();
		}
		
		Color curPlayer = gameController.getCurPlayer();
		System.out.println((curPlayer.equals(Color.WHITE)? "White" : "Black") + " wins.");
	}
}
