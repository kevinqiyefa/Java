package interpreter;

import interpreter.debugger.DebuggerVM;
import interpreter.debugger.SourceLine;
import interpreter.debugger.SourceCodeLoader;
import interpreter.debugger.ui.DebugUserInterFaces;
import java.io.IOException;
import java.util.Vector;
/**
* <pre>
*
*
*
* Interpreter class runs the interpreter:
* 1. Perform all initializations
* 2. Load the bytecodes from file
* 3. Run the virtual machine
*
*
*
* </pre>
*/
public class Interpreter {

        private ByteCodeLoader bcl;
        private boolean DebugMode = false;
        private SourceCodeLoader scl;
        private Vector<SourceLine> xCodes;
        private String debugSourceFile ="";
        private String debugCodeFile = "";

        public Interpreter(String args[], boolean debugMode) {
            try{
                CodeTable.init();
                if(debugMode){
                           DebugMode = true; 
                           debugSourceFile = args[1].concat(".x");
                           debugCodeFile = args[1].concat(".x.cod");  
                           scl = new SourceCodeLoader(debugSourceFile);
                           xCodes = scl.loadSource();
                           bcl = new ByteCodeLoader(debugCodeFile);
                           System.out.println("******Source: " + debugSourceFile + " *******");
                }
                else{               	
                           String codeFile = args[0];
                           bcl = new ByteCodeLoader(codeFile);
                }
             } catch (IOException e) {
                           System.out.println("**** " + e);
             }
        }

        void run() {
                 if(DebugMode){
        
                        Program program = bcl.loadCodes(true);
                        DebuggerVM dvm = new DebuggerVM(program,xCodes);
                    
                        DebugUserInterFaces ui = new DebugUserInterFaces(dvm);
                        ui.debug();
                } else {

                        Program program = bcl.loadCodes(false);
                        VirtualMachine vm = new VirtualMachine(program);
                        vm.executeProgram();
                }
        }
        
	public static void main(String args[]) {
                        boolean debugMode = false;
                        if (args.length == 0) {
                                System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
                                System.exit(1);
                        } 
                        if (args[0].equalsIgnoreCase("-d")){
                            if(args.length != 2){
                                System.out.println("***Incorrect usage, try: java interpreter.Interpreter -d <file>");
                                System.exit(1);
                            }
                            debugMode = true;
                        }

                        (new Interpreter(args, debugMode )).run();
	}
}