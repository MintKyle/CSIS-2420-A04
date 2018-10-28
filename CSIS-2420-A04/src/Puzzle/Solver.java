package Puzzle;

import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private final Stack<Board> solution;
	
	public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
    	if (initial == null)
    		throw new NullPointerException("initial");
    	
    	if (!initial.isSolvable())
    		throw new IllegalArgumentException("Board is unsolvable!");
    	solution = solve(initial);
    }
    
    public int moves()                     // min number of moves to solve initial board
    {
    	return solution.size()-1;
    }
    
    public Iterable<Board> solution()      // sequence of boards in a shortest solution
    {
    	return solution;
    }
    
    private Stack<Board> solve(Board initial)
    {
    	MinPQ<BoardEntry> entries = new MinPQ<BoardEntry>(new ManhattanComparator());
    	entries.insert(new BoardEntry(initial, null));
    	
    	BoardEntry fastest = null;
    	int traversed = 0;
    	
    	if (initial.isGoal())
    		fastest = entries.delMin();
    	
    	//HashMap<Board, Integer> checked = new HashMap<Board, Integer>();
    	
    	main: while(!entries.isEmpty())
    	{
    		BoardEntry entry = entries.delMin();
    		Board prev;
    		if (entry.prev != null)
    			prev = entry.prev.board;
    		else
    			prev = null;
    		
    		for(Board b : entry.board.neighbors())
    		{
    			traversed++;
    			if (traversed % 100 == 99)
    				System.gc();
    			
    			if (b.equals(prev))
    				continue;
    			
//    			if (fastest == null || entry.moves+1 < fastest.moves)
//    			{
				if (b.isGoal())
				{
					fastest = new BoardEntry(b, entry);
					break main;
				}
				else
					entries.insert(new BoardEntry(b, entry));
//    			}
    		}
    	}
    	
    	Stack<Board> sequence = new Stack<>();
    	
    	while(fastest != null)
    	{
    		sequence.push(fastest.board);
    		fastest = fastest.prev;
    	}
    	
    	StdOut.println("Boards Checked: " + traversed);
    	
    	return sequence;
    }

    public static void main(String[] args) // solve a slider puzzle (given below) 
    {
    	String prefix = "ftp://ftp.cs.princeton.edu/pub/cs226/8puzzle/";
    	String[] puzzles = new String[] {
			"puzzle28.txt",
			"puzzle30.txt",
			"puzzle32.txt",
			"puzzle34.txt",
			"puzzle36.txt",
			"puzzle38.txt",
			"puzzle40.txt",
			"puzzle42.txt"
    	};
    	
    	for(String puzzle : puzzles)
    	{
    		long time = System.currentTimeMillis();
    		Board.main(prefix + puzzle);
    		time = System.currentTimeMillis() - time;
    		
    		System.out.printf("%s Time: %dms%n", puzzle, time);
    	}
    }
    
    private class BoardEntry
    {
    	private BoardEntry(Board board, BoardEntry prev)
    	{
    		this.board = board;
    		if (prev == null)
    			this.moves = 0;
    		else
    			this.moves = prev.moves + 1;
    		this.prev = prev;
    	}
    	private final int moves;
    	private final Board board;
    	private final BoardEntry prev;
    }
    
    private class HammingComparator implements Comparator<BoardEntry>
    {

		@Override
		public int compare(BoardEntry arg0, BoardEntry arg1) {
			return (arg0.board.hamming() + arg0.moves) - (arg1.board.hamming() + arg1.moves);
		}
    	
    }
    
    private class ManhattanComparator implements Comparator<BoardEntry>
    {
		@Override
		public int compare(BoardEntry arg0, BoardEntry arg1) {
			return (arg0.board.manhattan() + arg0.moves) - (arg1.board.manhattan() + arg1.moves);
		}
    }

}
