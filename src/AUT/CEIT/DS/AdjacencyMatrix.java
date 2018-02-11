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
public class AdjacencyMatrix {

    private int fullTime;
    private int graphFromDisc;
    private int algorithmTime;
    private long MemoryUsed;

    private ArrayList<SparseMCell> list;

    private Stack stack;

    private ArrayList<ArrayList<Integer>> cycleHelper;

    private int nodesNum;

    private ArrayList<Integer> childs;

    private ArrayList<Integer> degree;

    public ArrayList<Integer> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<Integer> childs) {
        this.childs = childs;
    }

    public ArrayList<Integer> getDegree() {
        return degree;
    }

    public void setDegree(ArrayList<Integer> degree) {
        this.degree = degree;
    }

    public int getNodesNum() {
        return nodesNum;
    }

    public void setNodesNum(int nodesNum) {
        this.nodesNum = nodesNum;
    }

    public ArrayList<Integer> getVisitList() {
        return visitList;
    }

    public void setVisitList(ArrayList<Integer> visitList) {
        this.visitList = visitList;
    }

    private ArrayList<Integer> visitList;

    public ArrayList<WeightNode> getWeightList() {
        return weightList;
    }

    public void setWeightList(ArrayList<WeightNode> weightList) {
        this.weightList = weightList;
    }

    private ArrayList<WeightNode> weightList;

    public ArrayList<SparseMCell> getList() {
        return list;
    }

    public void setList(ArrayList<SparseMCell> list) {
        this.list = list;
    }

    public AdjacencyMatrix() {
        cycleHelper = new ArrayList<>();
        childs = new ArrayList<>();
        degree = new ArrayList<>();
        visitList = new ArrayList<>();
        list = new ArrayList<>();
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
                SparseMCell sparseMCell = new SparseMCell(Integer.parseInt(edges[0]), Integer.parseInt(edges[1]));

                list.add(sparseMCell);
                cycleSetter(Integer.parseInt(edges[0]), Integer.parseInt(edges[1]));
                if (Integer.parseInt(edges[0]) - 1 < degree.size())
                    degree.set(Integer.parseInt(edges[0]) - 1, degree.get(Integer.parseInt(edges[0]) - 1) + 1);
                else degree.add(1);
                if (!visitList.contains(Integer.parseInt(edges[0]))) {
                    visitList.add(Integer.parseInt(edges[0]));
                }
                line = br.readLine();
            }
            nodesNum = visitList.size();
            visitList.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
        graphFromDisc = (int) (System.currentTimeMillis() - startTime);


        long startTime2 = System.currentTimeMillis();
        allweight();
        allOutMin();
        allComparison();


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


//        setVisit(list.get(0));
//        if (visitList.size() == nodesNum)
//            System.out.println("YES");
        // bubbleSort(weightList, 0, weightList.size());
        quicksort(0, weightList.size() - 1, weightList);
       // print();
        do {
            visitList.clear();
            if (weightList.size() > 0)
                deleter();
            update();
            // bubbleSort(weightList, 0, weightList.size());
            //  quicksort(0, weightList.size()-1, weightList);

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

            setVisit(list.get(0));
            // System.out.println(visitList.size()+"  "+nodesNum+"   yy");
        } while (visitList.size() == nodesNum);
        algorithmTime = (int) (System.currentTimeMillis() - startTime2);

        writeFile();

        fullTime = (int) (System.currentTimeMillis() - startTime);
        MemoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.println("Full time : "+fullTime);
        System.out.println("loading time for graph to data structure :"+graphFromDisc);
        System.out.println("Algorithm time complition :"+algorithmTime);
        System.out.println("Memory used:"+MemoryUsed);

    }

    public int weightSetter(int a, int b) {
//          System.out.println(a + "   " + b);
//        int i = 0;
//        int j = 0;
//        int result = 0;
//        i = placeFinder(a);
//        j = placeFinder(b);
//        ArrayList<Integer> a1, a2;
//        a1 = new ArrayList<>();
//        a2 = new ArrayList<>();
//        while (i < list.size() && list.get(i).getRow() == a) {
//            a1.add(list.get(i).getColoumn());
//            i++;
//        }
//        while (j < list.size() && list.get(j).getRow() == b) {
//            a2.add(list.get(j).getColoumn());
//            j++;
//        }
//
//        for (int k = 0; k < a1.size(); k++) {
//            if (a2.contains(a1.get(k)))
//                result++;
//        }
//        return result;
        int result = 0;

        for (int i = 0; i < cycleHelper.get(a - 1).size(); i++) {
            if (cycleHelper.get(b - 1).contains(cycleHelper.get(a - 1).get(i)))
                result++;
        }
        return result;

    }

    public int placeFinder(int given) {
        int i = 0;
        while (list.get(i).getRow() != given)
            i++;
        return i;

    }


    public boolean checker(int a, int temp) {
        if (a >= list.size()) return false;
        if (list.get(a).getRow() == temp)
            return true;
        return false;
    }

    public void allweight() {
        for (int i = 0; i < list.size(); i++) {
            //list.get(i).setWeight(weightSetter(list.get(i).getRow(), list.get(i).getColoumn()));
            if (addChecker(list.get(i).getRow(), list.get(i).getColoumn())) {
                WeightNode q = new WeightNode(list.get(i).getRow(), list.get(i).getColoumn());
                q.setWeight(weightSetter(list.get(i).getRow(), list.get(i).getColoumn()));
                weightList.add(q);
            }
        }
    }

    public boolean addChecker(int a, int b) {
        for (int i = 0; i < weightList.size(); i++) {
            if ((weightList.get(i).getFrom() == b && weightList.get(i).getTo() == a))
                return false;
        }
        return true;
    }

    public void print() {
        for (int i = 0; i < weightList.size(); i++) {
            System.out.println(weightList.get(i).getFrom() + "  " + weightList.get(i).getTo() + "  " + weightList.get(i).getComparison() + "   " + weightList.get(i).getWeight() + "  " + weightList.get(i).getOutMin());
        }
    }

    public int setOutMin(int a, int b) {

//        int temp1 = degree.get(a - 1);
//        int temp2 = degree.get(b - 1);
//        if (temp1 > temp2)
//            return temp2 - 1;
//        else return temp1 - 1;

        int temp1 = cycleHelper.get(a - 1).size();
        int temp2 = cycleHelper.get(b - 1).size();
        if (temp1 > temp2)
            return temp2 - 1;
        else return temp1 - 1;

    }

    public void allOutMin() {
        for (int i = 0; i < weightList.size(); i++) {
            // System.out.println(weightList.get(i).getFrom()+"  "+weightList.get(i).getTo());
            weightList.get(i).setOutMin(setOutMin(weightList.get(i).getFrom(), weightList.get(i).getTo()));
        }
    }

    public void allComparison() {
        for (int i = 0; i < weightList.size(); i++) {
            if (weightList.get(i).getOutMin() == 0)
                weightList.get(i).setComparison(100000);
            else
                weightList.get(i).setComparison((weightList.get(i).getWeight() + 1) / weightList.get(i).getOutMin());
        }
    }

    public void setVisit(SparseMCell sm) {


//
//        visitList.add(sm.getRow());
//        int comp=sm.getRow();
//        int t = placeFinder(sm.getRow());
//        SparseMCell temp = list.get(placeFinder(sm.getRow()));
//        while (temp.getRow()==comp)
//        {
//            int inside = temp.getColoumn();
//            if (!visitList.contains(inside))
//                setVisit(list.get(placeFinder(inside)));
//            if (t==list.size()-1)
//                break;
//            t++;
//            temp=list.get(t);
//        }

        // Stack stack = new Stack();

        stack.add(sm.getRow());
        while (!stack.isempty()) {
            int u = stack.delete();
            if (!visitList.contains(u)) {
                visitList.add(u);
                for (int i = 0; i < cycleHelper.get(u - 1).size(); i++) {
                    if (!visitList.contains(cycleHelper.get(u - 1).get(i)))
                        stack.add(cycleHelper.get(u - 1).get(i));
                }
            }
        }

        stack.clear();

    }

    public ArrayList<ArrayList<Integer>> getCycleHelper() {
        return cycleHelper;
    }

    public void setCycleHelper(ArrayList<ArrayList<Integer>> cycleHelper) {
        this.cycleHelper = cycleHelper;
    }

    public void deleter() {

        System.out.println("PPP   " + weightList.get(0).getFrom() + "  " + weightList.get(0).getTo());
        int temp1 = weightList.get(0).getFrom();
        int temp2 = weightList.get(0).getTo();
        delete(temp1, temp2);
        delete(temp2, temp1);
        weightList.remove(0);
        cycleHelper.get(temp1 - 1).remove(Integer.valueOf(temp2));

        cycleHelper.get(temp2 - 1).remove(Integer.valueOf(temp1));
        degree.set(temp1 - 1, degree.get(temp1 - 1) - 1);
        degree.set(temp2 - 1, degree.get(temp2 - 1) - 1);


    }

    public void delete(int a, int b) {
        int temp = placeFinder(a);
        while (list.get(temp).getRow() == a && list.get(temp).getColoumn() != b)
            temp++;
        if (list.get(temp).getRow() == a && list.get(temp).getColoumn() == b)
            list.remove(temp);
    }


    public void update() {
        for (int i = 0; i < weightList.size(); i++) {

            weightList.get(i).setWeight(weightSetter(weightList.get(i).getFrom(), weightList.get(i).getTo()));
            weightList.get(i).setOutMin(setOutMin(weightList.get(i).getFrom(), weightList.get(i).getTo()));
            if (weightList.get(i).getOutMin() == 0)
                weightList.get(i).setComparison(100000);
            else
                weightList.get(i).setComparison((weightList.get(i).getWeight() + 1) / weightList.get(i).getOutMin());

        }
    }

    void printt() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getRow() + "   " + list.get(i).getColoumn());
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


    public void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter("D:\\samples\\final\\try1Mat.txt");
            for (int i = 0; i < nodesNum; i++) {
                if (visitList.contains(i + 1)) {
                    fileWriter.write((i + 1) + "   " + "A");
                    fileWriter.append(System.lineSeparator());
                } else {
                    fileWriter.write((i + 1) + "   " + "B");
                    fileWriter.append(System.lineSeparator());
                }

            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int minfinder(ArrayList<WeightNode> input, int a1, int a2, int a3) {
        if ((input.get(a1).getComparison() < input.get(a2).getComparison() && input.get(a1).getComparison() > input.get(a3).getComparison()) || (input.get(a1).getComparison() < input.get(a3).getComparison() && input.get(a1).getComparison() > input.get(a2).getComparison()))
            return a1;
        if ((input.get(a2).getComparison() < input.get(a1).getComparison() && input.get(a2).getComparison() > input.get(a3).getComparison()) || (input.get(a2).getComparison() < input.get(a3).getComparison() && input.get(a3).getComparison() > input.get(a1).getComparison()))
            return a2;
        else return a3;
    }

////////////////////////////////////////////sorts////////////////////////////////////////////////////////////////////////


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
            insertionSort(inputArray, low, high);
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
            bubbleSort(inputArray, high, low);
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

