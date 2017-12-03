package util;

import javafx.util.Pair;


public interface StockSorter {

    /**
     * Accepting series of stock info, sorting them ascending according to their comment length.
     * List.sort() or Arrays.sort() are not allowed.
     * You have to write your own algorithms,Bubble sort、quick sort、merge sort、select sort,etc.
     *
     * @param info stock information
     * @return sorted stock
     */
    Pair<String,Double>[] sort(Pair<String,Double>[] info);

    /**
     * Accepting series of stock info, sorting them ascending, descending order.
     * @param info stock information
     * @param order true:ascending,false:descending
     * @return sorted stock
     */
    Pair<String,Double>[] sort(Pair<String,Double>[] info, boolean order);

}
