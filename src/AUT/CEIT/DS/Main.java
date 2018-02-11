package AUT.CEIT.DS;

import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here

        // AdjacencyList adjacencyList = new AdjacencyList();
        // adjacencyList.manageFile("D:\\samples\\test1.txt");
//        System.out.println(adjacencyList.getP());
//        System.out.println(adjacencyList.getWeightList().get(0).getTo());
//        System.out.println(adjacencyList.getWeightList().get(0).getFrom());
//        System.out.println(adjacencyList.getWeightList().get(0).getOutMin());
//        System.out.println(adjacencyList.getWeightList().get(0).getWeight());
//        System.out.println(adjacencyList.getWeightList().get(2).getComparison());
//        System.out.println(adjacencyList.getWeightList().get(0).getComparison());
//        System.out.println(adjacencyList.getWeightList().get(3).getComparison());
//        System.out.println(adjacencyList.getWeightList().get(2).getWeight());
//        System.out.println(adjacencyList.getWeightList().get(2).getFrom());
//        System.out.println(adjacencyList.getWeightList().get(2).getTo());
//        System.out.println("   "+adjacencyList.getWeightList().get(0).getFrom());
//        System.out.println("   "+adjacencyList.getWeightList().get(0).getTo());
//        System.out.println(adjacencyList.getWeightList().get(0).getWeight());
//        System.out.println(adjacencyList.dorFinder(adjacencyList.getWeightList().get(0).getFrom(), adjacencyList.getWeightList().get(0).getTo()));

           // AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix();
             // adjacencyMatrix.manageFile("D:\\samples\\test1.txt");

//           WeightNode a = new WeightNode(2,1);
//           a.setComparison(5);
//        WeightNode b= new WeightNode(2,2);
//        b.setComparison(3);
//        WeightNode c = new WeightNode(2,9);
//        c.setComparison(8);
//        WeightNode d = new WeightNode(2,3);
//        d.setComparison(6);
//        ArrayList<WeightNode> t= new ArrayList<>();
//        t.add(a);
//        t.add(b);
//        t.add(c);
//        t.add(d);
//        for (int i = 0; i < t.size(); i++) {
//            System.out.println(t.get(i).getComparison());
//
//        }
//        adjacencyMatrix.insertionsort(t, 0, t.size()-1);
//        for (int i = 0; i < t.size(); i++) {
//            System.out.println(t.get(i).getComparison());}

        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        String path = scanner.nextLine();
        if (string.contains("RUN")) {
            if (string.contains("LinkedList")) {
                AdjacencyList adjacencyList = new AdjacencyList();
                if (string.contains("Quick")) {
                    adjacencyList.manageFile(path, 1, 0);
                } else if (string.contains("Insertion")) {
                    adjacencyList.manageFile(path, 2, 0);
                } else if (string.contains("Merge")) {
                    adjacencyList.manageFile(path, 3, 0);
                } else if (string.contains("Bubble")) {
                    adjacencyList.manageFile(path, 4, 0);
                } else if (string.contains("Optimum insertion")) {
                    System.out.println("Enter the N:");
                    int N = scanner.nextInt();
                    adjacencyList.manageFile(path, 5, N);
                } else if (string.contains("Optimum bubble")) {
                    System.out.println("Enter the N:");
                    int N = scanner.nextInt();
                    adjacencyList.manageFile(path, 6, N);
                }
            } else if (string.contains("Matrix")) {
                AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix();
                if (string.contains("Quick")) {
                    adjacencyMatrix.manageFile(path, 1, 0);
                } else if (string.contains("Insertion")) {
                    adjacencyMatrix.manageFile(path, 2, 0);
                } else if (string.contains("Merge")) {
                    adjacencyMatrix.manageFile(path, 3, 0);
                } else if (string.contains("Bubble")) {
                    adjacencyMatrix.manageFile(path, 4, 0);
                } else if (string.contains("Optimum insertion")) {
                    System.out.println("Enter the N:");
                    int N = scanner.nextInt();
                    adjacencyMatrix.manageFile(path, 5, N);
                } else if (string.contains("Optimum bubble")) {
                    System.out.println("Enter the N:");
                    int N = scanner.nextInt();
                    adjacencyMatrix.manageFile(path, 6, N);
                }
            }
        }


    }


    // adjacencyMatrix.Allweight();
    //adjacencyMatrix.print();
    // System.out.println(adjacencyMatrix.getList().get(2).getWeight()+"  "+adjacencyMatrix.getList().get(2).getRow()+"   "+adjacencyMatrix.getList().get(2).getColoumn());


}
