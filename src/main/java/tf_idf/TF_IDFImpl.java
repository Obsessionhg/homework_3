package tf_idf;

import javafx.util.Pair;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import util.StockSorter;
import util.StockSorterImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TF_IDFImpl implements TF_IDF {
    /**
     * this func you need to calculate words frequency , and sort by frequency.
     * you maybe need to use the sorter written by yourself in example 1
     *
     * @param words the word after segment
     * @return a sorted words
     * @see StockSorter
     */
    @Override
    public Pair<String, Double>[] getResult(List<String> words) {
        //TODO: write your code here
        String filePath="data.txt";  //语料库路径            ////////////////////////语料库
        List<String> keyWords=new ArrayList<>();   //关键字组
        List<Double> tf,idf;      //tf，idf值组
        keyWords.add(words.get(0));
        restart:
        for(int i=1;i<words.size();i++)   {            //计算不重复的关键字组
            for(int j=0;j<keyWords.size();j++)  if(words.get(i)==keyWords.get(j)) continue restart;
            keyWords.add(words.get(i));
        }
        tf=tf_Value(words,keyWords);             //算关键字组对应的tf值
        long a=System.currentTimeMillis();
        idf=idf_Value(filePath,keyWords);         //计算关键字组对应的idf值
        long b=System.currentTimeMillis();
        Pair<String, Double>[] pair=new Pair[keyWords.size()];   //接收词与tf-idf值
        for(int i=0;i<keyWords.size();i++) pair[i]=new Pair(keyWords.get(i),(tf.get(i)*idf.get(i)));
        StockSorter s=new StockSorterImpl();          //调用排序算法
        pair=s.sort(pair);
        return pair;
    }

    /**
     * 计算tf值
     * @param words  给定的词汇数组
     * @param keyWords  words中的无重复关键字数组
     * @return   返回与可以keyWords相对应的tf值组
     */
    public List<Double> tf_Value(List<String> words,List<String> keyWords){
        List<Double> tf=new ArrayList<>();
        for(int i=0;i<keyWords.size();i++){
            int t=0;
            for(int j=0;j<words.size();j++)  if(keyWords.get(i)==words.get(j)) t++;
            tf.add((double)t/words.size());
        }
        return tf;
    }

    /**
     *  计算idf值
     * @param filePath  语料库路径名
     * @param keyWords  无重复的关键字词组
     * @return   返回与keyWords对应的idf值组
     */
    public List<Double> idf_Value(String filePath,List<String> keyWords){       //计算idf值
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("t");add("s");add("f");add("v");
            add("a");add("b");add("z");add("r");add("m");
            add("x");
        }}; // 过滤词性
        String line;
        List<String> corpus=new ArrayList<>();
        List<Double> idf=new ArrayList<>();
        List<Term> terms;
        try{
            File file=new File(filePath);
            InputStreamReader inStream=new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader bfile=new BufferedReader(inStream);
            while((line=bfile.readLine())!=null){
                Result result= ToAnalysis.parse(line);
                terms=result.getTerms();
                for(int j=0;j<terms.size();j++)
                    if(expectedNature.contains(terms.get(j).getNatureStr())) corpus.add(terms.get(j).getName());
            }
            bfile.close();
            inStream.close();
        }catch(Exception e){
            System.out.println(e);
        }
        for(int i=0;i<keyWords.size();i++){
            double id=0;
            for(int j=0;j<corpus.size();j++) if(keyWords.get(i)==corpus.get(j)) id++;
            idf.add(Math.log10(corpus.size()/(id+1)));  //计算逆文档频率
        }
        return idf;
    }
}