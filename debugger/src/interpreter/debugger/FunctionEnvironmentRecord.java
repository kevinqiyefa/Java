package interpreter.debugger;
/**
* This module contains everything needed for the function enviroment records,
* and its symbol table.
* @author Ramin
*/

/** <pre>
* Binder objects group 3 fields
* 1. a value
* 2. the next link in the chain of symbols in the current scope
* 3. the next link of a previous Binder for the same identifier
* in a previous scope
* </pre>
*/
class Binder {
  private int value;
  private String prevtop; // prior symbol in same scope
  private Binder tail; // prior binder for same symbol
                            // restore this when closing scope
  Binder(int v, String p, Binder t) {
value=v; prevtop=p; tail=t;
  }

  int getValue() { return value; }
  String getPrevtop() { return prevtop; }
  Binder getTail() { return tail; }
}


/** <pre>
* The SymbolTable class is similar to java.util.Dictionary, except that
* each key must be a string and there is a scope mechanism.
*/
class SymbolTable {

  private java.util.HashMap<String,Binder> symbols = new java.util.HashMap<String,Binder>();
  private String top; // reference to last symbol added to
                            // current scope; this essentially is the
                            // start of a linked list of symbols in scope
  private Binder marks; // scope mark; essentially we have a stack of
                            // marks - push for new scope; pop when closing
                            // scope
  private boolean ScopeSet; //

 /**
*
*/
  public SymbolTable(){
      ScopeSet = false;
  }
 /**
* Gets the object associated with the specified symbol in the SymbolTable.
*/
  public int get(String key) {
Binder e = symbols.get(key);
return e.getValue();
  }
 /**
* Puts the specified value into the SymbolTable, bound to the specified String.<br>
* Maintain the list of symbols in the current scope (top);<br>
* Add to list of symbols in prior scope with the same string identifier
*/
  public void put(String key, int value) {
symbols.put(key, new Binder(value, top, symbols.get(key)));
top = key;
  }
 /**
*
*/
  public void removeSymbols(int number) {
       for (int i = 0; i < number; i++) {
Binder e = symbols.get(top);
if (e.getTail()!=null)
               symbols.put(top,e.getTail());
else
               symbols.remove(top);
top = e.getPrevtop();
}
  }
 /**
* Remembers the current state of the SymbolTable; push new mark on mark stack
*/
  public void beginScope() {
      if (!ScopeSet) {
          marks = new Binder(0,top,marks);
          top=null;
          ScopeSet = true;
      }
  }
  /**
* @return a set of the SymbolTable's strings.
*/
  public java.util.Set<String> keys() {return symbols.keySet();}
}


public class FunctionEnvironmentRecord {
    private SymbolTable symtab;
    private String funcname;
    private int startLine, endLine, currentLine;
    
    public FunctionEnvironmentRecord () {
        symtab = new SymbolTable();
    }
    
    public String getFunctionName() {
        return funcname;
    }
    public void setFunctionName(String name) {
        funcname = name;
    }
    public int getStartLine() {
        return startLine;
    }
    public void setStartLine(int line) {
        startLine = line;
    }
    public int getEndLine() {
        return endLine;
    }
    public void setEndLine(int line) {
        endLine = line;
    }
    public int getCurrentLine() {
        return currentLine;
    }
    public void setCurrentLine(int line) {
        currentLine = line;
    }
    public void BeginScope() {
        symtab.beginScope();
    }
    public void enterSymbol(String id, int value) {
        symtab.put(id, value);
    }
    public void removeSymbols(int number) {
        symtab.removeSymbols(number);
    }
    public java.util.Set<String> getSymbols() {
        return symtab.keys();
    }
    public int getSymbolValue(String id) {
        return symtab.get(id);
    }
    /*
public void dump() {
System.out.printf("(<");
java.util.Set<String> keys = getSymbols();
Iterator<String> iterator = keys.iterator();
while (iterator.hasNext()) {
String symbol = iterator.next();
System.out.printf("%s/%d",symbol,symtab.get(symbol));
if (iterator.hasNext())
System.out.printf(",");
}

System.out.printf(">,");
if (funcname != null)
System.out.printf(" %s,",funcname);
else
System.out.printf(" -,");
if (startLine != 0)
System.out.printf(" %d,",startLine);
else
System.out.printf(" -,");
if (endLine != 0)
System.out.printf(" %d,",endLine);
else
System.out.printf(" -,");
if (currentLine != 0)
System.out.printf(" %d",currentLine);
else
System.out.printf(" -");
System.out.printf(")\n");
}
public static void main(String args[]) {
FunctionEnvironmentRecord fctEnvRecord = new FunctionEnvironmentRecord();
fctEnvRecord.BeginScope();
fctEnvRecord.dump();
fctEnvRecord.setFunctionName("g");
fctEnvRecord.setStartLine(1);
fctEnvRecord.setEndLine(20);
fctEnvRecord.dump();
fctEnvRecord.setCurrentLine(5);
fctEnvRecord.dump();
fctEnvRecord.enterSymbol("a",4);
fctEnvRecord.dump();
fctEnvRecord.enterSymbol("b",2);
fctEnvRecord.dump();
fctEnvRecord.enterSymbol("c",7);
fctEnvRecord.dump();
fctEnvRecord.enterSymbol("a",1);
fctEnvRecord.dump();
fctEnvRecord.removeSymbols(2);
fctEnvRecord.dump();
fctEnvRecord.removeSymbols(1);
fctEnvRecord.dump();
}*/
}