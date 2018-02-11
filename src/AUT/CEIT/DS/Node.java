package AUT.CEIT.DS;

/**
 * Created by eisak on 2017-12-29.
 */
public class Node {

    private int nodeNumber;
    private int nodeWeight;
    private Node link;
    private boolean visited;

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {

        return weight;
    }

    private int weight;

    public void setLink(Node link) {
        this.link = link;
    }

    public Node getLink() {

        return link;
    }

    public Node(int name) {
        nodeNumber = name;
        nodeWeight = 0;
        visited = false;
    }

    public int getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public void setNodeWeight(int nodeWeight) {
        this.nodeWeight = nodeWeight;
    }


    public int getNodeWeight() {
        return nodeWeight;
    }
}
