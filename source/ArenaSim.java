// A program for computing distributions within the Hearthstone Arena Mode

import java.util.Arrays;
import java.lang.Math;

public class ArenaSim {

	public static void main(String[] args) {
		float[][] fullArena = batchCompute();
		// System.out.printf("%15s\n", "Losses");
		for (int i = 0; i < fullArena.length; i++) {
			for (int j = 0; j < fullArena[0].length; j++) {
				String inner = String.format("%.2f%%", fullArena[i][j]);
				System.out.printf("%-8s", inner);
			}
			System.out.println();
		}
	}

	private static float[][] batchCompute() {
		float[][] arena = new float[13][4];
		arena[0][0] = 100f;
		for (int games = 1; games < 15; games++) {
			int wins = Math.min(games, 12);
			int loss = games - wins;
			while (wins >= 0 && loss <= 3) {
				if (wins != 0 && loss != 3) {
					arena[wins][loss] += 0.5 * arena[wins-1][loss];
				}
				if (loss != 0 && wins != 12) {
					arena[wins][loss] += 0.5 * arena[wins][loss-1];
				}
				wins--;
				loss++;
			}
		}
		return arena;
	}
}