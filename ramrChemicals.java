/*
 * Randy Ram
 * ID: 808000428
 * Program: Chemicals
 * 
 * Problem: Given n elements, some which react unsafely with each other and needs to be 
 *          separated by carts of sand, fine the minimum number of sand carts needed to transport 
 *          all the chemicals.
 * 
 * Solution: A graph-colouring backtracking algorithm is applied to the carts to colour
 *           them in such a way such that the unreactive chemicals carts are coloured using the same
 *           colour. The similarly coloured carts and then grouped and printed, with each grouping
 *           separated by 2 carts of sand. The caboose is separated from the chemicals by 2 carts
 *           of sand as well.
 */
import java.util.*;
import java.io.*;

public class ramrChemicals
{
    public static void main(String[] args)
    {
        try
        {
            ramrChemicals r = new ramrChemicals();
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            int numChemicals = Integer.parseInt(br.readLine());
            String allCarts[][] = new String[numChemicals][numChemicals];
            String line;
            int row = 0;
            while((line = br.readLine()) != null)
            {
                //System.out.println(line);
                String [] splitLine = line.split("[\\s]+");
                int currentVal = 0;
                for(int i = 0; i < numChemicals; i++)
                {
                    if(i == row)
                    {
                        allCarts[row][i] = "S";
                    }
                    else
                    {
                        allCarts[row][i] = splitLine[currentVal];
                        currentVal++;
                    }
                }
                currentVal = 0;
                row++;
            }
            
            r.setupGraph(allCarts, numChemicals);
          }
      catch (FileNotFoundException e)
      {
          System.out.println("Cannot find file");
      }
      catch (IOException e) 
      {
          e.printStackTrace();
      }
    }
    
    
    void setupGraph(String allCarts[][], int numChemicals)
    {
        //Store the colours for each chemical and initialize them to 0
        int chemicalsPlaced[] = new int[numChemicals];
        for(int i = 0; i < numChemicals; i++)
        {
            chemicalsPlaced[i] = 0;
        }
        
        int numColours = 1; //We start with one colour and keep incrementing colours until we get a solution
        while(!placeChemicals(allCarts, numChemicals, numColours, chemicalsPlaced, 0))
        {
            numColours++;
        }
        
        printCarts(chemicalsPlaced, numColours);
    }
    
    /*
     * placeChemicals - Recursive function that tries to colour all the carts using the number of colours given.
     * Parameters:
     * allCarts - The adjacency matrix representing the chemicals and their reactions with other chemicals
     * numChemicals - The total amount of chemicals being transported.
     * numColours - The maximum number of colours we're trying to colour the carts with
     * chemicalsPlaced - An array for storing the colour for each chemical placed.
     * cart - The cart that is being coloured.
     */
    boolean placeChemicals(String allCarts[][], int numChemicals, int numColours, int chemicalsPlaced[], int cart)
    {
        //If we've placed all the chemicals, then exit
        if(cart == numChemicals)
        {
            return true;
        }
        for(int colour = 1; colour <= numColours; colour++)
        {
            if(canColourCart(cart, allCarts, colour, chemicalsPlaced, numChemicals))
            {
                chemicalsPlaced[cart] = colour;
                if(placeChemicals(allCarts, numChemicals, numColours, chemicalsPlaced, cart + 1))
                {
                    return true;
                }
                chemicalsPlaced[cart] = 0;  //Backtrack and try again
            }
        }
        return false; //We can't solve the problem with the given amount of colours, return false and try again with more colours.
    }
    
    /*
     * canColourCart - Check whether we can place a particular colour on a particular cart.
     */
    boolean canColourCart(int cartNumber, String allCarts[][], int colourToPlace, int chemicalsPlaced[], int numChemicals)
    {
        for(int i = 0; i < numChemicals; i++)
        {
            //If the chemical being placed is unsafe with another chemical and the colour being placed matches this chemical return false
            if(allCarts[cartNumber][i].equals("U") && colourToPlace == chemicalsPlaced[i])
            {
                return false;
            }
        }
        return true;
    }
    
    /*
     * Print the result of the cart assignments to an output file.
     */
    void printCarts(int chemicalsPlaced[], int numColours)
    {
        BufferedWriter output = null;
        try
        {
            File file = new File("output.txt");
            output = new BufferedWriter(new FileWriter(file));
            Map<Integer, ArrayList<Integer>> mapping = new HashMap<Integer, ArrayList<Integer>>();
            for(int i = 1; i <= numColours; i++)
            {
                for(int j = 0; j < chemicalsPlaced.length; j++)
                {
                    if(chemicalsPlaced[j] == i)
                    {
                        if(!mapping.containsKey(i))
                        {
                            ArrayList<Integer> myInt = new ArrayList<Integer>();
                            myInt.add(j + 1);
                            mapping.put(i, myInt); 
                        }
                        else
                        {
                            mapping.get(i).add(j + 1);
                        }
                    }
                }
            }
            //System.out.println(numColours);
            output.write(Integer.toString(numColours) + System.getProperty("line.separator"));
            //output.write("\n");
            for(Map.Entry<Integer, ArrayList<Integer>> entry: mapping.entrySet())
            {
                for(Integer cart: entry.getValue())
                {
                    output.write(cart + " ");
                    //System.out.print(cart + " ");
                }
                output.write("S S ");
                //System.out.print("S S ");
            }
            output.write("C");
            //System.out.print("C");
            output.close();
            //System.out.println(mapping);
        }
       catch (IOException e)
       {
           e.printStackTrace();
       }
    }
}