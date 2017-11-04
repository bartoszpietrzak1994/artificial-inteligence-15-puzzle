package sise.puzzle.solution;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import sise.puzzle.AStarPuzzleState;
import sise.puzzle.PuzzleMove;

public class AStarSolution
{
	// wartosc c
	private static int HORIZONTAL_VERTICAL_COST = 1;

	// lista nieodwiedzonych stanow ukladanki
	private PriorityQueue<AStarPuzzleState> openList;

	// lista odwiedzonych stanow ukladanki
	private List<AStarPuzzleState> closedList;

	// pierwszy stan ukladanki
	private AStarPuzzleState initialAStarPuzzleState;

	// id heurystyki
	private int heuristicId;

	// konstruktor algorytmu A*
	public AStarSolution(AStarPuzzleState initialState, int heuristicId)
	{
		this.initialAStarPuzzleState = initialState;
		this.openList = new PriorityQueue<>((AStarPuzzleState0, AStarPuzzleState1) -> AStarPuzzleState0.getF() < AStarPuzzleState1.getF() ? -1 : AStarPuzzleState0
				.getF() > AStarPuzzleState1.getF() ? 1 : 0);
		this.heuristicId = heuristicId;
		calculateHeuristic(initialState);
		this.closedList = new ArrayList<>();
	}

	// obliczanie heurystyki
	private void calculateHeuristic(AStarPuzzleState state)
	{
		switch (heuristicId)
		{
			case 1:
				state.blocksOutOfPlaceHeuristic();
				break;
			case 2:
				state.manhattanSumHeuristic();
				break;
		}
	}

	// glowny algorytm A*
	public String findPath(boolean showPuzzle)
	{
		openList.add(initialAStarPuzzleState);
		while (!isEmpty(openList))
		{
			AStarPuzzleState currentAStarPuzzleState = openList.poll();
			closedList.add(currentAStarPuzzleState);
			if (isFinalState(currentAStarPuzzleState))
			{
				return getPath(currentAStarPuzzleState, showPuzzle);
			}
			else
			{
				addAdjacentStates(currentAStarPuzzleState);
			}
		}
		return "";
	}

	// metoda zwracajaca sciezke stanow
	private String getPath(AStarPuzzleState currentAStarPuzzleState, boolean showPuzzle)
	{
		AStarPuzzleState parent;
		StringBuilder sb = new StringBuilder();
		int i = 0;
		if (showPuzzle)
		{
			while ((parent = (AStarPuzzleState) currentAStarPuzzleState.getPrev()) != null)
			{
				sb.append(currentAStarPuzzleState.getMove()).append("\n");
				sb.append(currentAStarPuzzleState).append("\n\n");
				currentAStarPuzzleState = parent;
			}
		}
		else
		{
			while ((parent = (AStarPuzzleState) currentAStarPuzzleState.getPrev()) != null)
			{
				sb.append(currentAStarPuzzleState.getMove()).append(" ");
				currentAStarPuzzleState = parent;
				i++;
			}
		}

		StringBuilder numberOfMoves = new StringBuilder().append(Integer.valueOf(i).toString());
		String reversedNumber = numberOfMoves.reverse().toString();

		return showPuzzle ? sb.toString() : sb.append("\n").append(reversedNumber).reverse().toString();
	}

	// dodanie stanow sasiednich do kolejki priorytetowej
	private void addAdjacentStates(AStarPuzzleState currentAStarPuzzleState)
	{
		addAdjacentUpperRow(currentAStarPuzzleState);
		addAdjacentMiddleRow(currentAStarPuzzleState);
		addAdjacentLowerRow(currentAStarPuzzleState);
	}

	// proba przesuniecia elementu w dol
	private void addAdjacentLowerRow(AStarPuzzleState currentAStarPuzzleState)
	{
		int row = currentAStarPuzzleState.getZeroRow();
		int lowerRow = row + 1;
		if (lowerRow < currentAStarPuzzleState.getRowsCount())
		{
			AStarPuzzleState adjacentAStarPuzzleState = (AStarPuzzleState) PuzzleMove.down(currentAStarPuzzleState);
			if (adjacentAStarPuzzleState == null)
			{
				return;
			}
			calculateHeuristic(adjacentAStarPuzzleState);
			checkState(currentAStarPuzzleState, adjacentAStarPuzzleState, HORIZONTAL_VERTICAL_COST);
		}
	}

	// proba przesuniecia elementu w lewo i w prawo
	private void addAdjacentMiddleRow(AStarPuzzleState currentAStarPuzzleState)
	{
		int col = currentAStarPuzzleState.getZeroColumn();
		if (col - 1 >= 0)
		{
			AStarPuzzleState adjacentAStarPuzzleState = (AStarPuzzleState) PuzzleMove.left(currentAStarPuzzleState);
			calculateHeuristic(adjacentAStarPuzzleState);
			if (adjacentAStarPuzzleState == null)
			{
				return;
			}
			checkState(currentAStarPuzzleState, adjacentAStarPuzzleState, HORIZONTAL_VERTICAL_COST);
		}
		if (col + 1 < currentAStarPuzzleState.getRowsCount())
		{
			AStarPuzzleState adjacentAStarPuzzleState = (AStarPuzzleState) PuzzleMove.right(currentAStarPuzzleState);
			if (adjacentAStarPuzzleState == null)
			{
				return;
			}
			calculateHeuristic(adjacentAStarPuzzleState);
			checkState(currentAStarPuzzleState, adjacentAStarPuzzleState, HORIZONTAL_VERTICAL_COST);
		}

	}

	// proba przesuniecia elemenu w gore
	private void addAdjacentUpperRow(AStarPuzzleState currentAStarPuzzleState)
	{
		int row = currentAStarPuzzleState.getZeroRow();
		int upperRow = row - 1;
		if (upperRow >= 0)
		{
			AStarPuzzleState adjacentAStarPuzzleState = (AStarPuzzleState) PuzzleMove.up(currentAStarPuzzleState);
			if (adjacentAStarPuzzleState == null)
			{
				return;
			}
			calculateHeuristic(adjacentAStarPuzzleState);
			checkState(currentAStarPuzzleState, adjacentAStarPuzzleState, HORIZONTAL_VERTICAL_COST);
		}
	}

	// sprawdzenie stanu
	private void checkState(AStarPuzzleState currentAStarPuzzleState, AStarPuzzleState adjacentAStarPuzzleState, int cost)
	{
		if (!getClosedList().contains(adjacentAStarPuzzleState))
		{
			if (!getOpenList().contains(adjacentAStarPuzzleState))
			{
				adjacentAStarPuzzleState.setState(currentAStarPuzzleState, cost);
				openList.add(adjacentAStarPuzzleState);
			}
			else
			{
				boolean changed = adjacentAStarPuzzleState.checkBetterPath(currentAStarPuzzleState, cost);
				if (changed)
				{
					openList.remove(adjacentAStarPuzzleState);
					openList.add(adjacentAStarPuzzleState);
				}
			}
		}
	}

	// porownanie obecnego stanu z oczekiwanym
	private boolean isFinalState(AStarPuzzleState currentAStarPuzzleState)
	{
		return currentAStarPuzzleState.isGoalState();
	}

	private boolean isEmpty(PriorityQueue<AStarPuzzleState> openList)
	{
		return openList.size() == 0;
	}

	public void setInitialAStarPuzzleState(AStarPuzzleState initialAStarPuzzleState)
	{
		this.initialAStarPuzzleState = initialAStarPuzzleState;
	}

	public PriorityQueue<AStarPuzzleState> getOpenList()
	{
		return openList;
	}

	public List<AStarPuzzleState> getClosedList()
	{
		return closedList;
	}
}
