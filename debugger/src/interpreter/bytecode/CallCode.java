package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * Call a function or transfer control to the indicated funtion
 * @author Yefa Qi
*/

public class CallCode extends ByteCode {
    private int pc;
    private int size;
    private String str ="";
    private int[] Array;
    private String label;
    private int addr;
    
    @Override
    public void init(Vector<String> args) {
         try {
            addr = Integer.parseInt(args.get(0));
        } catch (Exception e) {
            label = args.get(0);
        }
    }
    
    @Override 
    public void execute(VirtualMachine vm) { 
        vm.FuncBranch(addr);
        size = vm.getFrameSize();
        Array = new int[size];
        for (int i = 0; i < size; i++) {
            Array [i] = vm.setValueOfOffset(i);
        }
        
    }
    
    @Override
    public String toString() {

        int index = label.indexOf("<");
        if (index == -1)
           index = label.length();
        String funcname = label.substring(0, index);
        str += "CALL " + label+" "+funcname;       
        str += "(";      
        int i = 0;              
        //output the function arguments
        while(i < size) {
             str += Array[i];
             if (i != (size - 1))
                str += ",";
                i++;
             }
             str += ")";
             return str;
    }

    public String getLabelString() {
        return label;
    }
}