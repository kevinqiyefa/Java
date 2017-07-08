package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * Pop top 2 levels of the stacks and perform indicated operation
 * @author Yefa Qi
*/
public class BopCode extends ByteCode {
    private String op;
     /**
     *Initializes the arg bytecode with one argument
     */
    @Override
    public void init(Vector<String> arg) {
        op = arg.get(0);
    }
    
     /**
     * Get the top two elements and do the operation, then push back to the runStack
     */
    @Override
    public void execute(VirtualMachine vm) {
        int Top_Level, Second_Level;
        int result = 0;
        
        Top_Level = vm.popRunStack();
        Second_Level = vm.popRunStack();
        
        //addition
        if ("+".equals(op)) {
            result = Second_Level + Top_Level;
        }
        //subtraction
        else if ("-".equals(op)) {
            result = Second_Level - Top_Level;
        }
        //division
        else if ("/".equals(op)) {
            result = Second_Level / Top_Level;
        }
        //multiplication
        else if ("*".equals(op)) {
            result = Second_Level * Top_Level;
        }
        //equality
        else if ("==".equals(op)) {
            if (Second_Level == Top_Level)
                result = 1;
            else
                result = 0;
        }
        //not equal
        else if ("!=".equals(op)) {
            if (Second_Level != Top_Level)
                result = 1;
            else
                result = 0;
        }
        //less than or equal
        else if ("<=".equals(op)) {
            if (Second_Level <= Top_Level)
                result = 1;
            else
                result = 0;
        }
        //greater than or equal
        else if (">=".equals(op)) {
            if (Second_Level >= Top_Level)
                result = 1;
            else
                result = 0;
        }
        //greater than
        else if (">".equals(op)) {
            if (Second_Level > Top_Level)
                result = 1;
            else
                result = 0;
        }
        //less than
        else if ("<".equals(op)) {
            if (Second_Level < Top_Level)
                result = 1;
            else
                result = 0;
        }
        //or
        else if ("|".equals(op)) {
            if (Second_Level > 0 || Top_Level > 0)
                result = 1;
            else
                result = 0;
        }
        //and
        else if ("&".equals(op)) {
            if (Second_Level > 0 && Top_Level > 0)
                result = 1;
            else
                result = 0;
        }
        vm.pushRunStack(result);
    }
    
     /**
     * Print the BopCOde
     */
    @Override
    public String toString() {
        String str = "BOP ";
        str+= op;
        return str;
    }
}