package sise.puzzle.test;

import sise.puzzle.AStarPuzzleState;
import sise.puzzle.Puzzle;
import sise.puzzle.PuzzleMaker;
import sise.puzzle.solution.AStarSolution;

/**
 * Created by bartoszpietrzak on 06/05/2017.
 */
public class AStarTest
{
	public static void main(String[] args) throws Exception
	{
		Puzzle puzzle = new Puzzle();
		puzzle.initialize(3, 3);
		do
		{
			puzzle.randomize();
		}
		while(!PuzzleMaker.isSolvable(puzzle.getPuzzleArray()));
		AStarPuzzleState initialAStarPuzzleState = new AStarPuzzleState(puzzle);
		AStarSolution aStarSolution = new AStarSolution(initialAStarPuzzleState, 1);
		String path = aStarSolution.findPath(true);
		System.out.println(path);
	}
}
