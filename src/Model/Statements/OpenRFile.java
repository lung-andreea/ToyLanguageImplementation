package Model.Statements;

import Model.DataStructures.FileTableInterface;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyStmtExecException;
import Model.PrgState;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt {
    String var_file_id;
    String filename;

    public OpenRFile(String var_file_id,String filename)
    {
        this.var_file_id = var_file_id;
        this.filename = filename;
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        FileTableInterface fl = state.getFileTable();
        MyIDictionary<String,Integer> symTable = state.getSymTable();
        if(fl.exists(filename))
            throw new MyStmtExecException("A file with the same name is already opened!\n");
        try
        {
            BufferedReader buf = new BufferedReader(new FileReader(filename));
            Pair<String,BufferedReader> p = new Pair<>(filename,buf);
            int key = fl.add(p);
            symTable.add(var_file_id,key);
        }
        catch (IOException e)
        {
            System.out.println("Error opening the file!\n");
        }
        state.setFileTable(fl);
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public String toString() {
        return "openRFile("+var_file_id+",\""+filename+"\")";
    }
}
