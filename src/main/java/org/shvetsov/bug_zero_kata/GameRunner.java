
package org.shvetsov.bug_zero_kata;
import java.util.Random;

/**
 *BugsZero Kata
 *  <p><a href="https://kata-log.rocks/bugs-zero-kata">bugs-zero-kata</a>
 */
public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Random rand = new Random();
		playGame(rand);

	}

	public static void playGame(Random rand) {
		Game aGame = new Game();

		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");


		do {

			aGame.roll(rand.nextInt(5) + 1);

			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}



		} while (notAWinner);
	}
}
