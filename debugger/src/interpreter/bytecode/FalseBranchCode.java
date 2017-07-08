package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;
/**
 * Fralsebranch - pop yje top of the stack; if it's false()0 then branch 
 *                  to or execute the next bytecode 
 * @author Yefa Qi
*/
public class FalseBranchCode extends ByteCode {
    private String label;
    private int addr;
    
    @Override
    public void init(Vector<String> args) {
         try {
            addr = Integer.parseInt(args.get(0));
        } catch (Exception e) {
            label = args.get(0);
        }
    }
    
    @Override
    public void execute(VirtualMachine M) {
        int topStack = M.popRunStack();
        //if false(0) then branch to <label>, otherwise do nothing
        if (topStack == 0)
            M.setProgramCounter(addr-1);
    }
    
    @Override
    public String toString() {
        String str = "FALSEBRANCH ";
        str+= label;
        return str;
    }

    public String getLabelString() {
        return label;
    }
    
}