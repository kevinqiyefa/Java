package interpreter.bytecode.debugByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.debugger.DebuggerVM;
import java.util.Vector;

/**
* FunctionCode sets the current function in the DebuggerVM
* @author Ramin
*/
public class FunctionCode extends ByteCode {
    private String funcname;
    private int startLine;
    private int endLine;
    
    @Override
    public void execute(VirtualMachine M) {
        DebuggerVM dvm = (DebuggerVM)M;
        dvm.setCurrentFunction(funcname, startLine, endLine);
        
       // if (dvm.CheckTrace())
         //   System.out.println(dvm.OutputTraceEntry());
        dvm.CheckStepBreaks();
        //super.execute(M);
    }

    @Override
    public void init(Vector<String> v) {
        funcname = v.firstElement();
        startLine = Integer.parseInt(v.elementAt(1));
        endLine = Integer.parseInt(v.elementAt(2));
    }
    
}