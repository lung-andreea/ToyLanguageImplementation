package Repository;

import Model.PrgState;

import java.util.List;

public interface RepositoryInterface {
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> l);
    void addNewPrg(PrgState prg);
    void clearLogFile();
    void logPrgStateExec(PrgState p);
    void resetPrgState();
    public PrgState getPrgStateByID(int id);
}
