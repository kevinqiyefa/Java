package interpreter.debugger;

import interpreter.Program;
import interpreter.RunTimeStack;
import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.bytecode.debugByteCodes.FormalCode;
import interpreter.bytecode.debugByteCodes.FunctionCode;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

/**
* DebuggerVM allows an X program to be debugged.
* @author Ramin
*/
public class DebuggerVM extends VirtualMachine {
    private Vector<SourceLine> lineEntries;
    private HashMap<Integer,Boolean> lines;
    private Stack<FunctionEnvironmentRecord> records;
    private StepObject step;
    private boolean breakExecution;
    private boolean trace;
    
    /**
* Construct a new Debug Virtual Machine.
* @param p Program object to be executed.
* @param vs Vector of source line entries.
*/
    public DebuggerVM(Program p, Vector<SourceLine> vs) {
        super(p);
        lineEntries = vs;
        records = new Stack<FunctionEnvironmentRecord>();
        lines = program.getBreakableLines();
        isRunning = false;
    }
//-------------------------------Overloaded functions-----------------------------------
    @Override
    public void executeProgram() {
        if (!isRunning) {
            pc = 0;
            runStack = new RunTimeStack();
            returnAddrs = new Stack<Integer>();
            records.push(new FunctionEnvironmentRecord());
            breakExecution = false;
            isRunning = true;
        }
        
        while (isRunning) {
            ByteCode code = program.getCode(pc);
            code.execute(this);
            pc++;
            
            if (breakExecution) {
                breakExecution = false;
                break;
            }
        }
    }
    @Override
    public void FuncBranch(int addr){
        super.FuncBranch(addr);
        FunctionEnvironmentRecord fctEnvRcd = new FunctionEnvironmentRecord();
        records.push(fctEnvRcd);
    }
    @Override
    public void returnToLastAddr() {
        super.returnToLastAddr();
        records.pop();
    }
//-------------------------------New Debug functions-----------------------------------
    /**
* Test if the DebuggerVM is currently
 running a program object.
* @return true if the program is still running, false otherwise.
*/
    public boolean isRunning() {
        return isRunning;
    }
    /**
* Gives a string for the function trace on entry.
* @return string containing the trace for the function
*/
    public String OutputTraceEntry() {
        String traceStr = "";
        for (int i = 0; i < records.size(); i++)
            traceStr += " ";
        traceStr += records.peek().getFunctionName() + "(";
        
        int size = runStack.getFrameSize();
        for (int i = 0; i < size; i++) {
            traceStr += runStack.valueAtOffset(i);
            if (i != size - 1)
                traceStr += ",";
        }
        traceStr += ")";
        return traceStr;
    }
    /**
* Gives a string for the function trace on exit.
* @return string containing the trace for the function
*/
    public String OutputTraceExit() {
        String traceStr = "";
        for (int i = 0; i < records.size(); i++)
            traceStr += " ";
        
        traceStr += "exit: ";
        traceStr += records.peek().getFunctionName() + ": ";
        
        int size = runStack.getFrameSize();
        traceStr += runStack.valueAtOffset(size - 1);
        return traceStr;
    }
    /**
* Check if we need to trace.
* @return boolean value if we are tracubg if not.
*/
  /*  public boolean CheckTrace() {
        return trace;
    }
    /**
* Set trace to on.
*/
 /*   public void TraceOn() {
        trace = true;
    }
    /**
* set tracing to off.
*/
   /* public void TraceOff() {
        trace = false;
    }
    /**
* Checks if the DebugVM needs to break at a line.
*/
    public void CheckLineBreaks() {
        int lineNum = records.peek().getCurrentLine();
        
        if ((lineNum > 0) && lineEntries.elementAt(lineNum - 1).hasBreakpt())
            this.setBreak();
    }
    /**
* Checks to see if the DebuVM needs to break as a
* result of a step object.
*/
    public void CheckStepBreaks() {
        if (step != null)
            step.CheckBreakCondition(this);
    }
    /**
* Request the DebugVM to break execution.
*/
    public void setBreak() {
        breakExecution = true;
        step = null;
    }
    /**
* Set a breakpoint at a specified line.
* @param lineNumber the number of the line to set the break point at.
* @return True if the break point was set, false if not.
*/
    public boolean CheckAndSetBreakpt(int lineNumber) {
        Boolean bool = lines.get(lineNumber);
        
        if (bool != null) {
            lineEntries.elementAt(lineNumber - 1).setBreakpt();
            return true;
        }
        else
            return false;
    }
    /**
* Clear a breakpoint at a specified line.
* @param lineNumber the number of the line to clear the break point at.
* @return True if the break point was cleared, false if it wasn't set to
* begin with.
*/
    public boolean CheckAndClearBreakpt(int lineNumber) {
        Boolean bool = lines.get(lineNumber);
        
        if (bool != null) {
            if (lineEntries.elementAt(lineNumber - 1).hasBreakpt()) {
                lineEntries.elementAt(lineNumber - 1).clrBreakpt();
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
    /**
* Clear all break points on the DebuggerVM.
*/
    public void ClearAllBkpts() {
        for (SourceLine ent: lineEntries)
            ent.clrBreakpt();
    }
    /**
* Lists all the break points currently set in the
 DebuggerVM.
* @return A string representing the list of lines with break points set
*/
    public String showBreakpts() {
        String bkpts = "Breakpoints: ";
        for (int i = 0; i < lineEntries.size(); i++) {
            SourceLine ent = lineEntries.elementAt(i);
            if (ent.hasBreakpt())
                bkpts += (i+1) + " ";
        }
        return bkpts;
    }
    /**
* Set the DebuggerVM to step out of the current function.
*/
  public void stepOut() {
        step = new StepOut(records.size());
    }
    /**
* Set the DebuggerVM to step over a line.
*/
   public void stepOver() {
        int size = records.size();
        int line = records.peek().getCurrentLine();
        step = new StepOver(size,line);
    }
    /**
* Set the DebuggerVM to step into a function.
*/
  public void stepIn() {
        int size = records.size();
        int line = records.peek().getCurrentLine();
        step = new StepIn(size,line);
    }
    /**
* Return a string representing the current function thats executing
* in the Debug Virtual Machine.
* @return string representation of the current function, with break
* points listed and the current line pointed out.
*/
    public String showCurrentFunction() {
        String func = "";
        int start, end;
        
        //if we haven't started execution
        if (records.empty() || records.peek().getFunctionName() == null) {
            start = 1;
            end = lineEntries.size() + 1;
        }
        //if we are in an intrinsic function
        else if (records.peek().getFunctionName().matches("Read|Write")) {
            func = "**********" + records.peek().getFunctionName() + "**********";
            return func;
        }
        //else we are in a user defined function
        else {
            start = records.peek().getStartLine();
            end = records.peek().getEndLine() + 1;
        }
        //get the source for the function
        for (int i = start; i < end; i++) {
            if (lineEntries.elementAt(i-1).hasBreakpt())
                {func += "**";}
            else
                {func += " ";}
            func += String.format("%2d: ", i);
            func += lineEntries.elementAt(i-1).getSourceLine();
            if (isRunning && (i == records.peek().getCurrentLine()))
                func += " <-----";
            func += "\n";
        }
        return func;
    }
    /**
* Display the local variables for the current function.
* @return string representation of the local variables.
*/
    public String displayVariables() {
        String variables = "";
        
        if (!records.empty()) {
            FunctionEnvironmentRecord CurrRcd = records.peek();
            
            Set<String> vars = CurrRcd.getSymbols();
            for (String var: vars) {
                variables += var + ": ";
                int offset = CurrRcd.getSymbolValue(var);
                variables += runStack.valueAtOffset(offset) + "\n";
            }
        }
        return variables;
    }
    /**
* Display the callstack of the currently running program.
* @return string representation of the call stack.
*/
    public String showCallStack() {
        String callStack = "";
        for (int i = records.size() - 1; i > -1; i--) {
            callStack += records.elementAt(i).getFunctionName()
            + ": " + records.elementAt(i).getCurrentLine() + "\n";
        }
        return callStack;
    }
    /**
* Return the integer value of the current line.
* @return an integer.
*/
    public int getCurrentLine() {
        return records.peek().getCurrentLine();
    }
    /**
* Set the current line being debugged by the
* DebugVirtual Machine.
* @param lineNum Line that is being debugged.
*/
    public void setCurrentLine(int lineNum) {
        records.peek().setCurrentLine(lineNum);
    }
    /**
* Set the current function on the DebuggerVM,
 as well as the start and end lines of the function.
* @param funcname Name of the function
* @param startLine Number of the first line of the function
* @param endLine Number of the last line of the function
*/
    public void setCurrentFunction(String funcname, int startLine, int endLine) {
        FunctionEnvironmentRecord CurrRcd = records.peek();
        CurrRcd.setFunctionName(funcname);
        CurrRcd.setStartLine(startLine);
        CurrRcd.setEndLine(endLine);
    }
    /**
* Get the current size of the Function Environment Record stack.
* @return the size of the stack currently.
*/
    public int getRecordsSize() {
        return records.size();
    }
    /**
* Add a local variable to the symbol table for the current function.
* @param id name of the symbol.
*/
    public void addLocal(String id) {
        addSymbol(id, runStack.getFrameSize() - 1);
    }
    /**
* Add a symbol to the current function environment record.
* @param id
* @param offset
*/
    public void addSymbol(String id, int offset) {
        records.peek().enterSymbol(id, offset);
    }
    /**
* Remove the top n symbols from the symbol table
* @param n number of symbols to remove.
*/
    public void removeSymbols(int n) {
        records.peek().removeSymbols(n);
    }
    /**
* Checks to see if the next ByteCode is a function
* header bytecode.
* @return true if a HeaderCode, false otherwise.
*/
    public boolean isNextCodeFuncHeader() {
        ByteCode code = program.getCode(pc+1);
        return (code instanceof FunctionCode)||(code instanceof FormalCode);
    }
}