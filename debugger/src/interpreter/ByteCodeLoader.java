package interpreter;

import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.FileReader;
import java.io.IOException;
import interpreter.bytecode.ByteCode;
//import interpreter.debugger.DebugCodeTable;

/**
* ByteCodeLoader class will load byteCodes from the file into the VirtualMachine.
* @author Yefa Qi
*/
public class ByteCodeLoader {
    private final BufferedReader bcFile;
    //private CodeTable codeTable;
    //private DebugCodeTable debugCodetable;
    private boolean isDebug = false;
    /**
    * Default constructor:
    * 
    * Open and read the file;
    * @param codeFile contains the name of the x.cod file
    * @throws java.io.IOException if problem with file
    */
    public ByteCodeLoader(String codeFile) throws IOException {


        bcFile = new BufferedReader( new FileReader(codeFile) );
    }

    /**
    * loadCodes() will load all codes file; request Program to solve any branch 
    * addresses from symbols to their address in code memory.
    * @return a Program object for processing by the VirtualMachines.
    */
    public Program loadCodes(boolean isDebug) { 
        
        ByteCode bc;
        StringTokenizer token;
        String codeClass; 
        String codeName;
        String line;
        Vector<String> arg;
        Program program = new Program();
        
        this.isDebug = isDebug;
        try{
                while((line = bcFile.readLine()) != null){
                        token = new StringTokenizer(line);
                        arg = new Vector<>();
                        //token the code and get the bytecode name
                        codeName = token.nextToken();
                        
                        codeClass = getClassName(codeName);

                        bc = (ByteCode)(Class.forName("interpreter.bytecode."+codeClass).newInstance());
                        while (token.hasMoreTokens()){ 
                            String tokenName = token.nextToken();
                            arg.add(tokenName);
                        }
                        bc.init(arg);
                        program.addCode(bc);
                        if (false)
                            break;
                }
            } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                System.out.println("Error in ByteCodeLoader");
            }
        //Resolves the addresses
        program.resolveAddresses();
        return program;
    }

    public String getClassName(String codeName) {
        String className;
        if (isDebug){
            className = CodeTable.get(codeName);
            if (codeName.matches("FORMAL|LINE|FUNCTION|POP|LIT|RETURN")) {
                className = "debugByteCodes." + className;
                if (codeName.matches("POP|LIT|RETURN"))
                 className += "Debugger";
            }
            return className;
        }
        else return CodeTable.get(codeName);
       
    }

}