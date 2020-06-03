/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import grid.SudokuGrid;


/**
 * Dancing links solver for standard Sudoku.
 */
public class DancingLinksSolver extends StdSudokuSolver
{
    private Header header;
    private int size;
    private int numberOfCells;
    SudokuGrid grid;
    public DancingLinksSolver() 
    {
        header = new Header();
        size = 0;
    } // end of DancingLinksSolver()


    @Override
    public boolean solve(SudokuGrid grid) 
    {
        this.grid = grid;
        size = grid.getSize();
        numberOfCells = size*size;
        buildLinkedList();

        boolean solved  = false;

        //prep Matrix
        prepMatrix();
        //work Matrix
        solved = workCollumn((Header) header.getRight());

        // printLinkedList();
        importGrid();

        // placeholder
        return solved;
    } // end of solve()

    private void buildLinkedList()
    {
        
        Header currentHeader = header;
        //BUILD HEADER ROW
        int collumn = 0;
        //First Constraint Header
        while(collumn < numberOfCells)
        {
            currentHeader.setRight(new Header(collumn, "G", 0));
            currentHeader.getRight().setLeft(currentHeader);
            currentHeader = (Header) currentHeader.getRight();

            currentHeader.setUp(currentHeader);
            currentHeader.setDown(currentHeader);

            ++collumn;
        }

        //Second Constraint Header
        while(collumn < numberOfCells*2)
        {
            currentHeader.setRight(new Header(collumn, "R", 0));
            currentHeader.getRight().setLeft(currentHeader);
            currentHeader = (Header) currentHeader.getRight();

            currentHeader.setUp(currentHeader);
            currentHeader.setDown(currentHeader);

            ++collumn;
        }

        //Third Constraint Header
        while(collumn < numberOfCells*3)
        {
            currentHeader.setRight(new Header(collumn, "C", 0));
            currentHeader.getRight().setLeft(currentHeader);
            currentHeader = (Header) currentHeader.getRight();

            currentHeader.setUp(currentHeader);
            currentHeader.setDown(currentHeader);

            ++collumn;
        }

        //Fourth Constrain Header
        while(collumn < numberOfCells*4)
        {
            currentHeader.setRight(new Header(collumn, "B", 0));
            currentHeader.getRight().setLeft(currentHeader);
            currentHeader = (Header) currentHeader.getRight();

            currentHeader.setUp(currentHeader);
            currentHeader.setDown(currentHeader);

            ++collumn;
        }

        currentHeader.setRight(header);
        header.setLeft(currentHeader);
        //END Header Construction

        currentHeader = (Header) header.getRight();

        //BUILD FIRST CONSTRAINT
        for(int i = 0; i < numberOfCells; ++i)
        {
            Node currentNode = currentHeader;
            for(int j = 1; j <= size; ++j)
            {
                currentNode.setDown(new Node(j));
                currentNode.getDown().setUp(currentNode);
                currentNode = currentNode.getDown();
                currentNode.setHeader(currentHeader);
                currentHeader.incremeantSize();

                //For TESTING REMOVE ONCE All constraints are built
                currentNode.setLeft(currentNode);
                currentNode.setRight(currentNode);
            }

            currentNode.setDown(currentHeader);
            currentHeader.setUp(currentNode);
            currentHeader = (Header) currentHeader.getRight();
        }

        Header rowHeader = currentHeader;
        Header gridHeader = (Header) header.getRight();
        //BUILD SECOND CONSTRAINT
        for(int i = 0; i < numberOfCells; ++i)
        {
            Node currentNode = gridHeader;
            for(int j = 1; j <= size; ++j)
            {
                if(j == 1)
                {
                    //set Head to start rowHead
                    currentHeader = rowHeader;
                }
                //Move Down
                currentNode = currentNode.getDown();

                //  Create Node and Alter Left
                currentNode.setRight(new Node(j));
                currentNode.getRight().setLeft(currentNode);

                //Alter Up
                currentNode.getRight().setUp(currentHeader.getUp());
                currentNode.getRight().getUp().setDown(currentNode.getRight());

                //Alter Down
                currentNode.getRight().setDown(currentHeader);
                currentNode.getRight().getDown().setUp(currentNode.getRight());

                //Alter Right (Not Necesary)
                currentNode.setLeft(currentNode.getRight());
                currentNode.getRight().setRight(currentNode);

                //Set Head
                currentNode.getRight().setHeader(currentHeader);
                currentNode.getRight().getHeader().incremeantSize();

                //Move head to the right
                currentHeader = (Header) currentHeader.getRight();

                

            }
            
            
            if(i%size == 3)
            {
                rowHeader = currentHeader;
            }

            gridHeader = (Header) gridHeader.getRight();
        }
        

        //BUILD THIRD CONSTRAINT
        Header collumnHeader = currentHeader;
        gridHeader = (Header) header.getRight();
        for(int i = 0; i < size; ++i)
        {
            currentHeader = collumnHeader;

            for(int j = 0; j < size; ++j)
            {

                Node currentNode = gridHeader;

                for(int k = 1; k <= size; ++k)
                {
                    currentNode = currentNode.getDown();

                    //Instansiate Node
                    currentNode.getRight().setRight(new Node(k));

                    Node newNode = currentNode.getRight().getRight();
                    //Link Left to previous Node
                    newNode.setLeft(currentNode.getRight());

                    //set Header
                    newNode.setHeader(currentHeader);
                    newNode.getHeader().incremeantSize();

                    //Link up
                    newNode.setUp(currentHeader.getUp());
                    currentHeader.getUp().setDown(newNode);

                    //Link down
                    newNode.setDown(currentHeader);
                    currentHeader.setUp(newNode);

                    //Link right
                    newNode.setRight(currentNode);
                    currentNode.setLeft(newNode);

                    currentHeader = (Header) currentHeader.getRight();
                }

                gridHeader = (Header) gridHeader.getRight();
            }
        }

        //BUILD FOURTH CONSTRAINT
        int rootSize = (int)Math.sqrt(size);
        gridHeader = (Header) header.getRight();
        for(int z = 0;  z < rootSize; ++z)
        {
            Header blockHeader = currentHeader;
            for(int i = 0; i < rootSize; ++i)//Iterates through phases
            {
                
                currentHeader = blockHeader;

                for(int j = 0; j < rootSize; ++j)//Itterates through rows
                {
                    Header anotherHeader = currentHeader;

                    for(int k = 0; k < rootSize; ++k)//Itterates through row
                    {
                        currentHeader = anotherHeader;
                        
                        Node currentNode = gridHeader;

                        for(int l = 1; l <= size; ++l)//iterates through collumn
                        {

                            currentNode = currentNode.getDown();

                            //Instansiate Node
                            currentNode.getRight().getRight().setRight(new Node(l));

                            Node newNode = currentNode.getRight().getRight().getRight();
                            //Link Left to previous Node
                            newNode.setLeft(currentNode.getRight().getRight());

                            //set Header
                            newNode.setHeader(currentHeader);
                            newNode.getHeader().incremeantSize();

                            //Link up
                            newNode.setUp(currentHeader.getUp());
                            currentHeader.getUp().setDown(newNode);

                            //Link down
                            newNode.setDown(currentHeader);
                            currentHeader.setUp(newNode);

                            //Link right
                            newNode.setRight(currentNode);
                            currentNode.setLeft(newNode);

                                currentHeader = (Header) currentHeader.getRight();
                            }

                        gridHeader = (Header) gridHeader.getRight();
                    }
                }
            
            }
        }
    }

    private void prepMatrix()
    {
        for(int row = 0; row < size; ++row)
        {
            for(int col = 0; col < size; ++col)
            {
                char value = grid.getValueAt(row,col);
                if(value != '\u0000')
                {
                    int index = grid.getIndexOfOption(value);
                    Header currentHeader = (Header) header.getRight();
                    for(int i = 0; i <row*size; ++i)
                    {
                        currentHeader = (Header) currentHeader.getRight();
                    }

                    for(int i = 0; i < col; ++i)
                    {
                        currentHeader = (Header) currentHeader.getRight();
                    }

                    Node currentNode = currentHeader.getDown();

                    for(int i = 0; i < index; ++i)
                    {
                        currentNode = currentNode.getDown();
                    }

                    selectRow(currentNode);
                }
            }
        }
    }

    private void selectRow(Node node)
    {
        Node currentNode = node.getRight();
        makeCollumnFalse(node);

        while(currentNode != node)
        {

            makeCollumnFalse(currentNode);

            currentNode = currentNode.getRight();
        }
    }

    private void deselectRow(Node node)
    {
        Node currentNode = node.getRight();
        makeCollumnTrue(node);

        while(currentNode != node)
        {

            makeCollumnTrue(currentNode);

            currentNode = currentNode.getRight();
        }
    }

    private void makeCollumnFalse(Node node)
    {
        Node currentNode = node.getHeader().getDown();

        while(node.getHeader() != currentNode)
        {
            if(currentNode != node)
            {
                currentNode.setVisible(false);
                node.getHeader().decremeantSize();
            }

            currentNode = currentNode.getDown();
        }
    }

    private void makeCollumnTrue(Node node)
    {
        Node currentNode = node.getHeader().getDown();

        while(node.getHeader() != currentNode)
        {
            if(currentNode != node)
            {
                currentNode.setVisible(true);
                node.getHeader().incremeantSize();;
            }
            currentNode = currentNode.getDown();
        }
    }

    private boolean workCollumn(Header currentHeader)
    {
        boolean solved = false;
        if(currentHeader == header)
        {
            solved = true;
        }
        else
        {
            if(currentHeader.getSize() == 1)
            {
                solved = workCollumn((Header)currentHeader.getRight());
            }
            else
            {
                Node currentNode = currentHeader.getDown();

                while(currentNode != currentHeader && !solved)
                {
                    Node node = currentNode.getRight();
                    boolean canBeSelected = true;
                    while(currentNode != node && canBeSelected)
                    {
                        canBeSelected = node.getVisible();
                
                        node = node.getRight();
                    }

                    if(canBeSelected)
                    {
                        selectRow(currentNode);
                        solved = workCollumn((Header)currentHeader.getRight());

                        if(!solved)
                        {
                            deselectRow(currentNode);
                        }
                    }

                    currentNode = currentNode.getDown();
                }
            }
        }

        return solved;
    }

    private void importGrid()
    {
        Header currentHeader = (Header) header.getRight();
        for(int row = 0; row < size; ++row)
        {
            for(int col = 0; col < size; ++col)
            {
                if(grid.getValueAt(row, col) == '\u0000')
                {
                    Node currentNode = currentHeader.getDown();
                    for(int index = 0; index < size; ++index)
                    {
                        if(currentNode.getVisible())
                        {
                            grid.insertAt(row, col, grid.getOptionAt(index));
                        }

                        currentNode = currentNode.getDown();
                    }
                }
                currentHeader = (Header) currentHeader.getRight();
            }
        }
    }

    //FOR TESTING PURPOSES PLEASE DELETE
    private void printLinkedList()
    {
        String string = "";
        //Print Header Name
        Header currentHeader = (Header) header.getRight();
        while(currentHeader != header)
        {
            string += currentHeader.getName();
            currentHeader = (Header) currentHeader.getRight();
        }


        //Print Size
        string += "\n";
        currentHeader = (Header) header.getRight();
        while(currentHeader != header)
        {
            string += currentHeader.getSize();
            currentHeader = (Header) currentHeader.getRight();
        }

        //Print Collumn
        string += "\n";
        currentHeader = (Header) header.getRight();
        while(currentHeader != header)
        {
            string += (currentHeader.getCol()%4);
            currentHeader = (Header) currentHeader.getRight();
        }

        //Print Nodes
        string += "\n";
        currentHeader = (Header) header.getRight();
        for(int i = 0; i < numberOfCells; ++i)
        {
            //Traverse Down
            Node currentNode = currentHeader.getDown();
            // System.out.print(currentNode.getVisible());
            
            while(currentNode != currentHeader)
            {
                int collumn = 0;
                //Traverse Accross
                Node nodeAccross = currentNode;
                for(int j = 0; j < size ; ++j);
                {
                    for(int k = 0; k < size; ++k)//change 2 to size
                    {
                        if(nodeAccross.getVisible())
                        {
                            int temp = nodeAccross.getCol() - (collumn);

                            for(int l = 0; l < (nodeAccross.getCol() - (collumn)); ++l)
                            {
                                string += " ";
                            }
                            string += nodeAccross.getValue();
                            collumn = nodeAccross.getCol() + 1;
                        
                        }

                        nodeAccross = nodeAccross.getRight();
                    }
                    
                }

                string += "\n";
                currentNode = currentNode.getDown();
            }
            currentHeader = (Header) currentHeader.getRight();
        }

        System.out.println(string);
    }


} // end of class DancingLinksSolver

class Node
{
    private Node up;
    private Node down;
    private Node left;
    private Node right;
    private Header header;

    private boolean visible;
    private int value;

    public Node(int value)
    {
        this.value = value;
        this.visible = true;
    }

    public void setUp(Node up)
    {
        this.up = up;
    }

    public void setDown(Node down)
    {
        this.down = down;
    }

    public void setLeft(Node left)
    {
        this.left = left;
    }

    public void setRight(Node right)
    {
        this.right = right;
    }

    public Node getUp()
    {
        return up;
    }

    public Node getDown()
    {
        return down;
    }

    public Node getLeft()
    {
        return left;
    }
    
    public Node getRight()
    {
        return right;
    }

    public void setVisible(boolean vis)
    {
        visible = vis;
    }

    public boolean getVisible()
    {
        return visible;
    }

    public int getValue()
    {
        return value;
    }

    public void setHeader(Header header)
    {
        this.header = header;
    }

    public Header getHeader()
    {
        return header;
    }

    public int getCol()
    {
        return header.getCol();
    }
}

class Header extends Node
{
    private int col;
    private String name;
    private int size;

    public Header()
    {
        super(-1);
    }

    public Header(int col, String name, int size)
    {
        super(-1);

        this.col = col;
        this.name = name;
        this.size = size;
    }

    public int getCol()
    {
        return col;
    }

    public String getName()
    {
        return name;
    }

    public int getSize()
    {
        return size;
    }

    public void incremeantSize()
    {
        ++size;
    }

    public void decremeantSize()
    {
        --size;
    }
}