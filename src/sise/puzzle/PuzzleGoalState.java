package sise.puzzle;

/**
 * Created by bartoszpietrzak on 08/05/2017.
 */
public class PuzzleGoalState
{
	private static int[][] goalState;

	public PuzzleGoalState(int i, int j)
	{
		goalState = new int[i][j];

		int k = 1;
		for (int n = 0; n < i; n++)
		{
			for (int m = 0; m < j; m++)
			{
				if (n == i-1 && m == j-1)
				{
					break;
				}
				goalState[n][m] = k;
				k++;
			}
		}
	}

	public int[][] getGoalState()
	{
		return goalState;
	}
}
