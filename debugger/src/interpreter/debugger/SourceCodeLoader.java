package interpreter.debugger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
* SourceCodeLoader loads the source file into the debugger as
 a vector of LineEntries.
* @author Ramin
*/
public class SourceCodeLoader {
    private BufferedReader sourceFile;
    /**
* Construct a new SourceLoader
* @param source Name of the source file to load
* @throws IOException - Thrown if the file does not exist.
*/
    public SourceCodeLoader(String source) throws IOException {
        sourceFile = new BufferedReader(new FileReader(source));
    }
    /**
* Loads the source file.
* @return a Vector of SourceLine objects, each containing
 a line of the source file.
*/
    public Vector<SourceLine> loadSource() {
        Vector<SourceLine> srcEntries = new Vector<SourceLine>();
        try {
            while(sourceFile.ready()) {
                String line = sourceFile.readLine();
                SourceLine entry = new SourceLine(line);
                srcEntries.addElement(entry);
            }
        } catch (IOException e) {
            System.out.println("Error in reading source file.");
        }
        return srcEntries;
    }
}