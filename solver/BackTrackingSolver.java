/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import grid.StdSudokuGrid;
import grid.SudokuGrid;


/**
 * Backtracking solver for standard Sudoku.
 */
public class BackTrackingSolver extends StdSudokuSolver
{
    // TODO: Add attributes as needed.
    StdSudokuGrid grid;

    public BackTrackingSolver() 
    {
        // TODO: any initialisation you want to implement.
    } // end of BackTrackingSolver()


    @Override
    public boolean solve(SudokuGrid grid) {
        
        this.grid = (StdSudokuGrid) grid;
        solveCell(0,0);
    
        return grid.validate();
    } // end of solve()

    private boolean solveCell(int row, int col)
    {
        int size = grid.getSize();
        for(int index = 0; index < size; ++index)
        {
            
            if(grid.getValueAt(row, col) == '\u0000')
            {

                grid.insertAt(row, col, grid.getOptionAt(index));
                
            }
            if(grid.validate())
            {
                if(col < size-1)
                {
                    if(!solveCell(row, col + 1))
                    {
                        grid.insertAt(row, col, '\u0000');
                    }
                    else
                    {
                        index = size;
                    } 
                }
                else if(row < size - 1)
                {
                    if(!solveCell(row + 1, 0))
                    {
                        grid.insertAt(row, col, '\u0000');
                    }
                    else
                    {
                        index = size;
                    }
                }
                else
                {
                    index = size;
                }
            }
            else
            {
                grid.insertAt(row, col, '\u0000');
            }
        }
        if(grid.getValueAt(row, col) == '\u0000')
        {

            return false;
        }
        else
        {

            return true;
        }
    }
}
