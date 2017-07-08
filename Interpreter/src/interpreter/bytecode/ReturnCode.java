package interpreter.bytecode;
/**
 * Returns to the current function.
* @author Yefa Qi
*/
import interpreter.VirtualMachine;
import java.util.Vector;

public class ReturnCode extends ByteCode {
    private String label;
    private int TopStack; 
 
    @Override
    public void init(Vector<String> arg) {
        if (arg.isEmpty())
            label = "";
        else
            label = arg.get(0);
    }
    
    @Override
    //Return to the next statement after the function call
    public void execute(VirtualMachine VM) {
        int pc = VM.popReturnAddress();
        VM.popFrame();
        VM.setProgramCounter(pc);
        TopStack = VM.getTopOfStack();
    }

    @Override
    public String toString() {
        String temp = String.format("RETURN");
        if (label != null) {
            int index = label.indexOf("<");
            if (index < 0)
                index = label.length();
            String funcname = label.substring(0, index);
            
            if (temp.length() >= 6){
                temp += String.format(" %s exit %s: %d", label, funcname, TopStack);
            }
            else {temp += String.format(" %s exit %s: ", label, funcname);}
        }
        return temp;
    }
}