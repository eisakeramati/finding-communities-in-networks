package AUT.CEIT.DS;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by eisak on 2017-12-29.
 */
public class AdjacencyList {


    private Node[] list;
    private int p;
    private int fullTime;
    private int graphFromDisc;
    private int algorithmTime;
    private Stack stack;
    private long MemoryUsed;

    private ArrayList<ArrayList<Integer>> cycleHelper;

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    private ArrayList<WeightNode> weightList;

    public Node[] getList() {
        return list;
    }

    public void setList(Node[] list) {
        this.list = list;
    }

    public ArrayList<WeightNode> getWeightList() {
        return weightList;
    }

    public void setWeightList(ArrayList<WeightNode> weightList) {
        this.weightList = weightList;
    }


    public AdjacencyList() {
        cycleHelper = new ArrayList<>();
        weightList = new ArrayList<>();
        stack = new Stack();
    }


    public void manageFile(String path, int flag, int N) {
        Path pathToFile = Paths.get(path);

        long startTime = System.currentTimeMillis();
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            int temp = 0;
            while (line != null) {
                String[] edges = line.split("\t");
                if (Integer.parseInt(edges[0]) > temp || Integer.parseInt(edges[1]) > temp)
                    if (Integer.parseInt(edges[0]) > Integer.parseInt(edges[1]))
                        temp = Integer.parseInt(edges[0]);
                    else temp = Integer.parseInt(edges[1]);
                line = br.readLine();
            }
            list = new Node[temp];
            for (int i = 0; i < temp; i++) {
                Node p = new Node(i + 1);
                list[i] = p;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {
            int i = 0;

            String line = br.readLine();
            while (line != null) {
                String[] edges = line.split("\t");
                addLinkedList(Integer.parseInt(edges[0]), Integer.parseInt(edges[1]));

                cycleSetter(Integer.parseInt(edges[0]), Integer.parseInt(edges[1]));
                //addLinkedList(Integer.parseInt(edges[1]), Integer.parseInt(edges[0]));
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        graphFromDisc = (int) (System.currentTimeMillis() - startTime);


        long startTime2 = System.currentTimeMillis();
        setWeight();
        setMinOut();
        setComparison();
        if (flag == 1)
            quicksort(0, weightList.size() - 1, weightList);
        else if (flag == 2)
            insertionsort(weightList, 0, weightList.size()-1);
        else if (flag == 3)
            mergerSort(weightList, 0, weightList.size() - 1);
        else if (flag == 4)
            Bubblesort(weightList, 0, weightList.size() - 1);
        else if (flag == 5)
            optimumInsertion(0, weightList.size() - 1, weightList, N);
        else if (flag == 6)
            optimumBuble(0, weightList.size() - 1, weightList, N);
        //quick(0, weightList.size() - 1, weightList);
        //       sort(weightList);
//        bubbleSort(weightList, 0, weightList.size()-1);
        // optimumBuble(0, weightList.size()-1, weightList, 100);
        //       mergerSort(weightList, 0, weightList.size()-1);
        // QuickSort(weightList, 0, weightList.size()-1);
        // quicksort(0, weightList.size()-1, weightList);
        // optimumBuble(0, weightList.size() - 1, weightList, 6);
        //BubbleSort(weightList);
        //bubbleSort(weightList, weightList.size(), 0);
        //optimumInsertion(0, weightList.size()-1, weightList, 2000);
        // optimumBuble(0, weightList.size()-1, weightList, 6);
        // Bubblesort(weightList, 0, weightList.size()-1);


        do {
            setFin();
            if (weightList.size() > 0)
                edgeDeleter();
            resetWeights();
            // quick(0, weightList.size() - 1, weightList);
            //mergerSort(weightList, 0, weightList.size()-1);
            // optimumBuble(0, weightList.size()-1, weightList, 100);
            // sort(weightList);
            // bubbleSort(weightList, 0, weightList.size()-1);
            // QuickSort(weightList, 0, weightList.size()-1);
            //quicksort(0, weightList.size()-1, weightList);
            //optimumBuble(0, weightList.size() - 1, weightList, 6);
            //BubbleSort(weightList);
            //bubbleSort(weightList, weightList.size(), 0);
            // optimumInsertion(0, weightList.size()-1, weightList, 2000);
            //optimumBuble(0, weightList.size()-1, weightList, 6);
            //Bubblesort(weightList, 0, weightList.size()-1);

            if (flag == 1)
                quicksort(0, weightList.size() - 1, weightList);
            else if (flag == 2)
                insertionsort(weightList, 0, weightList.size()-1);
            else if (flag == 3)
                mergerSort(weightList, 0, weightList.size() - 1);
            else if (flag == 4)
                Bubblesort(weightList, 0, weightList.size() - 1);
            else if (flag == 5)
                optimumInsertion(0, weightList.size() - 1, weightList, N);
            else if (flag == 6)
                optimumBuble(0, weightList.size() - 1, weightList, N);

            connectionSetter(list[0]);
        } while (connectionChecker());
        algorithmTime = (int) (System.currentTimeMillis() - startTime2);

        writeFile();

        fullTime = (int) (System.currentTimeMillis() - startTime);
        MemoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.println("Full time : " + fullTime);
        System.out.println("loading time for graph to data structure :" + graphFromDisc);
        System.out.println("Algorithm time complition :" + algorithmTime);
        System.out.println("Memory used:"+MemoryUsed);
    }


    public void addLinkedList(int x, int y) {
        Node first = searchList(x);
        Node p = new Node(y);
        if (first.getLink() == null) {
            first.setLink(p);
        } else {
            Node q = first;
            while (q.getLink() != null) {
                q = q.getLink();
            }
            q.setLink(p);

        }
    }


    public void resetWeights() {
        for (int i = 0; i < weightList.size(); i++) {
            //  weightList.get(i).setWeight(dorFinder(list[weightList.get(0).getFrom() - 1], list[weightList.get(0).getTo() - 1]));
            weightList.get(i).setWeight(weightSetter(weightList.get(i).getFrom(), weightList.get(i).getTo()));
            weightList.get(i).setOutMin(findMinOut(weightList.get(i).getTo(), weightList.get(i).getFrom()));
            if (weightList.get(i).getOutMin() == 0)
                weightList.get(i).setComparison(100000);
            else
                weightList.get(i).setComparison((weightList.get(i).getWeight() + 1) / weightList.get(i).getOutMin());
            // weightList.get(i).setComparison((weightList.get(i).getWeight() + 1) /findMinOut(weightList.get(i).getTo(), weightList.get(i).getFrom()) );

        }
    }

    public Node searchList(int x) {

        int i = 0;
        while (list.length > i) {
            if (list[i].getNodeNumber() == x) {
                return list[i];
            }
            i++;
        }

        return null;


//        if (Integer.parseInt(x)<=list.length)
        //             return list[x - 1];
//        else return null;
    }


    public int dorFinder(Node s1, Node s2) {
        int dor = 0;
        Node temp1 = s1;
        Node temp2 = s2;
        if (temp1 != null && temp2 != null) {
            while (temp1.getLink() != null) {
                temp1 = temp1.getLink();
                while (temp2.getLink() != null) {
                    temp2 = temp2.getLink();
                    if (temp2.getNodeNumber() == temp1.getNodeNumber())
                        dor++;

                }
                temp2 = s2;
            }

        }
        return dor;
    }

    public void setWeight() {
        for (int i = 0; i < list.length; i++) {
            Node p = list[i];
            while (p.getLink() != null) {
                p = p.getLink();
                if (addChecker(list[i].getNodeNumber(), p.getNodeNumber())) {
                    WeightNode q = new WeightNode(list[i].getNodeNumber(), p.getNodeNumber());
                    // q.setWeight(dorFinder(list[i], list[p.getNodeNumber() - 1]));
                    q.setWeight(weightSetter(list[i].getNodeNumber(), p.getNodeNumber()));
                    weightList.add(q);
                }
            }
        }


    }


    public Node searchInList(Node first, int number) {
        if (first == null) return null;
        if (first.getNodeNumber() == number) return first;
        return searchInList(first.getLink(), number);
    }

    public int findLenght(Node p) {
        if (p == null) return 0;
        if (p.getLink() == null) {
            return 0;
        }
        return findLenght(p.getLink()) + 1;
    }

    public int findMinOut(int s1, int s2) {
        // if (findLenght(list[s1 - 1]) <= findLenght(list[s2 - 1]))
        if (cycleHelper.get(s1 - 1).size() <= cycleHelper.get(s2 - 1).size())
            return findLenght(list[s1 - 1]) - 1;
        else return findLenght(list[s2 - 1]) - 1;
    }

    public void setMinOut() {
        for (int i = 0; i < weightList.size(); i++) {
            weightList.get(i).setOutMin(findMinOut(weightList.get(i).getTo(), weightList.get(i).getFrom()));
        }
    }

    public void setComparison() {
        for (int i = 0; i < weightList.size(); i++) {
            if (weightList.get(i).getOutMin() == 0)
                weightList.get(i).setComparison(10000);
            else
                weightList.get(i).setComparison((weightList.get(i).getWeight() + 1) / weightList.get(i).getOutMin());
        }
    }

    public boolean addChecker(int a, int b) {
        for (int i = 0; i < weightList.size(); i++) {
            if ((weightList.get(i).getFrom() == b && weightList.get(i).getTo() == a))
                return false;
        }
        return true;
    }


    public void connectionSetter(Node p) {
//        if (p == null) return;
//        p.setVisited(true);
//        while (p.getLink() != null) {
//            p = p.getLink();
//            p.setVisited(true);
//
//            if (list[p.getNodeNumber() - 1].isVisited() == false)
//                connectionSetter(searchList(p.getNodeNumber()));
//
//        }

       // Stack stack = new Stack();
        stack.add(p.getNodeNumber());
        while (!stack.isempty()) {
            int u = stack.delete();
            if (list[u - 1].isVisited() != true) {
                list[u - 1].setVisited(true);
                for (int i = 0; i < cycleHelper.get(u - 1).size(); i++) {
                    if (list[(cycleHelper.get(u - 1).get(i))-1].isVisited() != true)
                        stack.add(cycleHelper.get(u - 1).get(i));
                }
            }
        }
        stack.clear();

    }


    public void setFin() {
        for (int i = 0; i < list.length; i++) {
            Node p = list[i];
            p.setVisited(false);
            while (p.getLink() != null) {
                p = p.getLink();
                p.setVisited(false);
            }
        }
    }


    public boolean connectionChecker() {
//        for (int i = 0; i < list.length; i++) {
//            Node p = list[i];
//            if (p.isVisited() == false)
//                return false;
//            while (p.getLink() != null) {
//                p = p.getLink();
//                if (p.isVisited() == false)
//                    return false;
//            }
//
//
//        }
//        return true;
        for (int i = 0; i < list.length; i++) {
            if (list[i].isVisited() == false)
                return false;
        }
        return true;
    }

    public void edgeDeleter() {

        //System.out.println(weightList.get(0).getFrom()+"  "+weightList.get(0).getTo());
        p++;
        System.out.println("delete");
        System.out.println(weightList.size());
        System.out.println(weightList.get(0).getFrom() + "  " + weightList.get(0).getTo());
        Node p1 = list[weightList.get(0).getFrom() - 1];
        Node p2 = list[weightList.get(0).getTo() - 1];
        cycleHelper.get(weightList.get(0).getFrom() - 1).remove(Integer.valueOf(weightList.get(0).getTo()));

        cycleHelper.get(weightList.get(0).getTo() - 1).remove(Integer.valueOf(weightList.get(0).getFrom()));
        listDeleter(p1, weightList.get(0).getTo());
        listDeleter(p2, weightList.get(0).getFrom());
        // removerFromList(weightList.get(0).getFrom(), weightList.get(0).getTo());
        weightList.remove(0);

    }

    public void listDeleter(Node p, int s) {
        while (p.getLink().getNodeNumber() != s && p.getLink() != null) {
            p = p.getLink();

        }
        if (p.getLink() == null) return;
        p.setLink(p.getLink().getLink());
    }

    public void removerFromList(int s1, int s2) {
        int i = 0;
        while (weightList.get(i).getTo() != s1 && weightList.get(i).getFrom() != s2) {
            i++;
            if (i == weightList.size())
                break;
        }
        if (i < weightList.size())
            weightList.remove(i);

    }


    public void print() {
        for (int i = 0; i < weightList.size(); i++) {
            System.out.println("   ::"+weightList.get(i).getFrom() + "  " + weightList.get(i).getTo() + "  " + weightList.get(i).getComparison());
        }
    }

    public void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter("D:\\samples\\final\\try1.txt");
            for (int i = 0; i < list.length; i++) {
                if (list[i].isVisited() == true) {
                    fileWriter.write(list[i].getNodeNumber() + "   " + "A");
                    fileWriter.append(System.lineSeparator());
                } else {
                    fileWriter.write(list[i].getNodeNumber() + "   " + "B");
                    fileWriter.append(System.lineSeparator());
                }

            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void cycleSetter(int a, int b) {
        if (a > cycleHelper.size()) {
            cycleHelper.add(new ArrayList<>());
            cycleHelper.get(cycleHelper.size() - 1).add(b);
        } else {
            cycleHelper.get(a - 1).add(b);
        }
    }

    public int weightSetter(int a, int b) {
        int result = 0;

        for (int i = 0; i < cycleHelper.get(a - 1).size(); i++) {
            if (cycleHelper.get(b - 1).contains(cycleHelper.get(a - 1).get(i)))
                result++;
        }
        return result;

    }

    public int minfinder(ArrayList<WeightNode> input, int a1, int a2, int a3) {
        if ((input.get(a1).getComparison() < input.get(a2).getComparison() && input.get(a1).getComparison() > input.get(a3).getComparison()) || (input.get(a1).getComparison() < input.get(a3).getComparison() && input.get(a1).getComparison() > input.get(a2).getComparison()))
            return a1;
        if ((input.get(a2).getComparison() < input.get(a1).getComparison() && input.get(a2).getComparison() > input.get(a3).getComparison()) || (input.get(a2).getComparison() < input.get(a3).getComparison() && input.get(a3).getComparison() > input.get(a1).getComparison()))
            return a2;
        else return a3;
    }


/////////////////////////////////////////sorts/////////////////////////////////////////////////////


    public static void merge(ArrayList<WeightNode> arr, int l, int m, int r) {

        int n1 = m - l + 1;
        int n2 = r - m;

        WeightNode L[] = new WeightNode[n1];
        WeightNode R[] = new WeightNode[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr.get(l + i);
        for (int j = 0; j < n2; ++j)
            R[j] = arr.get(m + 1 + j);

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].getComparison() <= R[j].getComparison()) {
                //arr[k] = L[i];
                arr.set(k, L[i]);
                i++;
            } else {
                //arr[k] = R[j];
                arr.set(k, R[j]);
                j++;
            }
            k++;
        }

        while (i < n1) {
            //arr[k] = L[i];
            arr.set(k, L[i]);
            i++;
            k++;
        }

        while (j < n2) {
            //arr[k] = R[j];
            arr.set(k, R[j]);
            j++;
            k++;
        }


    }


    public static void mergerSort(ArrayList<WeightNode> arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;

            mergerSort(arr, l, m);
            mergerSort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }


    public void quicksort(int low, int high, ArrayList<WeightNode> inputArray) {
        int i = low, j = high, mid = (low + high) / 2;
        swap(weightList, minfinder(weightList, low, high, mid), low);
        // WeightNode pivot = inputArray.get((low + high) / 2);
        WeightNode pivot = inputArray.get(low);
        while (i <= j) {
            while (inputArray.get(i).getComparison() < pivot.getComparison()) i++;
            while (inputArray.get(j).getComparison() > pivot.getComparison()) j--;
            if (i <= j) {
                //swap(i, j, inputArray);
                WeightNode temp = inputArray.get(i);
                inputArray.set(i, inputArray.get(j));
                inputArray.set(j, temp);
                i++;
                j--;
            }
        }
        if (low < j)
            quicksort(low, j, inputArray);
        if (i < high)
            quicksort(i, high, inputArray);
    }


    public void insertionSort(ArrayList<WeightNode> p, int i, int n) {
        if (n - i <= 0)
            return;
        insertionSort(p, i, n - 1);
        WeightNode last = p.get(n);
        int temp = n;
        int j = n - 1;
        while (j >= i && p.get(j).getComparison() > last.getComparison()) {
            p.set(j + 1, p.get(j));
            temp = j;
            j--;
        }
        p.set(temp, last);

    }

    public void optimumInsertion(int low, int high, ArrayList<WeightNode> inputArray, int N) {

        if (high - low + 1 >= N) {
            int i = low, j = high;
            int pivot = inputArray.get((low + high) / 2).getComparison();
            while (i <= j) {
                while (inputArray.get(i).getComparison() < pivot) i++;
                while (inputArray.get(j).getComparison() > pivot) j--;
                if (i <= j) {
                    WeightNode temp = inputArray.get(i);
                    inputArray.set(i, inputArray.get(j));
                    inputArray.set(j, temp);
                    i++;
                    j--;
                }
            }
            if (low < j)
                optimumInsertion(low, j, inputArray, N);
            if (i < high)
                optimumInsertion(i, high, inputArray, N);
        } else {
            // insertionSort(inputArray, low, high);
            insertionsort(inputArray, low, high);
        }

    }

    public void optimumBuble(int low, int high, ArrayList<WeightNode> inputArray, int N) {
        if (high - low >= N) {
            int i = low, j = high;
            int pivot = inputArray.get((low + high) / 2).getComparison();
            while (i <= j) {
                while (inputArray.get(i).getComparison() < pivot) i++;
                while (inputArray.get(j).getComparison() > pivot) j--;
                if (i <= j) {
                    WeightNode temp = inputArray.get(i);
                    inputArray.set(i, inputArray.get(j));
                    inputArray.set(j, temp);
                    i++;
                    j--;
                }
            }
            if (low < j)
                optimumBuble(low, j, inputArray, N);
            if (i < high)
                optimumBuble(i, high, inputArray, N);
        } else {
            // bubbleSort(inputArray, high, low);
            Bubblesort(weightList, low, high);
        }

    }

    void insertionsort(ArrayList<WeightNode> p, int start, int end) {
        int n = end + 1;
        for (int i = start + 1; i < n; ++i) {
            WeightNode key = p.get(i);
            int j = i - 1;

            while (j >= start && p.get(j).getComparison() > key.getComparison()) {
                p.set(j + 1, p.get(j));
                j = j - 1;
            }
            p.set(j + 1, key);
        }
    }

    void Bubblesort(ArrayList<WeightNode> arr, int low, int high) {
        int n = high;

        for (int i = low; i < n - 1; i++)
            for (int j = low; j < n - i - 1; j++)
                if (arr.get(j).getComparison() > arr.get(j + 1).getComparison()) {
                    swap(arr, j, j + 1);
                }
    }


    public void bubbleSort(ArrayList<WeightNode> arr, int n, int low) {
        if (n == 1)
            return;

        for (int i = low; i < n - 1; i++)
            if (arr.get(i).getComparison() > arr.get(i + 1).getComparison()) {
//                int temp = arr[i];
//                arr[i] = arr[i+1];
//                arr[i+1] = temp;
                swap(arr, i, i + 1);
            }

        bubbleSort(arr, n - 1, low);
    }


    public void swap(ArrayList<WeightNode> arr, int i, int j) {
        WeightNode t = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, t);
    }

    int partition(ArrayList<WeightNode> arr, int l, int h) {
        WeightNode x = arr.get(h);
        int i = (l - 1);

        for (int j = l; j <= h - 1; j++) {
            if (arr.get(j).getComparison() <= x.getComparison()) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, h);
        return (i + 1);
    }

    void QuickSort(ArrayList<WeightNode> arr, int l, int h) {
        int stack[] = new int[h - l + 1];

        int top = -1;

        stack[++top] = l;
        stack[++top] = h;

        while (top >= 0) {
            h = stack[top--];
            l = stack[top--];

            int p = partition(arr, l, h);

            if (p - 1 > l) {
                stack[++top] = l;
                stack[++top] = p - 1;
            }

            if (p + 1 < h) {
                stack[++top] = p + 1;
                stack[++top] = h;
            }
        }
    }


}
