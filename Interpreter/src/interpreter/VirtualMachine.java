package interpreter;

import java.util.Stack;
import interpreter.bytecode.ByteCode;
import interpreter.bytecode.DumpCode;

/**
* The VirtualMachine executes the program and interface all parts of the Interpreter.
* @author Yefa Qi
*/
public class VirtualMachine {
    private RunTimeStack runStack;
    private int pc, count = 0;
    private Stack<Integer> returnAddrs;
    private boolean isRunning;
    private final Program program;
    private boolean dumping;
    
     /**
     * Constructor:
     * Initializes the program object.
     * @param p the Program to run
     */
    public VirtualMachine(Program p) {
        program = p;
    }

    /**
    * Execute the Program by the program object 
    */
    public void executeProgram() {
        dumping = false;
        pc = 0;
        runStack = new RunTimeStack();
        returnAddrs = new Stack<Integer>();
        isRunning = true;
        while (isRunning) {
            ByteCode code = program.getCode(pc);
            code.execute(this); 
            //check if dump on or off
            if (dumping){            
                count ++;              
                if (!( code instanceof DumpCode)){                    
                    System.out.println(code); //print dump code
                    runStack.dump();  //print the runStack
                }
                else{
                    if(count != 1){runStack.dump();} //print the runStack 
                }
            }            
            pc++;
        }
    }
    
    /**
    * Call RunTimeStack and get the current frame size
     * @return The size of the frame
    */
    public int getFrameSize(){
        return runStack.getFrameSize();
    }
    
    /**
    * Returns the value of the runStck at the i of frame 
     * @param i The location of the value
     * @return Value at a given location
    */
    public int setValueOfOffset(int i){
        return runStack.valueAtOffset(i);
    }
    
    /**
    * Stars a new framePointer at a given offset
     * @param offset Indicates the number of slots down from the top of RunTimeStack 
     *              for starting the new frame. 
    */
    public void setNewFrame(int offset) {
        runStack.newFrameAt(offset);
    }
    
    /**
    * Returns the value of the runStck at the i of frame 
     * @return Top item from the runStck
    */
    public int popRunStack() {
        return runStack.pop();
    }

    /**
    * Pushes item from the runStck
     * @param arg Push item i on the runStack
     * @return the item just pushed
    */
    public int pushRunStack(int arg) {
        return runStack.push(arg);
    }
     
    /**
     * Load variable onto the runStack
     * @param offset The location to store the value.
     * @return Returns the value at the requested location
    */
    public int loadRunStack(int offset) {
        return runStack.load(offset);
    }
    /**
     * Stop the Virtual Machine
     */
    public void stopVM() {
        isRunning = false;
    }
     /**
     * Setting to dump on or off
     * @param status The Dumping status
     */
    public void setDump(String status) {
        dumping = "ON".equals(status);
    }

     /**
     * Store variable into the offset
     * @param offset The location to store the value.
    */
    public void storeRunStack(int offset) {
        runStack.store(offset);
    }    
    
    /**
    * Peek the top item on the runStack  
     * @return the top item on runStack
    */
    public int getTopOfStack() {
        if(!runStack.Empty()){
            return runStack.peek();
        }
        else {return -99;}    
    }
    /**
     * Sets the number of program counter
     * @param addr The number of the Program counter
     */
    public void setProgramCounter(int addr) {
        pc = addr;
    }

     /**
     * Push the address the return Address
     * @param addr The address that pushed
    */
    public void pushReturnAddress( int addr ) {
        returnAddrs.push( addr );
    }
    
    /**
     * Pop the return Address
     * @return The top item on the returnAddress
    */
    public int popReturnAddress() {
        return (Integer)returnAddrs.pop();
    }
    
    /**
    * Return the function value and pop the top frame after the function returns
    */
    public void popFrame() {
        runStack.popFrame();
    }
    /**
     * Returns the number of program counter
     * @return the program counter
     */
    public int getPC() {
        return pc;
    }
}