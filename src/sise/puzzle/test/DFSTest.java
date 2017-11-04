package sise.puzzle.test;

import java.util.Arrays;
import java.util.Collections;

import sise.puzzle.Puzzle;
import sise.puzzle.PuzzleMaker;
import sise.puzzle.solution.DFSSolution;
import sise.puzzle.solution.PuzzleSolution;

/**
 * Created by bartoszpietrzak on 11/05/2017.
 */
public class DFSTest
{
	public static void main(String[] args)
	{
		Puzzle puzzle = new Puzzle();
		puzzle.initialize(4, 4);
		do
		{
			puzzle.randomize();
		}
		while(!PuzzleMaker.isSolvable(puzzle.getPuzzleArray()));
		PuzzleSolution sol = DFSSolution.getInstance();

		String[] orderPermutation = {"D", "G", "L", "P"};
		Collections.shuffle(Arrays.asList(orderPermutation));

		sol.solve(puzzle, orderPermutation, true);
	}
}
