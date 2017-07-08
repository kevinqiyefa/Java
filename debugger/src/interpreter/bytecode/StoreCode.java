package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;
/**
 * Pop the top of the stack; store value into the offset n from the start of the frame.
* @author Yefa Qi
*/
public class StoreCode extends ByteCode {
    private String id;
    private int offset;
    private int topStack;

    
    @Override
    public void init(Vector<String> arg) {
        offset = Integer.parseInt(arg.get(0));
        if (arg.size() == 2)
            id = arg.get(1);
    }
    
    @Override
    public void execute(VirtualMachine VM) {
        topStack = VM.getTopOfStack();
        VM.storeRunStack(offset);
    }

    @Override
    public String toString() {
        return String.format("STORE %d %s  %s = %d",offset, id, id, topStack);
    }
}