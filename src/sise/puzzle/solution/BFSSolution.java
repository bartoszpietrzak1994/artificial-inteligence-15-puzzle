package sise.puzzle.solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import sise.puzzle.Puzzle;
import sise.puzzle.PuzzleMove;
import sise.puzzle.PuzzleState;

public class BFSSolution extends PuzzleSolution
{
	private static BFSSolution instance = new BFSSolution();

	// set stanow
	private Set<PuzzleState> stateSet = new HashSet<PuzzleState>();

	// kolejka stanow
	private Queue<PuzzleState> stateQueue = new LinkedList<PuzzleState>();

	private BFSSolution() {}

	public static BFSSolution getInstance()
	{
		return instance;
	}

	private void bfs(PuzzleState state, String[] order)
	{
		stateSet.clear();
		stateQueue.clear();
    
    	// dodanie stanu do setu
		stateSet.add(state);

		// dodanie stanu do kolejki
		stateQueue.add(state);

    	// algorytm bfs
		while (!stateQueue.isEmpty())
		{
      		// sciagniecie najnowszego stanu z kolejki
			state = stateQueue.poll();

      		// sprawdzenie, czy stan nie jest stanem oczekiwanym
			if (state.isGoalState())
			{
				goal = state;
				break;
			}

			// wykonanie odpowiedniego ruchu w zaleznosci od ustalonego porzadku
			makeMoveAccordingToOrder(state, order);
		}
	}

	private void makeMoveAccordingToOrder(PuzzleState state, String[] order)
	{
		PuzzleState newState;
		for (int i = 0; i < order.length; i++)
		{
			switch(order[i])
			{
				case "L":
				{
					newState = PuzzleMove.left(state);
					addNewState(newState);
					break;
				}
				case "P":
				{
					newState = PuzzleMove.right(state);
					addNewState(newState);
					break;
				}
				case "G":
				{
					newState = PuzzleMove.up(state);
					addNewState(newState);
					break;
				}
				case "D":
				{
					newState = PuzzleMove.down(state);
					addNewState(newState);
					break;
				}
				case "R":
				{
					String[] orderPermutation = {"D", "G", "L", "P"};
					Collections.shuffle(Arrays.asList(orderPermutation));
					makeMoveAccordingToOrder(state, orderPermutation);
					break;
				}
			}
		}
	}

	private void addNewState(PuzzleState state)
	{
		// sprawdzenie czy stan istnieje oraz czy set stanow go nie zawiera
		if (state != null && !stateSet.contains(state))
		{
			// daodanie stanu do setu i kolejki
			stateSet.add(state);
			stateQueue.add(state);
		}
	}

	public void solve(Puzzle puzzle, String[] order, boolean showPuzzle)
	{
		goal = null;
		PuzzleState state = new PuzzleState(puzzle);

		// przeszukiwanie grafu algorytmem bfs
		bfs(state, order);

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