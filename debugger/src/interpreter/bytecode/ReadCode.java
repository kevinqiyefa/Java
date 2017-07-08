package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Scanner;
import java.util.Vector;

/**
 * Read an integer; prompt the user for input; put the value just read 
 *  on top of the stack.
* @author Yefa Qi
*/
public class ReadCode extends ByteCode {   
    private int input;
    
     @Override 
    /**
     * Initializes the byte code with no arguments
     */
    public void init(Vector<String> arg) {
        //do nothing
    }
    
    @Override
    public void execute(VirtualMachine VM) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter an Integer: ");
        input = scan.nextInt();
        VM.pushRunStack(input);
    }

    @Override
    public String toString() {
        return String.format("READ");
    }
}