package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * ARGS n - Used prior to calling a function
* @author Yefa Qi
*/
public class ArgsCode extends ByteCode {
    private int n;
   
    /**
     *Initializes the arg bytecode with one argument
     */
    @Override
    public void init(Vector<String> arg) {
         n = Integer.parseInt(arg.get(0));        
    }
    
    @Override
     /**
     *Set up the new frame
     */
    public void execute(VirtualMachine rt) {
        rt.setNewFrame(n);
    }

     /**
     * Print the ArgsCOde
     */
    @Override
    public String toString() {
        String str = "ARGS ";
        str+= n;
        return str;
    }
}