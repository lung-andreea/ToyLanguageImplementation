package Model.DataStructures;

import java.util.List;

public interface MyIList<T> {
    void add(T elem);
    String toStringForFile();
    List getContent();
}
