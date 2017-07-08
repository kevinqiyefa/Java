package interpreter.bytecode.debugByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.debugger.DebuggerVM;
import java.util.Vector;

/**
* FormalCode adds to the symbol table a variable that
* was passed into the current function.
* @author Ramin
*/
public class FormalCode extends ByteCode {
    private int offset;
    private String id;
    
    @Override
    public void execute(VirtualMachine M) {
        DebuggerVM dvm = (DebuggerVM)M;
        
        dvm.addSymbol(id, offset);
        dvm.CheckStepBreaks();
        //super.execute(M);
    }
    @Override
    public void init(Vector<String> v) {
        id = v.firstElement();
        offset = Integer.parseInt(v.elementAt(1));
    }
}