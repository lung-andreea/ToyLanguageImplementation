package Model.DataStructures;

import Model.Exceptions.MyExpEvalException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;


public interface FileTableInterface {
    int add(Pair p);
    boolean exists(String filename);
    Pair lookUp(Integer key) throws MyExpEvalException;
    void deleteEntry(Integer key);
    String toStringForFile();
    HashMap getContent();
    HashMap getContentForGUI();
    void update(Integer index, Pair p);
}
