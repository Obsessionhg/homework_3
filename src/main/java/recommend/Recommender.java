package recommend;

import javafx.util.Pair;
import vo.StockInfo;

public interface Recommender {

    /**
     * 该函数用于计算相似度矩阵，返回一个记录相似度二位数组（矩阵）
     * @param stocks stock info
     * @return similarity matrix
     */
    double cosSimiliarity(Pair<String,Double>[] pair1, Pair<String,Double>[] pair2);

}
