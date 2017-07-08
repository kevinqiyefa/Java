package interpreter.bytecode.debugByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.LitCode;
import interpreter.debugger.DebuggerVM;


/**
* Version of the Lit Bytecode used by the debugger.
* @author Ramin
*/
public class LitCodeDebugger extends LitCode {
    @Override
    public void execute(VirtualMachine vm){
        super.execute(vm);
        DebuggerVM dvm = (DebuggerVM)vm;
        if (!id.equals(""))
            dvm.addLocal(id);
    }
}