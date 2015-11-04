public class chemicalTrains
{
    int maxCarts = 12;
    int coloursPlaced[];
    boolean canPlaceTrain(int cartNumber, int allCarts[][], int colourToPlace, int coloursPlaced[])
    {
        for(int i = 0; i < maxCarts; i++)
        {
            if(allCarts[cartNumber][i] == 1 && colourToPlace == coloursPlaced[i])
            {
                return false;
            }
        }
        return true;
    }
    
    boolean colourCarts(int allCarts[][], int numColours, int coloursPlaced[], int cart)
    {
        if (cart == maxCarts)
        {
            return true;
        }
        
        for(int colour = 1; colour <= numColours; colour++)
        {
            //printSolution(coloursPlaced);
            if(canPlaceTrain(cart, allCarts, colour, coloursPlaced))
            {
                coloursPlaced[cart] = colour;
                if(colourCarts(allCarts, numColours, coloursPlaced, cart + 1))
                {
                    return true;
                }
                
                coloursPlaced[cart] = 0; //Backtrack
            }
        }
        return false;
    }
    
    boolean setupGraph(int allCarts[][], int numColours)
    {
        coloursPlaced = new int[maxCarts];
        for(int i = 0; i < maxCarts; i++)
        {
            coloursPlaced[i] = 0;
        }
        
        if(!colourCarts(allCarts, numColours, coloursPlaced, 0))
        {
            System.out.println("No Solution Exists with " + numColours + " colours.");
            return false;
        }
        printSolution(coloursPlaced);
        return true;
    }
    
    void printSolution(int coloursPlaced[])
    {
        for(int i =0; i < maxCarts; i++)
        {
            System.out.print(coloursPlaced[i] + " ");
        }
        System.out.println("");
    }
    
    public static void main(String[] args)
    {
        chemicalTrains t = new chemicalTrains();
        int allCarts[][] = {
            {0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1},
            {0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
            {0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1},
            {0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0},
            {0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1},
            {1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0}
        };
        int numColours = 5;
        t.setupGraph(allCarts, numColours);
    }
}