/**
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */
package grid;

import java.io.*;


/**
 * Class implementing the grid for Killer Sudoku.
 * Extends SudokuGrid (hence implements all abstract methods in that abstract
 * class).
 * You will need to complete the implementation for this for task E and
 * subsequently use it to complete the other classes.
 * See the comments in SudokuGrid to understand what each overriden method is
 * aiming to do (and hence what you should aim for in your implementation).
 */
public class KillerSudokuGrid extends SudokuGrid
{
    // TODO: Add your own attributes
    int[] values;
    
    Node[][] inputGrid;

    int[][] outputGrid;

    public KillerSudokuGrid() {
        super();

        // TODO: any necessary initialisation at the constructor
    } // end of KillerSudokuGrid()


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

        line = br.readLine();
        String[] elements = line.split(" ");

        values = new int[size];
        for(int i = 0; i < size; ++i)
        {
            values[i] = Integer.parseInt(elements[i]);
        }

        inputGrid = new Node[size][size];
        outputGrid = new int[size][size];


        line = br.readLine();

        while(line != null)
        {
            String[] variables = line.split(" ");
            Node node = new Node(Integer.parseInt(variables[0]));
            for(int i = 1; i < variables.length; ++i)
            {
                String[] cordinates = variables[i].split(",");
                inputGrid[Integer.parseInt(cordinates[0])][Integer.parseInt(cordinates[1])] = node;

            }
            line = br.readLine();
        }

        br.close();
        // TODO
    } // end of initBoard()


    @Override
    public void outputGrid(String filename)
        throws FileNotFoundException, IOException
    {
        // TODO
    } // end of outputBoard()


    @Override
    public String toString() {
        String string = "";
        string += "Size: " + size + "x" + size + "\n";

        for(int i = 0; i < size; ++i)
        {
            string += values[i] + ", ";
        }

        string += "\n\n";

        for(int row = 0; row < size; ++row)
        {
            for(int col = 0; col < size; ++col)
            {
                string += inputGrid[row][col].getCapacity();

                if(col != size -1)
                {
                    string += ", ";
                }
            }
            string += "\n";
        }
        // placeholder
        return string;
    } // end of toString()


    @Override
    public boolean validate() 
    {

        // TODO Validate Sum Constraint

        // placeholder
        return false;
    } // end of validate()

    @Override
    public char getOptionAt(int index)
    {
        //TODO

        //placeholder
        return '\u0000';
    }// end of getOptionAt(int index)

    @Override
    public void insertAt(int row, int col, char value)
    {
        //TODO

    }// end of insertAt(int row, int col, char value)

    public char getValueAt(int row, int col)
    {
        //TODO

        //placeholder
        return '\u0000';
    }//end of getValueAt(int row, int col)

    public int getSize()
    {
        //TODO

        //placeholder
        return -1;
    }//end of getSize()

    public int getIndexOfOption(char option)
    {
        return 0;
    }
} // end of class KillerSudokuGrid

class Node
{
    int capacity;
    int value;

    public Node(int capacity)
    {
        this.capacity = capacity;
        value = 0;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public int getValue()
    {
        return value;
    }

    public void addValue(int value)
    {
        this.value += value;
    }

    public void subtractValue(int value)
    {
        this.value -= value;
    }

    public boolean validateFull()
    {
        return capacity == value;
    }

    public boolean isUnderOrEqual()
    {
        return capacity >= value;
    }


}
