package interpreter.debugger.ui;

import interpreter.debugger.DebuggerVM;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
* The User Interface for the X Debugger, implemented as a command line.
* @author Ramin
*/
public class DebugUserInterFaces {
    private DebuggerVM DebugVM;
    private boolean debugging;
    private HashMap<String,String> methods; //hashmap of methods
    /**
* construct a new DebugUI, and initialize the methods hashmap.
* @param VM - DebuggerVM object to debug
*/
    public DebugUserInterFaces(DebuggerVM VM) {
        DebugVM = VM;
        
        //initialize hashmap of command to method pairs
        methods = new HashMap<String,String>();
        methods.put("lstsrc", "listSource");
        methods.put("?", "help");
        methods.put("vars", "showVariables");
        methods.put("setbp","setBreakPoints");
        methods.put("clrbp","clearBreakPoints");
        //methods.put("trace", "trace");
        methods.put("lstbkpts","showBreakPoints");
        //methods.put("lstcalls", "showCalls");
        methods.put("cont", "Continue");
        methods.put("stpin", "StepIn");
        methods.put("stpout", "StepOut");
        methods.put("stpover", "StepOver");
        methods.put("halt", "Halt");
    }
    
    /**
* The user interaction loop for the debugger user interface.
*/
    public void debug() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String command [] = null;
        String methodName;
        Method m;
        boolean execute;
        
        debugging = true;
        System.out.println(DebugVM.showCurrentFunction());
        
        do {
            execute = false;
            
            System.out.println("\ntype \"?\" for help");
            System.out.print(">>");
            
            try { //get command from user.
                command = in.readLine().split("\\s");
            } catch (IOException ex) {
                System.out.printf("Error reading from keyboard");
            }
            
            methodName = methods.get(command[0]);
            
            
            //get the command via reflection
            if (methodName != null) {
                try {
                    m = this.getClass().getMethod(methodName, String[].class );
                    //call the command
                     Object[] temp = new Object[]{command};
                    execute = (boolean) m.invoke(this,temp);
                    
                } catch ( IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException ex){
                    System.out.println("Error, java reflection failed on DebugUI: " + ex.getMessage());
                }
            
            } else
                System.out.println("unknown command");
            
            //execute the vm as told to.
            if (execute) {
                DebugVM.executeProgram();
                if (!DebugVM.isRunning())
                    debugging = false;
                else
                    System.out.print(DebugVM.showCurrentFunction());
            }
        } while (debugging);
        
        System.out.println("******Execution Halted*******");
    }
    /**
* List the source file of the Debugger.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
    public boolean listSource(String command[]) {
        System.out.println(DebugVM.showCurrentFunction());
        return false;
    }
    /**
* List the commands for the UI.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
    public boolean help(String command[]) {
        showCommands();
        return false;
    }
    /**
* Show the list of local variables.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
    public boolean showVariables(String command[]) {
        System.out.print(DebugVM.displayVariables());
        return false;
    }
    /**
* Set break points as the specified lines.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
    public boolean setBreakPoints(String command[]) {
        for (int i = 1; i < command.length; i++) {
            int line = Integer.parseInt(command[i]);
            if (DebugVM.CheckAndSetBreakpt(line))
                System.out.printf("breakpoint set: %d\n", line);
            else
                System.out.printf("illegal breakpoint: %d\n", line);
        }
        return false;
    }
    /**
* Clear breakpoints at the specified lines, of all of them at once.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
    public boolean clearBreakPoints(String command[]) {
        if (command.length == 1) {
            DebugVM.ClearAllBkpts();
            System.out.println("All breakpoints cleared");
        }
        else {
            for (int i = 1; i < command.length; i++) {
                int line = Integer.parseInt(command[i]);
                if (DebugVM.CheckAndClearBreakpt(line))
                    System.out.printf("breakpoint cleared: %d\n", line);
                else
                    System.out.printf("no breakpoint found: %d\n", line);
            }
        }
        return false;
    }
    /**
* Set tracing on the DebuggerVM to on or off.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
   /* public boolean trace(String command[]) {
        if (command.length != 1) {
            if (command[1].matches("on")) {
                DebugVM.TraceOn();
                System.out.println("function tracing set");
            }
            else if (command[1].matches("off")) {
                DebugVM.TraceOff();
                System.out.println("function tracing cleared");
            }
            else
                System.out.printf("unknown parameter %s\n", command[1]);
        } else
            System.out.println("no parameter (on/off) specified");
        return false;
    }
    /**
* Show all the current breakpoints.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
    public boolean showBreakPoints(String command[]) {
        System.out.println(DebugVM.showBreakpts());
        return false;
    }
    /**
* Print out the Call Stack.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
    public boolean showCalls(String command[]) {
        System.out.print(DebugVM.showCallStack());
        return false;
    }
    /**
* Step Into a function.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
    public boolean StepIn(String command[]) {
        DebugVM.stepIn();
        return true;
    }
    /**
* Step Out of a function.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
    public boolean StepOut(String command[]) {
        DebugVM.stepOut();
        return true;
    }
    /**
* Step over the current line.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
   public boolean StepOver(String command[]) {
        DebugVM.stepOver();
        return true;
    }
    /**
* Continue executing the program on the debugger.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
    public boolean Continue(String command[]) {
        return true;
    }
    /**
* Halt the debugger and quit.
* @param command array of string arguments for this function.
* @return boolean which signals if the DebugVirtualMachin should execute.
*/
    public boolean Halt(String command[]) {
        debugging = false;
        return false;
    }
    /**
* Outputs to the screen the commands that can be given to the
* debugger user interface. The elements in explanations in the second
* array correspond to the commands in the first array.
*/
    private void showCommands() {
        //commands for the debug ui
        String cmds[] = {
            "lstsrc",
            "?",
            "vars",
            "setbp N P Q...",
            "clrbp N P Q...",
            "trace on/off",
            "lstbkpts",
            "lstcalls",
            "cont",
            "stpin",
            "stpout",
            "stpover",
            "halt"};
        
        //explainations for commands
        String explanation [] = {
            "prints out the current function",
            "print this command list",
            "display local variables",
            "sets breakpoints at the specified lines",
            "clears breakpoints at the specified lines, or all if none specified",
            "set tracing to on or off",
            "list all the break points set",
            "list the current call stack",
            "continue executing the program",
            "step into the next function call",
            "step out of the current function",
            "step over the current line",
            "halt the debugger"};
        
        for (int i = 0; i < cmds.length; i++) {
            System.out.printf("%-18s%s\n",cmds[i], explanation[i]);
        }
    }
}