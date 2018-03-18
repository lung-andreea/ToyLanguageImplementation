package Controller;

import Model.DataStructures.HeapInterface;
import Model.Exceptions.MyStmtExecException;
import Model.PrgState;
import Repository.RepositoryInterface;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    RepositoryInterface repo;
    ExecutorService executor;
    List<PrgState> prgList;

    public RepositoryInterface getRepo() {return repo;}

    public Controller(RepositoryInterface repo){this.repo = repo;}

    List<PrgState> removeCompletedPrg(List<PrgState> prgList){
        return prgList.stream().filter(p->p.isNotCompleted()).collect(Collectors.toList());
    }

    Map<Integer,Integer> conservativeGarbageCollector(Collection<Integer> symTableValues, HeapInterface heap)
    {
        return (Map)heap.getContent().entrySet().stream().filter(e->symTableValues.contains(((Map.Entry)e).getKey())).collect(Collectors.toMap(e->((Map.Entry)e).getKey(),e->((Map.Entry)e).getValue()));
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws MyStmtExecException {
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {  return p.oneStep();  }))
                .collect(Collectors.toList());

        ArrayList<String> exceptions = new ArrayList<>();

        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {try{ return future.get(); }catch(Exception e){exceptions.add(e.getMessage());}return null;})
                    .filter(p->p!=null).collect(Collectors.toList());
            prgList.addAll(newPrgList);
            if(exceptions.size()!=0)
                throw new MyStmtExecException(exceptions.get(0));
        }
        catch (InterruptedException ex1)
        {
            System.out.println("Interrupted Exception in invokeAll!\n");
        }
        repo.setPrgList(prgList);
    }

    public void prepareExecutionOfNewPrg()
    {
        repo.clearLogFile();
        executor = Executors.newFixedThreadPool(2);
        prgList=removeCompletedPrg(repo.getPrgList());
    }

    public void executeOneStep() throws Model.Exceptions.EmptyStackException,MyStmtExecException
    {
        if(prgList.size()==0)
        {
            executor.shutdownNow();
            repo.getPrgList().get(0).getFileTable().getContent().values().stream().map(e->((Pair)e).getValue()).forEach(f->{try{((BufferedReader)f).close();}catch (IOException e){System.out.println(e);}});
            throw new Model.Exceptions.EmptyStackException("Program finished!");
            //            repo.resetPrgState();
        }

        oneStepForAllPrg(prgList);

        Collection<Integer> symTableValues = new ArrayList<>();
        prgList.forEach(prg->symTableValues.addAll(prg.getSymTable().getContent().values()));
        repo.getPrgList().get(0).getHeap().setContent(conservativeGarbageCollector(symTableValues,repo.getPrgList().get(0).getHeap()));

        prgList.forEach(prg->repo.logPrgStateExec(prg));
        prgList.forEach(prg->System.out.println(prg));

        prgList = removeCompletedPrg(repo.getPrgList());
    }
}
