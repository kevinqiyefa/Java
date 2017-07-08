package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
* Goto jumps to other location by the label
* @author Yefa Qi
*/
public class GotoCode extends ByteCode {
    private int addr;
    private String label;

    @Override
    public void init(Vector<String> args) {
        try {
            addr = Integer.parseInt(args.get(0));
        } catch (Exception e) {
            label = args.get(0);
        }
    } 
    
    @Override
    public void execute(VirtualMachine vm) {
        vm.setProgramCounter(addr);
    }
    
    @Override
    public String toString() {
        String str = "GOTO ";
        str+= label;
        return str;
    }

    public String getLabelString() {
        return label;
    }
    
}