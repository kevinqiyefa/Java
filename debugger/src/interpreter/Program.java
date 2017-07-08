package interpreter;


import java.util.Vector;
import interpreter.bytecode.LabelCode;
import interpreter.bytecode.ByteCode;
import interpreter.bytecode.CallCode;
import interpreter.bytecode.FalseBranchCode;
import interpreter.bytecode.GotoCode;
import interpreter.bytecode.debugByteCodes.LineCode;
import java.util.HashMap;
import java.util.Iterator;

/**
* The Program class will hold the bytecode program loaded from the file.
* It will also resolve symbolic addresses in the program.
* 
* @author Yefa Qi
*/
public class Program {
    private final Vector<ByteCode> programCodes;
    private final java.util.HashMap<String,Integer> labels;
     
    /**
     * Constructor:
     * Instantiates the programCode-vector and the labels-HashMap 
     */
    public Program() {
        programCodes = new Vector<>();
        labels = new java.util.HashMap<>();
    }

    /**
    * Holds the bytecodes and resolves the all symbolic addresses:
    * Only resolve ByteCode which is a instance of LabelCode, CallCode, GotoCode,or FalsebranchCode
    */
    public void resolveAddresses() {
        
        ByteCode code;

        Vector<CallCode> cc = new Vector<>();
        Vector<GotoCode> gc = new Vector<>();
        Vector<FalseBranchCode> fc = new Vector<>();
       
        Iterator<ByteCode> iterator = programCodes.iterator();
        
        while (iterator.hasNext()) {
            code = iterator.next();
            //Saves the labels in temporary data structure
            if (code instanceof LabelCode) {
                LabelCode Lcode = (LabelCode) code;
                String labelName = Lcode.getLabelString();
                labels.put(labelName,programCodes.indexOf(code));
            }    
            else {
                //add a CALL bytecode to vector cc
                if(code instanceof CallCode){
                    cc.add((CallCode)code);
                }
                 //add a GOTO bytecode to vector gc
                else if(code instanceof GotoCode){
                    gc.add((GotoCode)code);
                }
                 //add a FALSEBRANCH bytecode to vector fc
                else if(code instanceof FalseBranchCode){
                    fc.add((FalseBranchCode)code);
                }
               
            }         
       }
        //slove any branch addresses that belongs to CALL bytecode
        for (CallCode Ccode: cc){
            String CallLabelName;
            Vector<String> temp = new Vector<String>();
            CallLabelName = Ccode.getLabelString();
            String arg = Integer.toString(labels.get(CallLabelName));
            temp.add(arg);
            Ccode.init(temp);
        }
        //slove any branch addresses that belongs to GOTO bytecode
        for (GotoCode Gcode: gc){
            String GotoLabelName;
            Vector<String> temp = new Vector<String>();
            GotoLabelName = Gcode.getLabelString();
            String arg = Integer.toString(labels.get(GotoLabelName));
            temp.add(arg);
            Gcode.init(temp);
        }
        //slove any branch addresses that belongs to FALSEBRANCH bytecode
        for (FalseBranchCode Fcode: fc){
            String FalseBLabelName;
            Vector<String> temp = new Vector<String>();
            FalseBLabelName = Fcode.getLabelString();
            String arg = Integer.toString(labels.get(FalseBLabelName));
            temp.add(arg);
            Fcode.init(temp);
        }
  
    }

    /**
    * Adds bcytecode object into the program-vector.
    * @param bc obcject to be added
    */
    public void addCode(ByteCode bc) {
            programCodes.add(bc);
    }

    /**
    * Gets and returns bytecode at a given location from the program-vector.
     * @param codeIndex A specific code index
     * @return the bytecode at a specific index 
    */
    public ByteCode getCode(int codeIndex) {
        return programCodes.get(codeIndex);
    }
    
    
    public HashMap<Integer,Boolean> getBreakableLines() {
        HashMap<Integer,Boolean> lines = new HashMap<Integer,Boolean>();
        
        for (ByteCode b: programCodes) {
            if (b instanceof LineCode) {
                LineCode lcode = (LineCode) b;
                int line = lcode.getNumber();
                if (line != -1)
                    lines.put(line, Boolean.TRUE);
            }
        }
        return lines;
    }
}