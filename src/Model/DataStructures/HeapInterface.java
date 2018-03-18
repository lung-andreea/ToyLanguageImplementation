package Model.DataStructures;

import Model.Exceptions.MyExpEvalException;

import java.util.Map;

public interface HeapInterface {
    int add(Integer p);
    void update(Integer key, Integer val) throws MyExpEvalException;
    int lookUp(int key) throws MyExpEvalException;
    Map getContent();
    void setContent(Map<Integer, Integer> m);
    String toStringForFile();
}
