package functionenvironmentrecord;

import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author kevin
 */
public class FunctionEnvironmentRecord {
    private Table table;
    private String FunctionName;
    private int start, end, current;
    public FunctionEnvironmentRecord (){
        table = new Table();
    }
    
    public static void main(String[] args) {
        
        FunctionEnvironmentRecord fctEnvRecord = new FunctionEnvironmentRecord();        
        
        String s = "g", s1 = "a", s2 = "b",s3 = "c";
        
        fctEnvRecord.callBeginScope();
        fctEnvRecord.Dump();
        fctEnvRecord.setFuncNameStartEnd(s, 1, 20);
        fctEnvRecord.Dump();        
        fctEnvRecord.setCurrentLine(5);
        fctEnvRecord.Dump(); 
        fctEnvRecord.enterSymbolValue(s1, 4);
        fctEnvRecord.Dump();
        fctEnvRecord.enterSymbolValue(s2, 2);
        fctEnvRecord.Dump();
        fctEnvRecord.enterSymbolValue(s3, 7);
        fctEnvRecord.Dump();
        fctEnvRecord.enterSymbolValue(s1, 1);
        fctEnvRecord.Dump();
        fctEnvRecord.Pop(2);
        fctEnvRecord.Dump();
        fctEnvRecord.Pop(1);
        fctEnvRecord.Dump();
        
    }
    
    //printing all the function name, start line, end line, and current line
    public void Dump(){
        System.out.print("<(");
        table.printSymbolsValue();
        
        if(FunctionName != null)
            System.out.print(FunctionName + ",");
        else
            System.out.print("-,");
        
        if(start > 0)
            System.out.print(start + ",");
        else
            System.out.print("-,");
        
        if(end > 0)
            System.out.print(end + ",");
        else
            System.out.print("-,");
        
        if(current == 0)              
            System.out.print("-");  
        else
            System.out.print(current);
     
        System.out.println(")>");
    }

    public void callBeginScope(){
        table.beginScope();
    }
    
    public void Pop(int n){
        table.popSymbol(n);
    }
    
    public void enterSymbolValue(String id, int n){
        table.put(id, n);
    }
    
    /* Getters and Setters */
    public String getFunctionName(){
        return FunctionName;
    }
    
    public void setFuncNameStartEnd(String funcName, int startLine, int endLine){
        FunctionName = funcName;
        start = startLine;
        end = endLine;
    }
    public void setFunctionName(String funcName){
         FunctionName = funcName;
    }
    
    public int getStartLine(){
        return start;
    }    
    
    public void setStartLine(int startLine){
        start = startLine;
    }   
    
    public int getEndLine(){
        return end;
    }    
    
    public void setEndLine(int endLine){
        end = endLine;
    }  
    
    public int getCurrentLine() {
        return current;
    }
    
    public void setCurrentLine(int currentLine) {
        current = currentLine;
    }
    
}

   
    /** <pre>
 *  Binder objects group 3 fields
 *  1. a value
 *  2. the next link in the chain of symbols in the current scope
 *  3. the next link of a previous Binder for the same identifier
 *     in a previous scope
 *  </pre>
*/
class Binder {
  private int value;
  private String prevtop;   // prior symbol in same scope
  private Binder tail;      // prior binder for same symbol
                            // restore this when closing scope
  Binder(int v, String p, Binder t) {
	value=v; prevtop=p; tail=t;
  }

  int getValue() { return value; }
  String getPrevtop() { return prevtop; }
  Binder getTail() { return tail; }
}



class Table {

  private java.util.HashMap<String,Binder> symbols = new java.util.HashMap<String,Binder>();
  private String top;    // reference to last symbol added to
                         // current scope; this essentially is the
                         // start of a linked list of symbols in scope
  private Binder marks;  // scope mark; essentially we have a stack of
                         // marks - push for new scope; pop when closing
                         // scope

  public Table(){}


 /**
  * Gets the object associated with the specified symbol in the Table.
  */
  public int get(String key) {
	Binder e = symbols.get(key);
	return e.getValue();
  }

 /**
  * Puts the specified value into the Table, bound to the specified Symbol.<br>
  * Maintain the list of symbols in the current scope (top);<br>
  * Add to list of symbols in prior scope with the same string identifier
  */
  public void put(String key, int value) {
	symbols.put(key, new Binder(value, top, symbols.get(key)));
	top = key;
  }

 /**
  * Remembers the current state of the Table; push new mark on mark stack
  */
  public void beginScope() {
    marks = new Binder(0,top,marks);
    top=null;
  }

 /**
  * Restores the table to what it was at the most recent beginScope
  *	that has not already been ended.
  */
  public void popSymbol(int n) {
	for (int i = 0; i < n; i++) {
	   Binder e = symbols.get(top);
	   if (e.getTail()!=null) symbols.put(top,e.getTail());
	   else symbols.remove(top);
	   top = e.getPrevtop();
	}

  }
  
 /**
  * Print the symbols/values 
  */
  public void printSymbolsValue(){
      java.util.Set<String> SymbolSet = keys(); 
        Iterator<String> it = SymbolSet.iterator();
        Stack<String> temp = new Stack();
        System.out.print("<");  
        
        //put all the Symbol set in the the stack
        while(it.hasNext()){
            String keys = it.next();
            temp.push(keys);
        }
        //print the stack
        while (!temp.isEmpty()){
            String s;
            s = temp.pop();
            if(!temp.isEmpty())
                System.out.print(s+"/"+ get(s)+","); 
            else
                System.out.print(s+"/"+ get(s));
        }

        System.out.print(">,");        
  }
  
  /**
   * @return a set of the Table's symbols.
   */
  public java.util.Set<String> keys() {return symbols.keySet();}
}
