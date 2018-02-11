package AUT.CEIT.DS;

/**
 * Created by eisak on 2017-12-30.
 */
public class SparseMCell {
    private int row;
    private int coloumn;
    private int weight;

    public SparseMCell(int s1, int s2) {
        row = s1;
        coloumn = s2;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColoumn() {
        return coloumn;
    }

    public void setColoumn(int coloumn) {
        this.coloumn = coloumn;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
