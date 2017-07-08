package interpreter.bytecode.debugByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.debugger.DebuggerVM;
import java.util.Vector;

/**
* The LineCode bytecode modifies the current line being debugged
 on the DebuggerVM. Also does break checking on the
* VirtualMachine.
* @author Ramin
*/
public class LineCode extends ByteCode {
    private int lineNum;
    
    @Override
    public void execute(VirtualMachine M) {
        DebuggerVM dvm = (DebuggerVM)M;
        dvm.setCurrentLine(lineNum);
        dvm.CheckLineBreaks();
        dvm.CheckStepBreaks();
    }

    @Override
    public void init(Vector<String> v) {
        lineNum = Integer.parseInt(v.firstElement());
    }
    /**
* Get the number of the line this ByteCode represents
* @return an integer number for the line.
*/
    public int getNumber() {
        return lineNum;
    }
}