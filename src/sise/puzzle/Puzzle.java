package sise.puzzle;

import java.util.Random;

public class Puzzle
{
	private static Puzzle instance = new Puzzle();

	private int rowsCount;

	private int columnCount;

	private int[][] puzzleArray;

	private static int[][] goalState;

	private int zeroColumn;

	private int zeroRow;

	private boolean isGoalState;

	public Puzzle() {}

	public void initialize(int rowsCount, int columnCount)
	{
		this.rowsCount = rowsCount;
		this.columnCount = columnCount;
		puzzleArray = new int[this.rowsCount][this.columnCount];
		goalState = new PuzzleGoalState(rowsCount, columnCount).getGoalState();

		int k = 1;
		for (int n = 0; n < rowsCount; n++)
		{
			for (int m = 0; m < columnCount; m++)
			{
				if (n == rowsCount-1 && m == columnCount-1)
				{
					break;
				}
				puzzleArray[n][m] = k;
				k++;
			}
		}
		// ustawienie wiersza oraz kolumny zawierajacej 0 na prawy dolny rog
		setZeroRow(this.rowsCount - 1);
		setZeroColumn(this.columnCount - 1);
		isGoalState = true;
	}

	public void initialize(int[][] puzzle, int rowsCount, int columnCount)
	{
		this.rowsCount = rowsCount;
		this.columnCount = columnCount;

		// ustawienie stanu oczekiwanego
		goalState = new PuzzleGoalState(rowsCount, columnCount).getGoalState();
		puzzleArray = new int[this.rowsCount][this.columnCount];

		// inicjalizacja tablicy
		for (int i = 0; i < this.rowsCount; i++)
		{
			for (int j = 0; j < this.columnCount; j++)
			{
				puzzleArray[i][j] = puzzle[i][j];
				if (puzzle[i][j] == 0)
				{
					setZeroRow(i);
					setZeroColumn(j);
				}
			}
		}
    
    	// sprawdzenie czy podana tablica jest stanem oczekiwanym
		this.isGoalState = isGoalState();
	}

	// metoda testowa, dokonujaca dozwolonych ruchow na ukladance mieszajac ja w losowy sposob
	public void randomize()
	{
		Random random = new Random();
		for (int i = 0; i < rowsCount * 20; i++)
		{
			switch (random.nextInt(4))
			{
				case (0):
					if (moveLeft())
					{
						break;
					}
					else
					{
						i--;
					}
					break;
				case (1):
					if (moveRight())
					{
						break;
					}
					else
					{
						i--;
					}
					break;
				case (2):
					if (moveUp())
					{
						break;
					}
					else
					{
						i--;
					}
					break;
				case (3):
					if (moveDown())
					{
						break;
					}
					else
					{
						i--;
					}
					break;
			}
		}
		if (isGoalState())
		{
			randomize();
		}
	}

	// metoda odpowiadajaca za ruch w lewo, nie odkladajaca stanu
	public boolean moveLeft()
	{
		if (zeroColumn <= 0)
		{
			return false;
		}

		int temp = puzzleArray[zeroRow][zeroColumn - 1];
		puzzleArray[zeroRow][zeroColumn - 1] = 0;
		puzzleArray[zeroRow][zeroColumn] = temp;
		setZeroColumn(zeroColumn - 1);

		return true;
	}

	// metoda odpowiadajaca za ruch w prawo, nie odkladajaca stanu
	public boolean moveRight()
	{
		if (zeroColumn >= columnCount - 1)
		{
			return false;
		}

		int temp = puzzleArray[zeroRow][zeroColumn + 1];
		puzzleArray[zeroRow][zeroColumn + 1] = 0;
		puzzleArray[zeroRow][zeroColumn] = temp;
		setZeroColumn(zeroColumn + 1);

		return true;
	}

	// metoda odpowiadajaca za ruch w gore, nie odkladajaca stanu
	public boolean moveUp()
	{
		if (getZeroRow() <= 0)
		{
			return false;
		}

		int temp = puzzleArray[zeroRow - 1][zeroColumn];
		puzzleArray[zeroRow - 1][zeroColumn] = 0;
		puzzleArray[zeroRow][zeroColumn] = temp;
		setZeroRow(zeroRow - 1);

		return true;
	}

	// metoda odpowiadajaca za ruch w dol, nie odkladajaca stanu
	public boolean moveDown()
	{
		if (getZeroRow() >= getRowsCount() - 1)
		{
			return false;
		}

		int temp = puzzleArray[zeroRow + 1][zeroColumn];
		puzzleArray[zeroRow + 1][zeroColumn] = 0;
		puzzleArray[zeroRow][zeroColumn] = temp;
		setZeroRow(zeroRow + 1);

		return true;
	}

	// metoda sprawdzajaca czy stan jest stanem oczekiwanym
	public boolean isGoalState()
	{
		for (int i = 0; i < rowsCount; i++)
		{
			for (int j = 0; j < columnCount; j++)
			{
				if (puzzleArray[i][j] != goalState[i][j])
				{
					isGoalState = false;
					return false;
				}
			}
		}
		isGoalState = true;
		return true;
	}

	public int getNumber(int row, int column)
	{
		return puzzleArray[row][column];
	}

	public void setZeroColumn(int zeroColumn)
	{
		this.zeroColumn = zeroColumn;
	}

	public int getZeroColumn()
	{
		return zeroColumn;
	}

	public void setZeroRow(int zeroRow)
	{
		this.zeroRow = zeroRow;
	}

	public int getZeroRow()
	{
		return zeroRow;
	}

	public int getRowsCount()
	{
		return rowsCount;
	}

	public int getColumnCount()
	{
		return columnCount;
	}

	public void setColumnCount(int columnCount)
	{
		this.columnCount = columnCount;
	}

	public int[][] getPuzzleArray()
	{

		return this.puzzleArray;
	}

	public int[][] getGoalState()
	{
		return goalState;
	}

	public static Puzzle getInstance()
	{
		return instance;
	}

	// przeciaziny toString sluzacy do wizualizacji rozwiazywania zagadki
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		for (int n = 0; n < puzzleArray.length; n++)
		{
			for (int j = 0; j < puzzleArray[n].length; j++)
			{
				builder.append(puzzleArray[n][j]).append(" ");
			}
			builder.append(newLine);
		}

		return builder.toString();
	}

	// przeciazony equals sluzacy sprawdzaniu czy stan znajduje sie juz na kolejce
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Puzzle)
		{
			Puzzle puzzle = (Puzzle) o;
			if (puzzle.getRowsCount() != this.getRowsCount())
			{
				return false;
			}
			int[][] tempArray = puzzle.getPuzzleArray();

			for (int i = 0; i < getRowsCount(); i++)
			{
				for (int j = 0; j < getRowsCount(); j++)
				{
					if (tempArray[i][j] != puzzleArray[i][j])
					{
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
}