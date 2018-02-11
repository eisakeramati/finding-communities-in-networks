package AUT.CEIT.DS;

import java.util.ArrayList;

/**
 * Created by eisak on 2018-01-17.
 */
public class Stack {

    ArrayList<Integer> s;
    int maxSize;

    public Stack() {
        s = new ArrayList<>();
        maxSize=0;
    }

    public void add(int x) {
            maxSize++;
           s.add(x);


    }

    public int delete() {

        return s.remove(s.size()-1);

    }

    public boolean isempty()
    {
        if (s.size()==0) return true;
        else return false;
    }

    public void clear()
    {
        s.clear();
    }


}
