package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * Load pushes the value in the slot which is offset n from the start 
 *  of the frame onto the top of the stack. 
 * @author Yefa Qi
*/
public class LoadCode extends ByteCode {
    private int offset;
    private String id;
    
     /**
     * Initializes the byte code with no arguments
     */
    @Override
    public void init(Vector<String> arg) {
        offset = Integer.parseInt(arg.get(0));
        id = arg.get(1);
    }
    //load the element that already on the runStack
    @Override
    public void execute(VirtualMachine vm) {
        vm.loadRunStack(offset);
    }

    @Override
    public String toString() {
        return String.format("LOAD %d %s <load %s>", offset,id,id);
    }
}