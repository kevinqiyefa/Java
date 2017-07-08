

package towerofhanoi;

/**
 * @ CSC 413
 * @ Author: Qi, Yefa
 * @ ID: 913163503
 */
import java.util.*;
public class TowerOfHanoi {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        int numberDisks = 4;
        //create objects for each tower clas
        TowerOfHanoi solveHanoi = new TowerOfHanoi();
        Tower pegA = solveHanoi.new Tower ('A', 1);
        Tower pegB = solveHanoi.new Tower ('B', 2 );
        Tower pegC = solveHanoi.new Tower ('C', 3);
       


        //add all the disks to the first peg
        for(int i = numberDisks; i > 0; i--){
            System.out.print(pegA.getStack().push(new Integer(i)));            
        } 
        System.out.println ();
        //call and slove the Tower of hanoi
        moveDisks (numberDisks, pegA, pegB, pegC);
        
    }
            
    private static void moveDisks (int diskNum, Tower source, Tower utility, Tower dest){
        //base case
        if (diskNum== 0)
           return;
        // move the top N - 1 disks from source peg to utility peg
        moveDisks(diskNum-1, source, dest, utility);  
        
        // Move one disk from source to destination peg.
        dest.stack.push(source.stack.pop());
        System.out.print(diskNum + " from " + source.getPegName() + " to " + dest.getPegName()+"\t");
        //print the disks list
        printDisks(source, utility, dest);
        System.out.println();
        
        // move the top N - 1 disks from utility peg to dest peg
        moveDisks(diskNum-1, utility, source, dest);

    }


    private static void printDisks(Tower pegA, Tower pegB, Tower pegC ){
        //create iterator objects for eatch Tower's stack
        Iterator printl = pegA.getStack().iterator();
        Iterator print2 = pegB.getStack().iterator();
        Iterator print3 = pegC.getStack().iterator();
        
         //test anf print the Tower's stack which has a ID equal 1
        if (pegA.getDiskNumber() == 1){
            while (printl.hasNext()){
                System.out.print(printl.next());
            }
            System.out.print("\t\t");
        }
        else if (pegB.getDiskNumber() == 1){
                while (print2.hasNext()){
                    System.out.print(print2.next());
                }
                System.out.print("\t\t");
            } 
            else if (pegC.getDiskNumber() == 1){
                    while (print3.hasNext()){
                        System.out.print(print3.next());
                    }
                    System.out.print("\t\t");
                 }
        
        //test and print the Tower's stack which has a ID equal 2
        if (pegA.getDiskNumber() == 2){
            while (printl.hasNext()){
                System.out.print(printl.next());
            }
            System.out.print("\t\t");
        }
        else if (pegB.getDiskNumber() == 2){
                while (print2.hasNext()){
                    System.out.print(print2.next());
                }
                System.out.print("\t\t");
            } 
            else if (pegC.getDiskNumber() == 2){
                    while (print3.hasNext()){
                        System.out.print(print3.next());
                    }
                    System.out.print("\t\t");
                 }
        
        //test anf print the Tower's stack which has a ID equal 3
       if (pegA.getDiskNumber() == 3){
            while (printl.hasNext()){
                System.out.print(printl.next());
            }
            System.out.print("\t\t");
        }
        else if (pegB.getDiskNumber() == 3){
                while (print2.hasNext()){
                    System.out.print(print2.next());
                }
                System.out.print("\t\t");
            } 
            else if (pegC.getDiskNumber() == 3){
                    while (print3.hasNext()){
                        System.out.print(print3.next());
                    }
                    System.out.print("\t\t");
                 }
                
    }
    
    //Tower class
    public class Tower  {
        private final Stack stack = new Stack();
        private final int diskNumber;
        private final char pegName;
        
        //default constructor
        public Tower(){
            diskNumber = 0;
            pegName = '\u0000';
        }
      
        //constructor
        public Tower(char peg, int disk){
            diskNumber = disk;
            pegName = peg;
        }
        //get diskNumber
        public int getDiskNumber(){
            return diskNumber;
        }
        //get pegname
        public char getPegName(){
            return pegName;
        }
        //get stack
        public Stack getStack(){
            return stack;
        }
    }  
}
