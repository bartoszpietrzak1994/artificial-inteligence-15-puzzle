package sise.puzzle;

/**
 * Created by bartoszpietrzak on 06/05/2017.
 */
public class AStarPuzzleState extends PuzzleState
{
	// koszt wykonania danego ruchu
	private int g;

	// wynik heurystyki
	private int h;

	// f = g + h, wartosc, wg ktorej sortowana jest kolejka priorytetowa
	private int f;

	// laczna ilosc elementow ukladanki
	private int elementCount;

	public AStarPuzzleState(Puzzle puzzle)
	{
		super(puzzle);
		elementCount = rowsCount * columnCount;
	}

	public AStarPuzzleState() {}

	// zwraca ilość elementów, ktore nie sa na swoich miejscach
	public void blocksOutOfPlaceHeuristic()
	{
		int i = 0;

		for (int m = 0; m < puzzleArray.length; m++)
		{
			for (int n = 0; n < puzzleArray[0].length; n++)
			{
				if (puzzleArray[m][n] != goalState[m][n])
				{
					i++;
				}
			}
		}
		this.h = i;
	}

	// ustawienie stanu oraz obliczenie jego wartosci g
	public void setState(AStarPuzzleState currentAStarPuzzleState, int cost)
	{
		int gCost = currentAStarPuzzleState.getG() + cost;
		setStateValues(currentAStarPuzzleState, gCost);
	}

	// sprawdzenie czy istnieje lepsza sciezka
	public boolean checkBetterPath(AStarPuzzleState currentAStarPuzzleState, int cost)
	{
		int gCost = currentAStarPuzzleState.getG() + cost;
		if (gCost < getG())
		{
			setStateValues(currentAStarPuzzleState, gCost);
			return true;
		}
		return false;
	}

	private void setStateValues(AStarPuzzleState currentAStarPuzzleState, int gCost)
	{
		setPrev(currentAStarPuzzleState);
		setG(gCost);
		calculateFinalCost();
	}

	private void calculateFinalCost()
	{
		f = g + h;
	}

	// zwraca sume odleglosci elementow nie na swoich miejscach od miejsc, w ktorych powinny sie znajdowac
	public void manhattanSumHeuristic()
	{
		int i = 0;
		for (int m = 0; m < puzzleArray.length; m++)
		{
			for (int n = 0; n < puzzleArray[0].length; n++)
			{
				if (puzzleArray[m][n] != goalState[m][n])
				{
					int value = puzzleArray[m][n];
					int column = value % puzzleArray.length == 0 ? puzzleArray.length : (value % puzzleArray.length) - 1;
					int row = value - column == 0 ? 0 : elementCount / (value - column);

					i+=Math.abs(m - row) + Math.abs(n - column);
				}
			}
		}

		this.h = i;
	}
	public int getG()
	{
		return g;
	}

	public void setG(int g)
	{
		this.g = g;
	}

	public int getF()
	{
		return f;
	}

	@Override
	public String toString()
	{
		return super.toString();
	}

	@Override
	public boolean equals(Object o)
	{
		return super.equals(o);
	}
}
