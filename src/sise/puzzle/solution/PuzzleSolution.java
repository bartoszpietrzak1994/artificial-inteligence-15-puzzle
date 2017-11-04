package sise.puzzle.solution;

import sise.puzzle.Puzzle;
import sise.puzzle.PuzzleState;

public abstract class PuzzleSolution
{
	// oczekiwany stan ukladanki
	protected PuzzleState goal;

	// abstrakcyjna metoda rozwiazujaca ukladanke
	public abstract void solve(Puzzle puzzle, String[] order, boolean showPuzzle);

	// metoda zwracajaca sekwencje ruchow po ulozeniu ukladanki
	public void getSequence()
	{
		// ustawienie obecnego stanu na wyjsciowy
		// parent bedzie sluzyl do iterowania po kolejnych stanach ukladanki
		PuzzleState current = goal, parent;
    
    	// builder sluzacy do dolaczania sekwencji ruchow
		StringBuilder builder = new StringBuilder();
    
    	// iteruje przez kolejne stany ukladanki
		while (true)
		{
			// jesli obecny stan jest poczatkowym, przerywamy petle
			if (current.getLevel() == 0)
			{
				if (current.getPrev() == null)
				{
					break;
				}
				continue;
			}
			else
			{
				System.out.println(current.getMove());
			}

			// wyswietlenie obecnego stanu w konsoli
			System.out.println(current.toString());

			// pobranie poprzedniego stanu
			parent = current.getPrev();

			// zalozenie, ze jesli poprzedni stan nie istnieje, dotarlismy do stanu poczatkowego
			if (current.getPrev() == null)
			{
				break;
			}

			// dodanie ruchu ukladanki do outputu
			builder.append(" ,").append(current.getMove());

			// ustawienie obecnego stanu na stan poprzedni
			current = parent;
		}

		// usuniecie oczekiwanego stanu
		goal = null;

		System.out.print(builder.reverse().toString());
	}

	// metoda zwracajaca ruchy wykonane przy rozwiazywaniu ukladanki
	public String getMoves()
	{
		// ustawienie obecnego stanu na wyjsciowy
		// parent bedzie sluzyl do iterowania po kolejnych stanach ukladanki
		PuzzleState current = goal, parent;

		// builder sluzacy do dolaczania sekwencji ruchow
		StringBuilder builder = new StringBuilder();

		int i = 0;
		if (current != null)
		{
			// iteruje przez kolejne stany ukladanki
			while (true)
			{
				// pobranie poprzedniego stanu
				parent = current.getPrev();

				// zalozenie, ze jesli poprzedni stan nie istnieje, dotarlismy do stanu poczatkowego
				if (parent == null)
				{
					break;
				}

				// dodanie ruchu ukladanki do outputu
				builder.append(" ,").append(current.getMove());

				// ustawienie obecnego stanu na stan poprzedni
				current = parent;
				i++;
			}
		}

		StringBuilder numberOfMoves = new StringBuilder().append(Integer.valueOf(i).toString());
		String reversedNumber = numberOfMoves.reverse().toString();

		return builder.append("\n").append(reversedNumber).reverse().toString();
	}
}
