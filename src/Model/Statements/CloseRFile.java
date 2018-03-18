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


public class CloseRFile implements IStmt{
    Exp exp_file_id;

    public CloseRFile(Exp exp_file_id){this.exp_file_id=exp_file_id;}

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        MyIDictionary<String,Integer> symTable = state.getSymTable();
        FileTableInterface fl = state.getFileTable();
        HeapInterface heap = state.getHeap();
        try
        {
            int key = exp_file_id.eval(symTable,heap);
            BufferedReader buf = (BufferedReader) fl.lookUp(key).getValue();
            buf.close();
            fl.deleteEntry(key);
        }
        catch (MyExpEvalException e)
        {
            throw new MyStmtExecException(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        state.setFileTable(fl);
        return null;
    }

    @Override
    public String toString() {
        return "closeFile("+exp_file_id.toString()+")";
    }
}
