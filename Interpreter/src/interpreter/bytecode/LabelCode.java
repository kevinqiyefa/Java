package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;
/**
 * Label targets for branches
 * @author Yefa Qi
*/
public class LabelCode extends ByteCode {
    private String label;
    
    @Override
    public void init(Vector<String> arg) {
        label = arg.get(0);
    }
    
    @Override
    public void execute(VirtualMachine vm) {
        //// do nothing when executed
    }

    @Override
    public String toString() {        
        String str = "LABEL ";
        str+= label;
        return str;
    }
    
    public String getLabelString() {
        return label;
    }

}