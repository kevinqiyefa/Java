package interpreter.debugger;

/**
* StepObject is an abstract base class used to allow breaking function
* for step commands in a unifed and cohesive manner.
* @author Ramin
*/
abstract public class StepObject {
    //check to see if we should break the current execution.
    abstract public void CheckBreakCondition(DebuggerVM DebugVM);
}