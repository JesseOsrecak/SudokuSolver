/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */
package solver;

import grid.SudokuGrid;


/**
 * Algorithm X solver for standard Sudoku.
 */
public class AlgorXSolver extends StdSudokuSolver
{
    SudokuGrid grid;
    int size;
    int numberOfCells;
    int[][] matrix;
    int numberOfRows;
    public AlgorXSolver() {
        // TODO: any initialisation you want to implement.
    } // end of AlgorXSolver()


    @Override
    public boolean solve(SudokuGrid grid) 
    {
        
        /*
            save grid to class
            save gridOptions.length
            return chooseCollumn(build2dArray(0), 0)
        */

        this.grid = grid;
        size = grid.getSize();
        numberOfCells = size * size;
        numberOfRows = numberOfCells*size;
        matrix = new int[numberOfRows][numberOfCells * 4];

        buildCellConstraints();
        buildRowConstraints();
        buildCollumnConstraints();
        buildBlockConstraints();


        prepMatrixForWork();
        boolean solved =  workGrid(0);
        buildGrid();

        return solved;
    } // end of solve()


    private void buildCellConstraints()
    {
        int row = 0;
        for(int col = 0; col < numberOfCells; ++col)
        {
            for(int i = 0; i < size ; ++i)
            {
                matrix[row++][col] = 1;
            }
        }
    }

    private void buildRowConstraints()
    {
        int row = 0;
        int x;
        for(int rowNumber = 0; rowNumber < numberOfCells; rowNumber += size)
        {
            x = numberOfCells + rowNumber;
            for(int i = 0; i < size; ++i)
            {
                for(int j = 0; j < size && row < numberOfRows; ++j)
                {
                    matrix[row++][x + j] = 1;
                }
            }
        }
    }

    private void buildCollumnConstraints()
    {          
        int collumnsBeforeConstraint = 2*numberOfCells;
        for(int row = 0; row < numberOfRows; ++row)
        {
            matrix[row][collumnsBeforeConstraint + (row % numberOfCells)] = 1;
        }
    }

    private void buildBlockConstraints()
    {
        int row = 0;
        int x;
        int y;
        int rootSize = (int)Math.sqrt(size);
        int sizeByRoot = rootSize * size;
        int offset = 3 * numberOfCells;
        int z;
        for(int i = 0; i < numberOfCells; i += sizeByRoot)
        {
            y = i + offset;
            for(int j = 0; j < rootSize; ++j)
            {
                for(int k =0; k < sizeByRoot ; k += size)
                {
                    x = y + k;
                    for(int l = 0; l < rootSize; ++l)
                    {
                        for(int m = 0; m < size && row < numberOfRows; ++m)
                        {
                            matrix[row++][x +m] = 1;
                        }
                    }
                }
            }
        }
    }

    private boolean workGrid(int col)
    {

        int checked = 0;
        boolean solved = false;

        char value = grid.getValueAt(col/size, col%size);
        int indexRow;
        int indexCollumn;
        int count;

        if(value == '\u0000')
        {
            for(int row = 0; row < numberOfRows && checked < size && !solved; ++row)
            {
                if(matrix[row][col] == 1)
                {
                    ++checked;
                    count = 0;
                    for(int rowCheck = col; rowCheck < numberOfCells*4 && !solved; ++rowCheck)
                    {
                        if(matrix[row][rowCheck] == 1)
                        {
                            ++count;

                        }
                    }
                    if(count > 3)
                    {
                        refactorRow(row, col);

                        if(col == numberOfCells -1)
                        {
                            solved = true;
                        }
                        else
                        {
                            solved = workGrid(col + 1);
                        }
    
                        if(!solved)
                        {
                            revertRow(row, col);
                        }
                    }
    
                    if(!solved)
                    {
                        matrix[row][col] = -1;
                    }
    
                }
            }

            if(!solved)
            {
                for(int i = 0; i < numberOfRows; ++i)
                {
                    if(matrix[i][col] == -1)
                    {
                        matrix[i][col] = 1;
                    }
                }
            }
        }
        else
        {
            if(col == numberOfCells -1)
            {
                solved = true;
            }
            else
            {
                solved = workGrid(col + 1);
            }
        }
       
        return solved;
    }

    private void refactorRow(int row, int collumn)
    {
        
        int  numberOfCollumns = numberOfCells * 4;
        for(int col = 0; col < numberOfCollumns; ++col)
        {
            if(matrix[row][col] == 1)
            {
                for(int i = 0; i < numberOfRows;i++)
                {
                    if(matrix[i][col] == 1 && i != row)
                    {
                        matrix[i][col] = -1;
                    }
                }
            }
        }

        for(int i = row + 1; i < row + size && i < numberOfRows; ++i)
        {
            if(matrix[i][collumn] == 1)
            {
                matrix[i][collumn] = -1;
            }
        }
    }

    private void revertRow(int row , int collumn)
    {

        
        int  numberOfCollumns = numberOfCells * 4;

        for(int col = numberOfCells; col < numberOfCollumns; ++col)
        {
            if(matrix[row][col] == 1)
            {
                for(int i = 0; i < numberOfRows;i++)
                {
                    if(matrix[i][col] == -1)
                    {
                        matrix[i][col] = 1;
                    }
                }
            }
        }

        for(int i = row + 1; i < row + size && i < numberOfRows; ++i)
        {
            if(matrix[i][collumn] == -1)
            {
                matrix[i][collumn] = 1;
            }
        }
        
    }

    private void buildGrid()
    {
        int collumn = 0;

        for(int row = 0; row < numberOfRows && collumn < numberOfCells; ++row)
        {
            if(matrix[row][collumn] == 1)
            {
                grid.insertAt( row / numberOfCells, (row % numberOfCells)/size , grid.getOptionAt(row % size));
                ++collumn;
            }
        }
    }

    private void prepMatrixForWork()
    {
        int row;
        int collumn;
        char value;
        int index;
        int matrixRow;
        for(int col = 0; col < numberOfCells; ++col)
        {
            row = col/size;
            collumn = col%size;
            value = grid.getValueAt(row, collumn);
            if(value != '\u0000')
            {
                index = grid.getIndexOfOption(value);
                matrixRow = row*numberOfCells + collumn*size + index;

                for(int i = 0; i < matrixRow; ++i)
                {
                    if(matrix[i][col] == 1)
                    {
                        matrix[i][col] = -1;
                    }
                }

                refactorRow(matrixRow, col);

            }
        }
    }
} // end of class AlgorXSolver
