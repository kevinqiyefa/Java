package interpreter.debugger;

/**
* StepOver causes the DebuggerVM to break after the current
 line has executed or we exited a function.
* @author Ramin
*/
public class StepOver extends StepObject {
    private int stackSize;
    private int lineNum;
    
    StepOver(int currSize, int line) {
        lineNum = line;
        stackSize = currSize;
    }

    @Override
    public void CheckBreakCondition(DebuggerVM DebugVM) {
        int num = DebugVM.getCurrentLine();
        int size = DebugVM.getRecordsSize();
        
        if (lineNum != num && size <= stackSize)
            DebugVM.setBreak();
    }
}