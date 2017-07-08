package interpreter.debugger;

/**
* StepOut requests the virtual machine to break just
* after executing the current function completely.
* @author Ramin
*/
public class StepOut extends StepObject {
    private int stackSize;
    
    StepOut(int currSize) {
        stackSize = currSize;
    }
    
    @Override
    public void CheckBreakCondition(DebuggerVM DebugVM) {
        int size = DebugVM.getRecordsSize();
        if (stackSize > size)
            DebugVM.setBreak();
    }
}
