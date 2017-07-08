package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;
/**
 * Pop the top levels of the running stack
* @author Yefa Qi
*/
public class PopCode extends ByteCode {
    protected int n;
    
    @Override
    public void init(Vector<String> arg) {
        n = Integer.parseInt(arg.get(0));
    }
    
    @Override
    public void execute(VirtualMachine vm) {
        for (int i = 1; i <= n; i++)
            vm.popRunStack();
    }

    @Override
    public String toString() {
        String str = "POP ";
        str+= n;
        return str;
    }
}