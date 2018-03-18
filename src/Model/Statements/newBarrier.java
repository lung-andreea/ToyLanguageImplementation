package Model.Statements;

import Model.DataStructures.FileTableInterface;
import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyExpEvalException;
import Model.Exceptions.MyStmtExecException;
import Model.Expressions.Exp;
import Model.PrgState;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class newBarrier implements IStmt {
    String var;
    Exp exp;

    public newBarrier(String var,Exp exp){this.var=var;this.exp=exp;}

    @Override
    public String toString() {
        return "newBarrier("+var+","+exp.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        MyIDictionary<String,Integer> symTable = state.getSymTable();
        HeapInterface heap = state.getHeap();
        FileTableInterface br = state.getBarrierTable();
        try
        {
            java.util.List<Integer> list = new ArrayList<>();
            int key = br.add(new Pair<>(exp.eval(symTable,heap),list));
            symTable.add(var,key);
        }
        catch (MyExpEvalException e)
        {
            throw new MyStmtExecException(e.getMessage());
        }
        state.setSymTable(symTable);
        state.setBarrierTable(br);
        return null;
    }
}
