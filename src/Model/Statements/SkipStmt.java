package Model.Statements;

import Model.Exceptions.MyStmtExecException;
import Model.PrgState;

public class SkipStmt implements IStmt {
    public SkipStmt(){}

    @Override
    public String toString() {
        return "SKIP";
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        return null;
    }
}
