
package org.shvetsov.bug_zero_kata;
import java.util.Random;

/**
 *BugsZero Kata
 *  <p><a href="https://kata-log.rocks/bugs-zero-kata">bugs-zero-kata</a>
 */
public class GameRunner {

	public static void main(String[] args) {
		Random rand = new Random();
		playGame(rand);
	}

	public static void playGame(Random rand) {
		Game aGame = new Game.GameBuilder("Anton", "Sasha", "Dany")
				.rollStepSupplier(() -> rand.nextInt(5) + 1)
				.correctAnswerCheckerSupplier(() -> rand.nextInt(9) != 7)
				.pursesToWin(10)
				.boardSize(25)
				.build();

		Game.Player winner = aGame.start();
		System.out.println(winner.getName() + " congratulations!");
	}
}
