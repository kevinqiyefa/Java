package interpreter.bytecode.debugByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.ReturnCode;
import interpreter.debugger.DebuggerVM;

/**
* Version of ReturnCode used by the debugger.
* @author Ramin
*/
public class ReturnCodeDebugger extends ReturnCode {
    @Override
    public void execute(VirtualMachine VM) {
        DebuggerVM DVM = (DebuggerVM)VM;
        
        //if (DVM.CheckTrace())
          //  System.out.println(DVM.OutputTraceExit());
        
        super.execute(VM);
        DVM.CheckStepBreaks(); //check if we need to break;
    }
}