package AUT.CEIT.DS;

/**
 * Created by eisak on 2017-12-30.
 */
public class WeightNode {
    private int from;
    private int to;
    private int weight;
    private int outMin;
    private int comparison;

    public int getComparison() {
        return comparison;
    }

    public void setComparison(int comparison) {
        this.comparison = comparison;
    }

    public int getOutMin() {
        return outMin;
    }

    public void setOutMin(int outMin) {
        this.outMin = outMin;
    }

    public WeightNode(int s1, int s2) {
        from = s1;
        to = s2;
        weight = 0;
    }


    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
