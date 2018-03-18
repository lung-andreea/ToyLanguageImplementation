package Model.Statements;

import Model.DataStructures.FileTableInterface;
import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyExpEvalException;
import Model.Exceptions.MyStmtExecException;
import Model.Expressions.Exp;
import Model.PrgState;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    Exp exp_file_id;
    String var_name;

    public ReadFile(Exp exp_file_id, String var_name){
        this.exp_file_id = exp_file_id;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        MyIDictionary<String,Integer> symTable = state.getSymTable();
        FileTableInterface fl = state.getFileTable();
        HeapInterface heap = state.getHeap();
        try
        {
            int key = exp_file_id.eval(symTable,heap);
            BufferedReader buf = (BufferedReader) fl.lookUp(key).getValue();
            String line = buf.readLine();
            int nr;
            if(line==null)
                nr = 0;
            else
                nr = Integer.parseInt(line);
            symTable.add(var_name,nr);
        }
        catch (MyExpEvalException e)
        {
            throw new MyStmtExecException(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public String toString() {
        return "readFile("+exp_file_id.toString()+","+var_name+")";
    }
}
