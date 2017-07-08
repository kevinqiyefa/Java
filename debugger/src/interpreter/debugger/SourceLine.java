package interpreter.debugger;

/**
* SourceLine holds a line from the source code file.
* It allows breakpoints to be set on each line.
* @author Ramin
*/
public class SourceLine {
    private String sourceLine;
    private boolean isBreakptSet;
    private boolean breakpointAllowed;
    
    /**
* Constructs a new LineEntry object.
* @param line a line of text from the source file.
*/
    public SourceLine(String line) {
        sourceLine = line;
    }
    /**
* Set a breakpoint at this SourceLine.
*/
    public void setBreakpt() {
        isBreakptSet = true;
    }
    /**
* Remove a breakpoint from this Line.
*/
    public void clrBreakpt() {
        isBreakptSet = false;
    }
    /**
* Test to see if there is a breakpoint set.
* @return True if there is a breakpoint, false if not.
*/
    public boolean hasBreakpt() {
        return isBreakptSet;
    }
    /**
* Get the source line that this SourceLine represents.
* @return a string that is a line from a source file.
*/
    public String getSourceLine() {
        return sourceLine;
    }
}