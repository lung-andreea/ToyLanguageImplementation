package Model;

import Model.DataStructures.*;
import Model.Exceptions.MyStmtExecException;
import Model.Statements.IStmt;
import javafx.stage.Stage;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String,Integer> symTable;
    MyIList<Integer> out;
    FileTableInterface fileTable;
    HeapInterface heap;
    FileTableInterface barrierTable;
    IStmt initialPrg;
    int id;

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String,Integer> symTable, MyIList<Integer> out, FileTableInterface fileTable, HeapInterface heap, FileTableInterface barrierTable, IStmt initialPrg, int id)
    {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.barrierTable=barrierTable;
        this.initialPrg = initialPrg;
        exeStack.push(initialPrg);
        this.id=id;
    }

    public boolean isNotCompleted()
    {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyStmtExecException
    {
        if(exeStack.isEmpty())
            System.out.println("Program finished!\n");
        IStmt crtStmt = exeStack.pop();
        try {
            return crtStmt.execute(this);
        }
        catch (MyStmtExecException e)
        {
            System.out.println(e);
            throw  e;
        }
    }

    @Override
    public String toString() {
        return "\tProgram ---------"+id+"---------\n\nExeStack="+exeStack.toString()+"\nSymTable="+symTable.toString()+"\nOut="+out.toString()+"\nFileTable="+fileTable.toString()+"\nHeap="+heap.toString()+"\nBarrierTable="+barrierTable.toString()+"\n";
    }

    public String toStringForFile(){
        return "\tProgram ---------"+id+"---------\n\nExeStack:\n"+exeStack.toStringForFile()+"\nSymTable:\n"+symTable.toStringForFile()+"\nOut:\n"+out.toStringForFile()+"\nFileTable:\n"+fileTable.toStringForFile()+"\nHeap:\n"+heap.toStringForFile()+"\nBarrierTble:\n"+barrierTable.toStringForFile();
    }

    public int getID(){return id;}

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, Integer> getSymTable() {
        return symTable;
    }

    public MyIList<Integer> getOut() {
        return out;
    }

    public IStmt getInitialPrg() {
        return initialPrg;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(MyIDictionary<String, Integer> symTable) {
        this.symTable = symTable;
    }

    public void setOut(MyIList<Integer> out) {
        this.out = out;
    }

    public FileTableInterface getFileTable() {
        return fileTable;
    }

    public void setFileTable(FileTableInterface fileTable) {
        this.fileTable = fileTable;
    }

    public HeapInterface getHeap() {
        return heap;
    }

    public void setHeap(HeapInterface heap) {
        this.heap = heap;
    }

    public FileTableInterface getBarrierTable() {
        return barrierTable;
    }

    public void setBarrierTable(FileTableInterface barrierTable) {
        this.barrierTable = barrierTable;
    }
}
