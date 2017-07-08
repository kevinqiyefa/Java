package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;
/**
 * Write the value on tip of the stack to output; leave the value on top of the stack.
* @author Yefa Qi
*/
public class WriteCode extends ByteCode {

    @Override
    public void init(Vector<String> arg) {
        //write has no arguments
    }
    
    @Override
    public void execute(VirtualMachine VM) {
        System.out.println(VM.getTopOfStack());
    }
    
    @Override
    public String toString() {
        String str = "WRITE";
        return str;
    }
}