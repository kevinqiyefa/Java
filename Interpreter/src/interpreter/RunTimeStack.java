package interpreter;

import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

/**
* The RunTimeSatck class maintains the stack of active frames
* @author Yefa Qi
*/

public class RunTimeStack {
    private final Vector<Integer> runStack;
    private final Stack<Integer> framePointers;
    
    
     /**
     * Constructor:
     * Initializes runStack, framePointers and loads first framePointer at index 0
     */
    public RunTimeStack() {
        runStack = new Vector<>();
        framePointers = new Stack<>();
        //initial entry value in the framePointers stack of 0
        framePointers.push(0);
    }

    /**
    * Dump the RunTimeStack contents for debugging
    */
    public void dump() {

        if (!runStack.isEmpty()){
            int i = 1;
            Iterator<Integer> runStackIT = runStack.iterator();
        
            System.out.print("[");
            while (runStackIT.hasNext()){
                System.out.print(runStackIT.next());
                if(framePointers.contains(i++))
                    System.out.print("] [");
                else if (runStackIT.hasNext())
                    System.out.print(",");
            }
            
            System.out.print("] \n");
        }
    }

    /**
    * Peek the top item on the runStack  
     * @return the top item on runStack
    */
    public int peek() {
        return (Integer)runStack.lastElement();
    }

    /**
    * pop the top item from the runStck
     * @return Top item from the runStck
    */
    public int pop() {
         int index = runStack.size() - 1;
         if (framePointers.peek() != index+1)
             return (Integer)runStack.remove(index);
         else {
             return -1;
         } 
    }

    /**
    * Push item from the runStck
     * @param i Push item i on the runStack
     * @return the item just pushed
    */
    public int push(int i) {
        runStack.add(new Integer(i));
        return runStack.lastElement();

    }
    /**
    * Stars a new framePointer at a given offset
     * @param offset Indicates the number of slots down from the top of RunTimeStack 
     *              for starting the new frame. 
    */
    public void newFrameAt(int offset) {
        int index = runStack.size();
        framePointers.add(index - offset);
    }

    /**
    * Return the function value and pop the top frame after the function returns
    */
    public void popFrame() {
        int returnVal = runStack.lastElement();
        int TopIndex = runStack.size()-1;
        while ( TopIndex >= framePointers.peek() )
            runStack.removeElementAt(TopIndex --);
        runStack.add(returnVal);
        framePointers.pop();
    }
    /**
     * Store variable into the offset
     * @param offset The location to store the value.
     * @return Returns the value at the requested location
    */
    public int store(int offset) {
        int TopValue = pop();  
        int index = offset + framePointers.peek();
        runStack.setElementAt(TopValue, index);
        return runStack.get(index);
    }

     /**
     * Load variable onto the runStack
     * @param offset The location to store the value.
     * @return Returns the value at the requested location
    */
    public int load(int offset) {
        int temp = runStack.get(offset + framePointers.peek());
        runStack.add(temp);
        return runStack.lastElement();
    }
    
    /**
    * Push item onto the runStck
     * @param i Push an Integer i onto the runStack
     * @return The pushed item
    */
    public Integer push(Integer i) {
        runStack.add(i);
        return runStack.lastElement();
    }  
    
    /**
    * Returns the value of the runStck at the offset of frame 
     * @param offset The location of the value
     * @return Value at a given location
    */
    public int valueAtOffset(int offset) {
        return runStack.elementAt(framePointers.peek() + offset).intValue();
    }
    
    /**
    * Check whether or the runStack is empty
     * @return Ture or false
    */
    public boolean Empty() {
        return runStack.isEmpty();
    }
    
    /**
    * Get the current frame size
     * @return The size of the frame
    */
    public int getFrameSize() {
        int size= runStack.size();
        int frameValue = framePointers.peek();
        return size - frameValue;
        
    }
}