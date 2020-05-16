/**
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */
package grid;

import java.io.*;
import java.util.Arrays;


/**
 * Class implementing the grid for standard Sudoku.
 * Extends SudokuGrid (hence implements all abstract methods in that abstract
 * class).
 * You will need to complete the implementation for this for task A and
 * subsequently use it to complete the other classes.
 * See the comments in SudokuGrid to understand what each overriden method is
 * aiming to do (and hence what you should aim for in your implementation).
 */
public class StdSudokuGrid extends SudokuGrid
{


    public StdSudokuGrid() {
        super();

    } // end of StdSudokuGrid()


    /* ********************************************************* */


    @Override
    public void initGrid(String filename)
        throws FileNotFoundException, IOException
    {

        File file = new File(filename); 
        String line;
        BufferedReader br = new BufferedReader(new FileReader(file)); 

        
       
        size = Integer.parseInt(br.readLine());
        options = new char[size];
        grid = new char[size][size];
        
        
        line = br.readLine();
        line= line.replaceAll("\\s", "");

        for(int i = 0; i < size; ++i)
        {
            options[i] = line.charAt(i);
        }
        
        line = br.readLine();
        while(line != null)
        {
            
            grid[Character.getNumericValue(line.charAt(0))][Character.getNumericValue(line.charAt(2))] = line.charAt(4);
            line = br.readLine();
        }
        

        br.close();
        
    } // end of initBoard()


    @Override
    public void outputGrid(String filename)
        throws FileNotFoundException, IOException
    {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
        String string = toString();
        bufferedWriter.write(string,0 ,string.length());
        bufferedWriter.close();
    } // end of outputBoard()


    @Override
    public String toString() 
    {
        String string = "";
        for(int row = 0; row < size; ++row)
        {
            for(int col = 0; col < size ; ++col)
            {
                if( grid[row][col] != '\u0000')
                {
                    string += grid[row][col];
                }
                else{
                    string += " ";
                }
                if(col != size - 1)
                {
                    string  += ",";
                }
            }
            string += '\n';
        }
        // placeholder
        return String.valueOf(string);
    } // end of toString()


    @Override
    public boolean validate() 
    {
        boolean validated = true;
        int count;
        int square = (int)Math.sqrt(size);
        //Validate rows
        for(int row = 0; row < size; ++row)
        {
            for(int index = 0; index < size; ++index)
            {
                count = 0;
                for(int col = 0; col < size; ++col)
                {
                    if(options[index] == grid[row][col])
                    {
                        ++count;
                    }
                }
                if(count > 1)
                {
                    validated = false;
                }
            }
        }
        //validate collumns
        for(int col = 0; col < size; ++col)
        {
            for(int index = 0; index < size; ++index)
            {
                count = 0;
                for(int row = 0; row < size; ++row)
                {
                    if(options[index] == grid[row][col])
                    {
                        ++count;
                    }
                }
                if(count > 1)
                {
                    validated = false;
                }
            }
        }
        //validate subdivisions
        for(int i = 0 ; i < size-1; i = i + square)
        {
            for(int j = 0; j < size-1; j = j+square)
            {
                for(int index = 0; index < size; ++index)
                {
                    count = 0;
                    for(int row = i; row < i+square; ++row)
                    {
                        for(int col = j; col< j+square; ++col)
                        {
                            if(grid[row][col] == options[index])
                            {
                                ++count;
                            }
                        }
                    }
                    if(count > 1)
                    {
                        validated = false;
                    }
                }
            }
        }

        return validated;
    } // end of validate()

    @Override
    public char getOptionAt(int index)
    {
        return options[index];
    }// end of getOptionAt(int index)

    @Override
    public void insertAt(int row, int col, char value)
    {
        grid[row][col] = value;
    }// end of insertAt(int row, int col, char value)

    public char getValueAt(int row, int col)
    {

        return grid[row][col];
    }//end of getValueAt(int row, int col)

    public int getSize()
    {
        
        return size;
    }//end of getSize()
} // end of class StdSudokuGrid
