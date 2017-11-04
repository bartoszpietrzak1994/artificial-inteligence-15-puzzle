package sise.puzzle;

/**
 * Created by bartoszpietrzak on 03/05/2017.
 */
public class PuzzleMove
{
	private static final String NEW_STATE_ERROR = "Error creating a new down-shifted puzzle state.";
	private static final String ACCESS_ERROR = "Error accessing new puzzle instance.";

	// metoda odpowiadajaca za ruch w dol oraz zwrocenie nowego stanu jesli ruch sie powiodl
	public static PuzzleState down(PuzzleState s)
	{
		if (s.getZeroRow() >= s.getRowsCount() - 1)
		{
			return null;
		}

		PuzzleState nextState = null;

		try
		{
			nextState = s.getClass().newInstance();
		}
		catch (InstantiationException e)
		{
			System.err.println(NEW_STATE_ERROR);
		}
		catch (IllegalAccessException e)
		{
			System.err.println(ACCESS_ERROR);
		}

		nextState.copy(s);

		int temp = s.getNumber(s.getZeroRow() + 1, s.getZeroColumn());
		nextState.setNumber(s.getZeroRow() + 1, s.getZeroColumn(), 0);
		nextState.setNumber(s.getZeroRow(), s.getZeroColumn(), temp);
		nextState.setZeroRow(s.getZeroRow() + 1);
		nextState.setMove('D');
		nextState.setPrev(s);
		nextState.setLevel(s.getLevel() + 1);

		return nextState;
	}

	// metoda odpowiadajaca za ruch w gore oraz zwrocenie nowego stanu jesli ruch sie powiodl
	public static PuzzleState up(PuzzleState s)
	{
		if (s.getZeroRow() <= 0)
		{
			return null;
		}

		PuzzleState nextState = null;

		try
		{
			nextState = s.getClass().newInstance();
		}
		catch (InstantiationException e)
		{
			System.err.println(NEW_STATE_ERROR);
		}
		catch (IllegalAccessException e)
		{
			System.err.println(ACCESS_ERROR);
		}

		nextState.copy(s);
		int temp = s.getNumber(s.getZeroRow() - 1, s.getZeroColumn());
		nextState.setNumber(s.getZeroRow() - 1, s.getZeroColumn(), 0);
		nextState.setNumber(s.getZeroRow(), s.getZeroColumn(), temp);
		nextState.setZeroRow(s.getZeroRow() - 1);
		nextState.setMove('G');
		nextState.setPrev(s);
		nextState.setLevel(s.getLevel() + 1);

		return nextState;
	}

	// metoda odpowiadajaca za ruch w lewo oraz zwrocenie nowego stanu jesli ruch sie powiodl
	public static PuzzleState left(PuzzleState s)
	{
		if (s.getZeroColumn() <= 0)
		{
			return null;
		}

		PuzzleState nextState = null;

		try
		{
			nextState = s.getClass().newInstance();
		}
		catch (InstantiationException e)
		{
			System.err.println(NEW_STATE_ERROR);
		}
		catch (IllegalAccessException e)
		{
			System.err.println(ACCESS_ERROR);
		}

		nextState.copy(s);

		int temp = s.getNumber(s.getZeroRow(), s.getZeroColumn() - 1);
		nextState.setNumber(s.getZeroRow(), s.getZeroColumn() - 1, 0);
		nextState.setNumber(s.getZeroRow(), s.getZeroColumn(), temp);
		nextState.setZeroColumn(s.getZeroColumn() - 1);
		nextState.setMove('L');
		nextState.setPrev(s);
		nextState.setLevel(s.getLevel() + 1);

		return nextState;
	}

	// metoda odpowiadajaca za ruch w prawo oraz zwrocenie nowego stanu jesli ruch sie powiodl
	public static PuzzleState right(PuzzleState s)
	{
		if (s.getZeroColumn() >= s.getColumnCount() - 1)
		{
			return null;
		}

		PuzzleState nextState = null;

		try
		{
			nextState = s.getClass().newInstance();
		}
		catch (InstantiationException e)
		{
			System.err.println(NEW_STATE_ERROR);
		}
		catch (IllegalAccessException e)
		{
			System.err.println(ACCESS_ERROR);
		}

		nextState.copy(s);
		int temp = s.getNumber(s.getZeroRow(), s.getZeroColumn() + 1);
		nextState.setNumber(s.getZeroRow(), s.getZeroColumn() + 1, 0);
		nextState.setNumber(s.getZeroRow(), s.getZeroColumn(), temp);
		nextState.setZeroColumn(s.getZeroColumn() + 1);
		nextState.setMove('P');
		nextState.setPrev(s);
		nextState.setLevel(s.getLevel() + 1);

		return nextState;
	}
}
