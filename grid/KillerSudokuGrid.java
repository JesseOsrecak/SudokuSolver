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

    Node[] nodes;

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
        nodes = new Node[Integer.parseInt(line)];
        line = br.readLine();
        int total = 0;
        int constraintNumber = 0;
        while(line != null)
        {
            String[] variables = line.split(" ");
            Node node = new Node(Integer.parseInt(variables[0]));
            nodes[constraintNumber++] = node;
            total += node.getCapacity();
            
            for(int i = 1; i < variables.length; ++i)
            {
                String[] cordinates = variables[i].split(",");
                inputGrid[Integer.parseInt(cordinates[0])][Integer.parseInt(cordinates[1])] = node;

            }
            line = br.readLine();
        }

        br.close();

        //Validate Totals
        if(total != getExpectedGridCapacity())
        {
            throw new IOException("Given Capacities != expected capacity");
        }

        //Validate that every cell has an expected total
        boolean hasNull = false;
        for(int row = 0; row < size ; ++row)
        {
            for(int col = 0; col < size; ++col)
            {
                if(inputGrid[row][col] == null)
                {
                    hasNull = true;
                }
            }
        }
        if(hasNull)
        {
            throw new IOException("File incomplete, killer sudoku grid is missing totals");
        }


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

        for(int row = 0; row < size; ++row)
        {
            for(int col = 0; col < size; ++col)
            {
                if(outputGrid[row][col] != 0)
                {
                    string += outputGrid[row][col];
                }
                else
                {
                    string += " ";
                }

                if(col != size -1)
                {
                    string += ", ";
                }
            }
            string += "\n";
        }

        return string;
    } // end of toString()


    @Override
    public boolean validate() 
    {
        boolean validated = true;

        //Validate Nodes array (All value = capacity)
        validated = validateNodesEqual();

        if(validated)
        {
            //Validate Rows
            validated = validateRows();
            if(validated)
            {
                //Validate Collumns
                validated = validateCollumns();
                if(validated)
                {
                    //Validate Boxes
                    validated = validateBoxes();
                }
            }
        }

        // placeholder
        return validated;
    } // end of validate()

    private boolean validateNodesEqual()
    {
        boolean validated = true;
        for(int index = 0; index < nodes.length && validated; ++index)
        {
            if(!nodes[index].validateFull())
            {
                validated = false;
            }
        }

        return validated;
    }

    private boolean validateRows()
    {
        boolean validated = true;
        
        for(int index = 0; index < size && validated; ++index)
        {
            validated = validateRow(index);
        }

        return validated;
    }

    private boolean validateRow(int row)
    {

        boolean validated = true;
        for(int index = 0; index < size && validated; ++index)
        {
            int count = 0;
            for(int collumn = 0; collumn < size; ++collumn)
            {
                if(outputGrid[row][collumn] == values[index])
                {
                    ++count;
                }
            }
            if(count > 1)
            {
                validated = false;
            }
        }
        return validated;
    }

    private boolean validateCollumns()
    {
        boolean validated = true;
        
        for(int index = 0; index < size && validated; ++index)
        {
            validated = validateCollumn(index);
        }

        return validated;
    }

    private boolean validateCollumn(int collumn)
    {

        boolean validated = true;
        for(int index = 0; index < size && validated; ++index)
        {
            int count = 0;
            for(int row = 0; row < size; ++row)
            {
                if(outputGrid[row][collumn] == values[index])
                {
                    ++count;
                }
            }
            if(count > 1)
            {
                validated = false;
            }
        }
        return validated;
    }

    private boolean validateBoxes()
    {
        boolean validated = true;
        int square = (int)Math.sqrt(size);
        for(int x = 0 ; x < size-1 && validated; x = x + square)
        {
            for(int y = 0; y < size-1 && validated; y = y+square)
            {
                validated = validateBox(x, y);
            }
        }
        return validated;
    }

    private boolean validateBox(int x, int y)
    {
        boolean validated = true;
        int square = (int)Math.sqrt(size);
        int count = 0;

        for(int index = 0; index < size && validated; ++index)
        {
            count = 0;
            for(int row = x; row < x+square; ++row)
            {
                for(int col = y; col< y+square; ++col)
                {
                    if(outputGrid[row][col] == values[index])
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
        return validated;
    }





    public int getExpectedGridCapacity()
    {
        int total = 0;

        for(int index = 0; index < size; ++index)
        {
            total += values[index]*size;
        }

        return total;
    }


    @Override
    public int getSize()
    {
        return size;
    }//end of getSize()


    public void insertAt(int row, int col, int value)
    {
        outputGrid[row][col] = value;
        inputGrid[row][col].addValue(value);
    }

    public void removeAt(int row, int col)
    {
        inputGrid[row][col].subtractValue(outputGrid[row][col]);
        outputGrid[row][col] = 0;
    }

    public boolean validateCurrent()
    {
        boolean validated = true;

        //Validate Nodes array (All value = capacity)
        validated = validateNodesless();

        if(validated)
        {
            //Validate Rows
            validated = validateRows();
            if(validated)
            {
                //Validate Collumns
                validated = validateCollumns();
                if(validated)
                {
                    //Validate Boxes
                    validated = validateBoxes();
                }
            }
        }

        // placeholder
        return validated;
    }

    private boolean validateNodesless()
    {
        boolean validated = true;
        for(int index = 0; index < nodes.length && validated; ++index)
        {
            if(!nodes[index].isUnderOrEqual())
            {
                validated = false;
            }
        }

        return validated;
    }

    public int getValueAt(int index)
    {
        return values[index];
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
