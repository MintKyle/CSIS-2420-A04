package Puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {

	private int N;
	private int[][] tiles;
	private boolean evenboard;

	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		N = blocks.length;
		tiles = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				tiles[i][j] = blocks[i][j];
				//System.out.println(tiles[i][j]);
			}
		}
	}

	// board size N
	public int size() {

		return N;
	}

	// number of blocks out of place
	public int hamming() {
		int hamming = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (tiles[i][j] != 0 && tiles[i][j] != N * i + j + 1) {
					hamming++;
				}
			}
		}
		//System.out.println("hamming: " + hamming);
		return hamming;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int manhattan = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (tiles[i][j] != 0 && tiles[i][j] != N * i + j + 1) {
					int row = Math.abs((tiles[i][j] - 1) / N - i);
					int col = Math.abs((tiles[i][j] - 1) % N - j);
					manhattan += row + col;
				}
			}
		}
		// System.out.println("manhattan: " + manhattan);
		return manhattan;
	}

	// is this board the goal board?
	public boolean isGoal() {
		return hamming() == 0;
	}

	// is this board solvable?
	public boolean isSolvable() {

		if ((N) % 2 == 0) {
			evenboard = true;
		}

		int inversions = 0;
		int blankRow = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {

				for (int i1 = i; i1 < N; i1++) {
					for (int j1 = -1; j1 < N - 1; j1++) {
						if (tiles[i][j] == 0) {
							blankRow = i;
						}
						if (i1 == i && j1 <= j - 1 || tiles[i][j] == 0 || tiles[i1][j1 + 1] == 0) {
							continue;
						}
						// System.out.println("tile: " + tiles[i][j] + " & " + tiles[i1][j1+1]);
						if (tiles[i][j] > tiles[i1][j1 + 1]) {
							// System.out.println("Inversion at tile: " + tiles[i][j] + " & " +
							// tiles[i1][j1+1]);
							inversions++;
						}
					}
				}
			}
		}
		System.out.println("Inversions: " + inversions + " blank row: " + blankRow);

		if (evenboard == true) {
			// System.out.println("The board is even ");
			int sum = inversions + blankRow;
			if (sum % 2 != 0) {
				// System.out.println("solvable. returning true ");
				return true;
			}
		} else {
			System.out.println("The board is odd ");
			if (inversions % 2 == 0) {
				// System.out.println("solvable. returning true ");
				return true;
			}
		}
		// System.out.println("unsolvable, returning false");
		return false;
	}

	// does this board equal y?
	public boolean equals(Object y) {
		if (this == y)
			return true;
		if (y == null)
			return false;
		if (this.getClass() != y.getClass())
			return false;
		Board that = (Board) y;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (this.tiles[i][j] != that.tiles[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		int row = 0;
		int col = 0;
		Stack<Board> neighborsStack = new Stack<Board>();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (tiles[i][j] == 0) {
					row = i;
					col = j;
				}
			}
		}

		if (row > 0) {
			neighborsStack.push(swap(row, col, row - 1, col));

		}
		if (row < N - 1) {
			neighborsStack.push(swap(row, col, row + 1, col));

		}
		if (col > 0) {
			neighborsStack.push(swap(row, col, row, col - 1));

		}
		if (col < N - 1) {
			neighborsStack.push(swap(row, col, row, col + 1));

		}

		return neighborsStack;
	}

	private Board swap(int i, int j, int i1, int j1) {
		int[][] tiles = deepCloneTiles();
		
		
		int temp = tiles[i][j];
		tiles[i][j] = tiles[i1][j1];
		tiles[i1][j1] = temp;
		return new Board(tiles);
	}
	
	private int[][] deepCloneTiles()
	{
		int[][] tiles = new int[N][N];
		for(int i = 0; i < N; i++)
			System.arraycopy(this.tiles[i], 0, tiles[i], 0, N);
		
		return tiles;
	}

	// string representation of this board (in the output format specified below)
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", tiles[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	// unit tests (not graded)
	public static void main(String... args) {

		// create initial board from file
		// In in = new In(args[0]);
		In in;
		if (args.length > 0)
			in = new In(args[0]);
		else
			in = new In("ftp://ftp.cs.princeton.edu/pub/cs226/8puzzle/puzzle36.txt");
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				blocks[i][j] = in.readInt();
				// System.out.println(blocks[i][j]);
			}
		}

		Board initial = new Board(blocks);

		// check if puzzle is solvable; if so, solve it and output solution
		if (initial.isSolvable()) {
			Solver solver = new Solver(initial);
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}

		// if not, report unsolvable
		else {
			StdOut.println("Unsolvable puzzle");
		}
	}
}