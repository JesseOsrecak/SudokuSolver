/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import grid.SudokuGrid;
import grid.KillerSudokuGrid;


/**
 * Backtracking solver for Killer Sudoku.
 */
public class KillerBackTrackingSolver extends KillerSudokuSolver
{

    private int size;
    public KillerBackTrackingSolver() {
        // TODO: any initialisation you want to implement.
    } // end of KillerBackTrackingSolver()


    @Override
    public boolean solve(SudokuGrid grid) {
        this.grid = (KillerSudokuGrid) grid;
        size = grid.getSize();
        
        return solveCell(0, 0);
    } // end of solve()

    private boolean solveCell(int row, int col)
    {
        boolean solved = false;
        for(int index = 0; index < size && !solved; ++index)
        {
            grid.insertAt(row, col, grid.getValueAt(index));

            if(grid.validateCurrent())
            {
                
                if(col != size -1)
                {
                    solved = solveCell(row, col + 1);
                }
                else if(row != size -1)
                {
                    solved = solveCell( row + 1, 0);
                }
                else
                {
                    solved = true;
                }
            }

            if(!solved)
            {
                grid.removeAt(row,col);
            }
        }

        return solved;
    }

} // end of class KillerBackTrackingSolver()
