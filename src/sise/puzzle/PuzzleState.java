package sise.puzzle;

public class PuzzleState
{
	protected int rowsCount;
	protected int columnCount;
	protected int[][] puzzleArray;
	protected static int[][] goalState;
	protected int zeroColumn;
	protected int zeroRow;
	protected int level;
	protected boolean isGoalState;
	protected char move;
	protected PuzzleState prev;

	PuzzleState()
	{
		this(Puzzle.getInstance());
	}

	// konstruktor stanu ukladanki
	public PuzzleState(Puzzle puzzle)
	{
		this.rowsCount = puzzle.getRowsCount();
		this.columnCount = puzzle.getColumnCount();
		goalState = puzzle.getGoalState();

		puzzleArray = new int[rowsCount][columnCount];
     
     	// kopia ukladanki do obecnego stanu
		for (int i = 0; i < rowsCount; i++)
		{
			for (int j = 0; j < columnCount; j++)
			{
				puzzleArray[i][j] = puzzle.getNumber(i, j);
			}
		}

		this.zeroRow = puzzle.getZeroRow();
		this.zeroColumn = puzzle.getZeroColumn();
		this.isGoalState = puzzle.isGoalState();
	}

	// kopia stanu ukladanki z innego stanu
	public void copy(PuzzleState state)
	{
		this.rowsCount = state.getRowsCount();
		this.columnCount = state.getColumnCount();
		this.zeroRow = state.getZeroRow();
		this.zeroColumn = state.getZeroColumn();
		this.isGoalState = state.isGoalState();

		puzzleArray = new int[rowsCount][columnCount];
    
    	// kopiuje ukladanke z innego stanu do obecnego
		for (int i = 0; i < rowsCount; i++)
		{
			for (int j = 0; j < columnCount; j++)
			{
				puzzleArray[i][j] = state.getNumber(i, j);
			}
		}
	}

	// pobranie dowolnego elementu ukladanki
	public int getNumber(int row, int column)
	{
		return this.puzzleArray[row][column];
	}

	// ustawienie dowolnego elementu ukladanki na wskazany element
	public void setNumber(int row, int column, int number)
	{
		this.puzzleArray[row][column] = number;
	}

	public char getMove()
	{
		return this.move;
	}

	public void setMove(char c)
	{
		this.move = c;
	}

	public PuzzleState getPrev()
	{
		return prev;
	}

	public void setPrev(PuzzleState s)
	{
		this.prev = s;
	}

	public void setZeroColumn(int column)
	{
		this.zeroColumn = column;
	}

	public void setZeroRow(int row)
	{
		this.zeroRow = row;
	}

	public int getZeroColumn()
	{
		return this.zeroColumn;
	}

	public int getZeroRow()
	{
		return this.zeroRow;
	}

	public int getRowsCount()
	{
		return this.rowsCount;
	}

	public int getColumnCount()
	{
		return this.columnCount;
	}

	public void setLevel(int n)
	{
		this.level = n;
	}

	public int getLevel()
	{
		return this.level;
	}

	// sprawdzenie czy obecny stan jest stanem oczekiwanym
	public boolean isGoalState()
	{
		int [][] goalState = new PuzzleGoalState(rowsCount, columnCount).getGoalState();
		for (int i = 0; i < rowsCount; i++)
		{
			for (int j = 0; j < columnCount; j++)
			{
      			/* When value at index is not goal value, not in goal state. */
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

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof PuzzleState)
		{
			PuzzleState state = (PuzzleState) o;

			if (state.getRowsCount() != this.getRowsCount())
			{
				return false;
			}
			for (int i = 0; i < rowsCount; i++)
			{
				for (int j = 0; j < columnCount; j++)
				{
					if (state.getNumber(i, j) != puzzleArray[i][j])
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