
import javafx.util.Pair;
import segmenter.ChineseSegmenter;
import segmenter.ChineseSegmenterImpl;
import tf_idf.TF_IDF;
import tf_idf.TF_IDFImpl;
import util.FileHandler;
import util.FileHandlerImpl;

import java.util.List;

public class Analysis {
    /**
     *  解析函数，解析导入文件
     *  @param  filePath 文件的路径
     */
    public static void run(String filePath,WindowSearch THIS){
        final FileHandler fileHandler=new FileHandlerImpl();
        final TF_IDF tf_idf=new TF_IDFImpl();
        final ChineseSegmenter chineseSegmenter=new ChineseSegmenterImpl();

        //解析的文件数组
        THIS.stocks=fileHandler.getStockInfoFromFile(filePath);
        //分词后的文件数组
        List<String>[] stockKey=chineseSegmenter.getWordsFromInput(THIS.stocks);
        //求tf_idf值的数组，并排好序,降序
        THIS.keyWordsOrder=new Pair[stockKey.length][];
        for(int i=0;i<stockKey.length;i++) THIS.keyWordsOrder[i]=tf_idf.getResult(stockKey[i]);
    }
}
