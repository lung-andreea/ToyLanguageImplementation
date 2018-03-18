package Model.DataStructures;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    ArrayList<T> list;

    public <T> MyList(){list = new ArrayList<>();}

    @Override
    public void add(T elem) {
        list.add(elem);
    }

    @Override
    public String toString() {
        String s = list.toString();
        return "{"+s.substring(1,s.length()-1)+"}";
    }

    @Override
    public String toStringForFile() {
        String s="";
        for(T elem:list)
            s+=elem.toString()+"\n";
        return s;
    }

    @Override
    public List getContent() {
        return list;
    }
}
