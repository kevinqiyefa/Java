package interpreter.bytecode.debugByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.PopCode;
import interpreter.debugger.DebuggerVM;

/**
* Version of the Pop Bytecode used by the Debugger.
* @author Ramin
*/
public class PopCodeDebugger extends PopCode {
    @Override
    public void execute(VirtualMachine vm){
        super.execute(vm);
        DebuggerVM dvm = (DebuggerVM)vm;
        dvm.removeSymbols(n);
    }
}