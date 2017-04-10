// A program for computing distributions within the Hearthstone Arena Mode

import java.util.Arrays;
import java.lang.Math;
import java.util.LinkedList;
import java.io.Console;

public class ArenaSim {

	public static void main(String[] args) {
		Console c = System.console();
		if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        int state = 0;

        int maxWins = -1;
        while (maxWins < 0) {
        	try {
	        	maxWins = Integer.parseInt(c.readLine("Max wins: "));
	        	if (maxWins <= 0) {
	        		maxWins = -1;
	        		System.out.println("Max wins must be greater than 0");
	        		continue;
	        	}
	        } catch (NumberFormatException e) {
	        	System.out.println("Not a valid number");
	        }
        }

		int maxLoss = -1;
		while (maxLoss < 0) {
        	try {
	        	maxLoss = Integer.parseInt(c.readLine("Max wins: "));
	        	if (maxLoss <= 0) {
	        		maxLoss = -1;
	        		System.out.println("Max wins must be greater than 0");
	        		continue;
	        	}
	        } catch (NumberFormatException e) {
	        	System.out.println("Not a valid number");
	        }
        }




        while (state != 1 && state != 2) {
        	try {
        		state = Integer.parseInt(c.readLine("\nSelect which calculation to make:\n1 for Percentile Computation\n2 for Simulating Runs\n:"));
        	} catch (NumberFormatException e) {
        		System.out.println("Not a valid option");
        	}
        }

        switch (state) {
        	case 1:
        		System.out.println("Calculating Percentages");
        		break;
    		case 2:
    			System.out.println("Simulating Arena Runs");
    			break;
        }

		float[][] percentages;
		int[][] runResults;
		switch (state) {
			case 1:
				percentages = batchCompute(maxWins, maxLoss);
				System.out.printf("%25s\n", "Losses");
				System.out.printf("%-8s", "Wins");
				for (int i = 0; i <= maxLoss; i++) {
					System.out.printf("%-8d", i);
				}
				System.out.println();
				for (int i = 0; i <= maxWins; i++) {
					System.out.printf("%-8d", i);
					for (int j = 0; j <= maxLoss; j++) {
						String inner = String.format("%.2f%%", percentages[i][j]);
						System.out.printf("%-8s", inner);
					}
					System.out.println();
				}
				System.out.println("============================");
				System.out.println("Win Percentages Over All Arena Runs");
				for (int i = 0; i < maxWins; i++) {
					System.out.printf("%-4s%.2f%%%n", String.format("%d:", i), percentages[i][maxLoss]);
				}
				float finalWin = 0f;
				for (int i = 0; i < maxLoss; i++) {
					finalWin += percentages[maxWins][i];
				}
				System.out.printf("%-4s%.2f%%%n", String.format("%d:", maxWins), finalWin);
				break;
			case 2:
				Boolean cont = true;
				// int n = 8192;
				while (cont) {
					int n = -1;
					while (n <= 0) {
						try {
							n = Integer.parseInt(c.readLine("How many participants: "));
						} catch (NumberFormatException e) {
							System.out.println("Invalid number of participants");
						}
					}
					runResults = simulateRuns(n, maxWins, maxLoss);
					System.out.printf("%20s\n", "Losses");
					System.out.printf("%-8s", "Wins");
					for (int i = 0; i <= maxLoss; i++) {
						System.out.printf("%-8d", i);
					}
					System.out.println();
					System.out.println();
					for (int i = 0; i <= maxWins; i++) {
						System.out.printf("%-8d", i);
						for (int j = 0; j <= maxLoss; j++) {
							System.out.printf("%-8d", runResults[i][j]);
						}
						System.out.println();
					}
					System.out.println("n = " + n);
				}
				break;
		}
		
	}

	private static float[][] batchCompute(int maxWins, int maxLoss) {
		float[][] arena = new float[maxWins+1][maxLoss+1];
		arena[0][0] = 100f;
		for (int games = 1; games < maxWins+maxLoss; games++) {
			int wins = Math.min(games, maxWins);
			int loss = games - wins;
			while (wins >= 0 && loss <= maxLoss) {
				if (wins != 0 && loss != maxLoss) {
					arena[wins][loss] += 0.5 * arena[wins-1][loss];
				}
				if (loss != 0 && wins != maxWins) {
					arena[wins][loss] += 0.5 * arena[wins][loss-1];
				}
				wins--;
				loss++;
			}
		}
		return arena;
	}

	private static int[][] simulateRuns(int n, int maxWins, int maxLoss) {
		int[][] arena = new int[maxWins+1][maxLoss+1];
		arena[0][0] = n;
		LinkedList<int[]> queue = new LinkedList<int[]>();
		queue.add(new int[]{0,0});
		int[] currentSquare;
		while (queue.size() != 0) {
			currentSquare = queue.remove();
			if (currentSquare[0] == maxWins || currentSquare[1] == maxLoss || arena[currentSquare[0]][currentSquare[1]] <= 1) {
				continue;
			}
			arena[currentSquare[0]+1][currentSquare[1]] += arena[currentSquare[0]][currentSquare[1]]/2;
			arena[currentSquare[0]][currentSquare[1]+1] += arena[currentSquare[0]][currentSquare[1]]/2;
			arena[currentSquare[0]][currentSquare[1]] = arena[currentSquare[0]][currentSquare[1]]%2;

			queue.add(new int[]{currentSquare[0]+1, currentSquare[1]});
			queue.add(new int[]{currentSquare[0], currentSquare[1]+1});
		}
		return arena;
	}
}