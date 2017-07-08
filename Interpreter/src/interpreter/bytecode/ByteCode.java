package interpreter.bytecode;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 * Abstract class for all types the bytecode
 * @author Yefa Qi
*/
public abstract class ByteCode {
    
    public abstract void init( Vector<String> args);
    public abstract void execute( VirtualMachine vm);

}