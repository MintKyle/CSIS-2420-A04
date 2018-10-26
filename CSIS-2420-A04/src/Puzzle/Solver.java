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
    	
    	main: while(fastest == null && !entries.isEmpty())
    	{
    		Board prev;
    		BoardEntry entry = entries.delMin();
    		
    		if (entry.prev != null)
    			prev = entry.prev.board;
    		else
    			prev = null;
    		
    		for(Board b : entry.board.neighbors())
    		{
    			traversed++;
    			if (b.equals(prev))
    				continue;
    			
    			if (b.isGoal())
    			{
    				fastest = new BoardEntry(b, entry);
    				break main;
    			}
    			
    			entries.insert(new BoardEntry(b, entry));
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
