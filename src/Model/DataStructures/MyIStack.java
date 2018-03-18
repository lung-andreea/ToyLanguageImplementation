package Model.DataStructures;

import java.util.List;
import java.util.Stack;

public interface MyIStack<T> {
    T pop();
    void push(T el);
    boolean isEmpty();
    String toStringForFile();
    abstract List getStack();
}
