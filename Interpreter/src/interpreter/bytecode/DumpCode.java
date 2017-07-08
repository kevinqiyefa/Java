package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * To decide the dump flag on the virtual machine
 * @author Yefa Qi
*/
public class DumpCode extends ByteCode {
    String status;
    
    @Override
    public void init(Vector<String> arg) {
        status = arg.get(0);
    }
    
    /**
    * Decides the dump flag
    */
    @Override
    public void execute(VirtualMachine vm) {
        vm.setDump( status );

    }
    
}