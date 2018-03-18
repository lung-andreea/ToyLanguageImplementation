package Model.Statements;

import Model.Exceptions.MyStmtExecException;
import Model.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws MyStmtExecException;
}
