package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * Halt stop the execution
 * @author Yefa Qi
*/
public class HaltCode extends ByteCode {

    //Initializes the halt byte code with no arguments
    @Override
    public void init(Vector<String> arg) {
        //do nothing
    }
    
    @Override
    public void execute(VirtualMachine vm) {
        vm.stopVM();
    }

    @Override
    public String toString() {
        String str = "HALT";      
        return str;
    }
}