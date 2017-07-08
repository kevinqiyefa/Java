package interpreter;

import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.FileReader;
import java.io.IOException;
import interpreter.bytecode.ByteCode;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* ByteCodeLoader class will load byteCodes from the file into the VirtualMachine.
* @author Yefa Qi
*/
public class ByteCodeLoader {
    private final BufferedReader bcFile;

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
    public Program loadCodes() { 
        
        ByteCode bc;
        StringTokenizer token;
        String codeClass; 
        String codeName;
        String line;
        Vector<String> arg;
        Program program = new Program();
        
        try {
            do{
                try{
                    line = bcFile.readLine();
                    arg = new Vector<>();
                    token = new StringTokenizer(line);
                    //token the code and get the bytecode name
                    codeName = token.nextToken();
                    codeClass = CodeTable.get(codeName);
                    bc = (ByteCode)(Class.forName("interpreter.bytecode."+codeClass).newInstance());
                    while (token.hasMoreTokens()){
                        String tokenName = token.nextToken();
                        arg.add(tokenName);
                    }
                    bc.init(arg);
                    program.addCode(bc);

                } catch (Exception e) {
                    // System.out.println("Error in ByteCodeLoader");
                }
            }while (true && bcFile.ready());
        } catch (IOException ex) {
            Logger.getLogger(ByteCodeLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Resolves the addresses
        program.resolveAddresses();
        return program;
    }


}