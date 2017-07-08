package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * Lit loads the literal value n
 * @author Yefa Qi
*/
public class LitCode extends ByteCode {
    protected String id;
    protected int value;
        
    @Override
    public void init(Vector<String> arg) {
        value = Integer.parseInt(arg.get(0));
        if (arg.size() != 2)
            id = "";
        else
            id = arg.get(1);
    }
    
    @Override
    public void execute(VirtualMachine vm) {
        vm.pushRunStack(value);
    }

    @Override
    public String toString() {
        String tmp = "LIT "+ value;
        if (!id.isEmpty())
            tmp += " " + id + "  int " + id;
        return tmp;
    }
}