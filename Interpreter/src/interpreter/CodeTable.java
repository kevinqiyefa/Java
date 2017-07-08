package interpreter;

/**
* The CodeTable is used by ByteCodeLoader and contains a HashMap with keys
* being the bytecode string. It can also be used to construct an instance of the bytecode.
* 
* @author Yefa Qi
*/
public class CodeTable {
    public static java.util.HashMap<String,String> byteCodes = new java.util.HashMap<>();
    
    /**
    * Constructor. Does nothing.
    */
    private CodeTable() {}
    
     /**
     * Returns the class name that associated with bytecode.
     * @param bytecode A String that represents the bytecode name
     * @return The name of the class that will be instantiated
     */
    public static String get(String bytecode) {
        return byteCodes.get(bytecode);
    }
    
    /**
     * Hard-code the statements that populate the data in the CodeTable
     */

    public static void init() {
        
        byteCodes.put("HALT", "HaltCode");
        byteCodes.put("POP", "PopCode");
        byteCodes.put("FALSEBRANCH", "FalseBranchCode");
        byteCodes.put("GOTO", "GotoCode");
        byteCodes.put("STORE", "StoreCode");
        byteCodes.put("LOAD", "LoadCode");
        byteCodes.put("LIT", "LitCode");
        byteCodes.put("ARGS", "ArgsCode");
        byteCodes.put("CALL", "CallCode");
        byteCodes.put("RETURN", "ReturnCode");
        byteCodes.put("BOP", "BopCode");
        byteCodes.put("READ", "ReadCode");
        byteCodes.put("WRITE", "WriteCode");
        byteCodes.put("LABEL", "LabelCode");
        byteCodes.put("DUMP", "DumpCode");
    }    
}