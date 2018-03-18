package Repository;

import Model.DataStructures.*;
import Model.PrgState;
import Model.Statements.IStmt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements RepositoryInterface{
    private List<PrgState> elems;
    private String logFilePath;

    public Repository(String logFilePath){
        elems = new ArrayList<>();
        this.logFilePath=logFilePath;
    }

    @Override
    public List<PrgState> getPrgList() {
        return elems;
    }

    @Override
    public void setPrgList(List<PrgState> l) {
        elems=l;
    }

    @Override
    public void addNewPrg(PrgState prg) {
        elems.add(prg);
    }

    @Override
    public void clearLogFile() {
        try {
        PrintWriter pw = new PrintWriter(new File(logFilePath));
        pw.close();
        }
        catch(IOException e)
        {System.out.println(e.getMessage());}
    }

    @Override
    public void logPrgStateExec(PrgState p) {
        try
        {
        PrintWriter logFile=new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
//
        logFile.println(p.toStringForFile());
        //logFile.println("-------------------");


        logFile.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void resetPrgState() {
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String,Integer> symTable = new MyDictionary<>();
        MyIList<Integer> out = new MyList<>();
        FileTable fileTable = new FileTable();
        HeapInterface heap = new Heap();
        FileTableInterface br = new BarrierTable();
        IStmt prg = elems.get(0).getInitialPrg();

        PrgState prgState = new PrgState(exeStack,symTable,out,fileTable,heap,br,prg,elems.get(0).getID());
        elems.remove(0);
        elems.add(prgState);
        }

    @Override
    public PrgState getPrgStateByID(int id) {
        for(PrgState prg : elems)
            if(prg.getID()==id)
                return prg;
        return null;
    }
}

