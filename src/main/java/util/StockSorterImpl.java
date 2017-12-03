package util;

import javafx.util.Pair;

public class StockSorterImpl implements StockSorter {
    /**
     * Accepting series of stock info, sorting them ascending according to their comment length.
     * List.sort() or Arrays.sort() are not allowed.
     * You have to write your own algorithms,Bubble sort、quick sort、merge sort、select sort,etc.
     *
     * @param info stock information
     * @return sorted stock
     */
    //默认排序函数,降序
    public Pair<String,Double>[] sort(Pair<String,Double>[] info) {
        //TODO: write your code here
        bubbleSort(info,false);
        return info;
    }

    //true是升序，false是降序
    public Pair<String,Double>[] sort(Pair<String,Double>[] info, boolean order) {
        //TODO: write your code here
        bubbleSort(info,order);
        return info;
    }

    //冒泡排序
    public void bubbleSort(Pair<String,Double>[] stock,boolean order){
        for(int i=stock.length-2;i>=0;i--){
            for(int j=0;j<=i;j++){
                boolean order1;
                if(order) order1=stock[j].getValue()>stock[j+1].getValue();  //升序
                else order1=stock[j].getValue()<stock[j+1].getValue();   //降序
                if(order1){
                    Pair<String,Double> str=stock[j+1];
                    stock[j+1]=stock[j];
                    stock[j]=str;
                }
            }
        }
    }

    public void bubbleSort(Pair<Integer,Double>[] stock){
        for(int i=stock.length-2;i>=0;i--){
            for(int j=0;j<=i;j++){
                boolean order1=stock[j].getValue()<stock[j+1].getValue();   //降序
                if(order1){
                    Pair<Integer,Double> str=stock[j+1];
                    stock[j+1]=stock[j];
                    stock[j]=str;
                }
            }
        }
    }
}

