package sise.puzzle;

import static java.lang.System.exit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import sise.puzzle.solution.AStarSolution;
import sise.puzzle.solution.BFSSolution;
import sise.puzzle.solution.DFSSolution;
import sise.puzzle.solution.PuzzleSolution;

/**
 * Created by bartoszpietrzak on 08/05/2017.
 */
public class PuzzleMaker
{
	// wywolanie metod pobierajacych dane oraz wywolanie odpowiedniego algorytmu na podstawie parametrow wejsciowych
	public static void solve(String[] args, boolean showPuzzle)
	{
		String arg0 = args[0];
		String arg1 = args[1];
		String[] order = null;
		String heuristicId;
		if (!(arg0.isEmpty() && arg0.equals("b") || arg0.equals("bfs") || arg0.equals("d") || arg0.equals("dfs") || arg0.equals("n") || arg0.equals("nn")))
		{
			throw new UnsupportedOperationException();
		}
		else if (arg0.equals("n") || arg0.equals("nn") && arg1 != null)
		{
			if (arg1.equals("1") || arg1.equals("2"))
			{
				heuristicId = args[1];
				aStar(heuristicId, showPuzzle);
			}
		}
		else
		{
			order = checkOrder(arg1);
		}
		if (arg0.equals("bfs") || arg0.equals("b"))
		{
			bfs(order, showPuzzle);
		}
		else if (arg0.equals("dfs") || arg0.equals("d"))
		{
			dfs(order, showPuzzle);
		}
	}

	// A*
	private static void aStar(String heuristicId, boolean showPuzzle)
	{
		Puzzle puzzle = getPuzzleState();

		if (puzzle == null)
		{
			System.out.println("-1");
			exit(1);
		}

		AStarSolution astar = new AStarSolution(new AStarPuzzleState(puzzle), Integer.valueOf(heuristicId));
		System.out.println(astar.findPath(showPuzzle));

		exit(0);
	}

	// BFS
	private static void bfs(String[] order, boolean showPuzzle)
	{
		Puzzle puzzle = getPuzzleState();

		if (puzzle == null)
		{
			System.out.println("-1");
			exit(1);
		}

		PuzzleSolution sol = BFSSolution.getInstance();
		sol.solve(puzzle, order, showPuzzle);

		exit(0);
	}

	// DFS
	private static void dfs(String[] order, boolean showPuzzle)
	{
		Puzzle puzzle = getPuzzleState();

		if (puzzle == null)
		{
			System.out.println("-1");
			exit(1);
		}

		PuzzleSolution sol = DFSSolution.getInstance();
		sol.solve(puzzle, order, showPuzzle);

		exit(0);
	}

	// wprowadzenie danych wejsciowych przez uzytkownika
	private static Puzzle getPuzzleState()
	{
		try (BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in)))
		{
			System.out.println("W: ");
			int w = Integer.valueOf(scanner.readLine().trim());
			System.out.println("K: ");
			int k = Integer.valueOf(scanner.readLine().trim());

			int[][] puzzleTable = new int[w][k];

			for (int i = 0; i < w; i++)
			{
				String[] columns = null;

				while (columns == null)
				{
					String row = null;
					System.out.println("W " + String.valueOf(i+1) + ": ");
					row = scanner.readLine();
					columns = getColumnsFromInput(row, k);
				}
				for (int j = 0; j < k; j++)
				{
					puzzleTable[i][j] = Integer.valueOf(columns[j]);
				}
			}

			if(!isSolvable(puzzleTable))
			{
				return null;
			}

			Puzzle puzzle = Puzzle.getInstance();
			puzzle.initialize(puzzleTable, w, k);

			return puzzle;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	// walidacja wiersza pobranego od uzytkownika
	private static String[] getColumnsFromInput(String row, int k)
	{
		String[] columns = row.split(" ");

		if (columns.length != k)
		{
			return null;
		}

		return columns;
	}

	// walidacja porzadku przekazanego w parametrach wejsciowych
	private static String[] checkOrder(String order)
	{
		if (order.contains("R"))
		{
			return new String[]{"R"};
		}

		else if (!(order.contains("L") && order.contains("P") && order.contains("G") && order.contains("D")))
		{
			return null;
		}

		String[] splittedOrder = order.split(",");
		if (splittedOrder.length != 4)
		{
			return null;
		}
		else
		{
			return splittedOrder;
		}
	}

	public static boolean isSolvable(int[][] twoDimensionalPuzzle)
	{
		// jedno wymiarowa tablica w miejsce tablicy dwuwymiarowej
		int[] puzzle;
		List<Integer> oneDimensionPuzzle = new ArrayList<>();

		for (int i = 0; i < twoDimensionalPuzzle.length; i++)
		{
			for (int j = 0; j < twoDimensionalPuzzle[0].length; j++)
			{
				oneDimensionPuzzle.add(twoDimensionalPuzzle[i][j]);
			}
		}

		puzzle = new int[oneDimensionPuzzle.size()];
		for (int i = 0; i < oneDimensionPuzzle.size(); i++)
		{
			puzzle[i] = oneDimensionPuzzle.get(i);
		}

		int parity = 0;
		int gridWidth = twoDimensionalPuzzle[0].length;
		int row = 0;
		int zeroRow = 0;

		for (int i = 0; i < puzzle.length; i++)
		{
			// ustalenie gdzie konczy sie wiersz
			if (i % gridWidth == 0)
			{
				row++;
			}

			// ustawienie wiersza zawierającego 0
			if (puzzle[i] == 0)
			{
				zeroRow = row; // save the row on which encountered
				continue;
			}

			// inkrementacja wartosci jezeli element poprzedzajacy
			// jest wiekszy od nastepnego
			for (int j = i + 1; j < puzzle.length; j++)
			{
				if (puzzle[i] > puzzle[j] && puzzle[j] != 0)
				{
					parity++;
				}
			}
		}

		// jezeli szerokosc ukladanki jest liczba parzysta
		if (gridWidth % 2 == 0)
		{
			// wiersz, w ktorym znajduje sie 0 jest parzysty
			if (zeroRow % 2 == 0)
			{
				// ukladanka jest rozwiazywalna jezeli wartosc parity jest parzysta
				return parity % 2 == 0;
			}
			// wiersz, w ktorym znajduje sie 0 nie jest parzysty
			else
			{
				// ukladanka jest rozwiazywalna jezeli wartosc parity jest nieparzysta
				return parity % 2 != 0;
			}
		}
		// jezeli szerokosc ukladanki jest liczba nieparzysta
		else
		{
			// ukladanka jest rozwiazywalna jezeli wartosc parity jest parzysta
			return parity % 2 == 0;
		}
	}
}
