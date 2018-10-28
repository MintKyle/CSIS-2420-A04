/******************************************************************************
 *  Name:     
 *
 *  Partner Name:      
 *
 *  Hours to complete assignment (optional):
 *
 ******************************************************************************/

Programming Assignment 4: Slider Puzzle


/******************************************************************************
 *  Explain briefly how you implemented the Board data type.
 *****************************************************************************/




/******************************************************************************
 *  Explain briefly how you represented a search node
 *  (board + number of moves + previous search node).
 *****************************************************************************/
a single class, with a link to the previous node, the board state, 
 and an integer tracking how many moves it took to reach that node






/******************************************************************************
 *  Explain briefly how you detected unsolvable puzzles.
 *
 *  What is the order of growth of the running time of your isSolvable()
 *  method as function of the board size n? Recall that with order-of-growth
 *  notation, you should discard leading coefficients and lower-order terms,
 *  e.g., n log n or n^3.

 *****************************************************************************/

Description:



Order of growth of running time:



/******************************************************************************
 *  For each of the following instances, give the minimum number of moves to
 *  solve the instance (as reported by your program). Also, give the amount
 *  of time your program takes with both the Hamming and Manhattan priority
 *  functions. If your program can't solve the instance in a reasonable
 *  amount of time (say, 5 minutes) or memory, indicate that instead. Note
 *  that your program may be able to solve puzzle[xx].txt even if it can't
 *  solve puzzle[yy].txt even if xx > yy.
 *****************************************************************************/


                 min number          seconds
     instance     of moves     Hamming     Manhattan
   ------------  ----------   ----------   ----------
   puzzle28.txt 
   puzzle30.txt 
   puzzle32.txt 
   puzzle34.txt 
   puzzle36.txt 
   puzzle38.txt 
   puzzle40.txt 
   puzzle42.txt 



/******************************************************************************
 *  If you wanted to solve random 4-by-4 or 5-by-5 puzzles, which
 *  would you prefer: a faster computer (say, 2x as fast), more memory
 *  (say 2x as much), a better priority queue (say, 2x as fast),
 *  or a better priority function (say, one on the order of improvement
 *  from Hamming to Manhattan)? Why?
 *****************************************************************************/
a better priority function, the biggest issue so far that I've determined is
that way too many board objects are getting created and tracked. Using a hashset
has proved difficult as the cost ends up more N than 1 due to poor default hashing implementation




/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
is extremely slow for some reason >.>" (maybe it's the garbage collector call?)


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
I personally did not get any help




/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
on the 50 puzzle, the priority function was unable to properly prioritize the boards,
 and so we got stuck at 14 boards. if we didn't, then the application became unresponsive at 20 due to memory allocation issues.

/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/
We used Github to manage the project files, Kyle did the board class, I did the solver class






/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/
