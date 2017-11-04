package sise.puzzle.solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import sise.puzzle.Puzzle;
import sise.puzzle.PuzzleMove;
import sise.puzzle.PuzzleState;

public class DFSSolution extends PuzzleSolution
{
	// set zawierajacy stany
	protected Set<PuzzleState> stateSet = new HashSet<PuzzleState>();

	// maksymalny poziom grafu
	public static final int MAX_DEPTH = 100;

	private static DFSSolution instance = new DFSSolution();

	private DFSSolution() {}

	public static DFSSolution getInstance()
	{
		return instance;
	}

	// rekurencyjna metoda przeszukujaca graf do ustalonego poziomu, z okreslonym porzadkiem
	void iterativeDeepening(PuzzleState state, String[] order, int depthLimit)
	{
		// wyczyszczenie setu stanow i dodanie stanu poczatkowego
		stateSet.clear();
		stateSet.add(state);
    
   		 // rekurencyjne przeszukiwanie grafu do okreslonego poziomu
		for (int i = 1; i <= depthLimit; i++)
		{
			dfs(state, order, i);
			if (goal != null)
			{
				return;
			}
		}
	}

	// algorytm DFS
	private void dfs(PuzzleState state, String[] order, int depth)
	{
		if (depth < 0)
		{
			return;
		}

    	/* When input state is the goal state, set it goal to input state. */
		if (state.isGoalState())
		{
			goal = state;
		}

		if (goal != null)
		{
			return;
		}

		makeMoveAccoringToOrder(state, order, depth);
	}

	// wykonywanie ruchow w zaleznosci od ustalonego porzadku
	private void makeMoveAccoringToOrder(PuzzleState state, String[] order, int depth)
	{
		PuzzleState newState;
		for (int i = 0; i < order.length; i++)
		{
			switch(order[i])
			{
				case "L":
				{
					newState = PuzzleMove.left(state);
					addNewState(newState, order, depth);
					break;
				}
				case "P":
				{
					newState = PuzzleMove.right(state);
					addNewState(newState, order, depth);
					break;
				}
				case "G":
				{
					newState = PuzzleMove.up(state);
					addNewState(newState, order, depth);
					break;
				}
				case "D":
				{
					newState = PuzzleMove.down(state);
					addNewState(newState, order, depth);
					break;
				}
				case "R":
				{
					String[] orderPermutation = {"D", "G", "L", "P"};
					Collections.shuffle(Arrays.asList(orderPermutation));
					makeMoveAccoringToOrder(state, orderPermutation, depth);
				}
			}
		}
	}

	private void addNewState(PuzzleState state, String[] order, int depth)
	{
		// sprawdzenie czy stan istnieje i czy nie zostal juz dodany do setu
		if (state != null && !stateSet.contains(state))
		{
			stateSet.add(state);

			// rekurencyjne wywolanie dfs, w tym samym kierunku
			dfs(state, order, depth - 1);

			if (goal != null)
			{
				return;
			}

      		// usuniecie stanu zawartego juz wczesniej w secie
			stateSet.remove(state);
		}
	}

	public void solve(Puzzle puzzle, String[] order, boolean showPuzzle)
	{
		goal = null;
		PuzzleState state = new PuzzleState(puzzle);
    
    	// rekurencyjne przeszukiwanie grafu algorytmem dfs
		iterativeDeepening(state, order, MAX_DEPTH);

    	// w zaleznosci od programu - prezentacja stanow ukladanki lub krokow prowadzacych do jej ulozenia
		if (showPuzzle)
		{
			getSequence();
		}
		else
		{
			System.out.println(getMoves());
		}
	}
}
