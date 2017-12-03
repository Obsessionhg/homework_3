package segmenter;


import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import vo.StockInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChineseSegmenterImpl implements ChineseSegmenter {

    /**
     * this func will get chinese word from a list of stocks. You need analysis stocks' answer and get answer word.
     * And implement this interface in the class : ChineseSegmenterImpl
     * Example: 我今天特别开心 result : 我 今天 特别 开心
     *
     * @param stocks stocks info
     * @return chinese word
     * @see ChineseSegmenterImpl
     */
    @Override
    public List<String>[] getWordsFromInput(StockInfo[] stocks) {
        //TODO: write your code here
        @SuppressWarnings("serial")
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("t");add("s");add("f");add("v");
            add("a");add("b");add("z");add("r");add("m");
            add("x");
        }}; // 过滤词性
        List<String>[] str=new ArrayList[stocks.length];
        for(int i=0;i<stocks.length;i++) str[i]=new ArrayList<>();
        List<Term> terms; //获取terms
        for (int i=0;i<stocks.length;i++) {

            //分词结果的一个封装，title分词
            Result result = ToAnalysis.parse(stocks[i].getTitle());
            terms=result.getTerms();  //获取terms
            for(int j=0;j<terms.size();j++)
                if(expectedNature.contains(terms.get(j).getNatureStr())) str[i].add(terms.get(j).getName());

            //分词结果的一个封装，content分词
            result = ToAnalysis.parse(stocks[i].getContent());
            terms=result.getTerms();  //获取terms
            for(int j=0;j<terms.size();j++)
                if(expectedNature.contains(terms.get(j).getNatureStr())) str[i].add(terms.get(j).getName());

            //分词结果的一个封装，answer分词
            result = ToAnalysis.parse(stocks[i].getAnswer());
            terms=result.getTerms();  //获取terms
            for(int j=0;j<terms.size();j++)
                if(expectedNature.contains(terms.get(j).getNatureStr())) str[i].add(terms.get(j).getName());
        }
        return str;
    }
    public List<String> getWordsFromInput(String sousuo){
        List<String> str=new ArrayList<>();
        //获取terms
        List<Term> terms;
        //分词结果的一个封装，sousuo分词
        Result result = ToAnalysis.parse(sousuo);
        terms=result.getTerms();  //获取terms
        for(int j=0;j<terms.size();j++)
              str.add(terms.get(j).getName());
        return str;
    }
    public List<String> getWordsFromInput(String sousuo,int l){
        List<String> str=new ArrayList<>();
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("t");add("s");add("f");add("v");
            add("a");add("b");add("z");add("r");add("m");
            add("x");
        }}; // 过滤词性
        //获取terms
        List<Term> terms;
        //分词结果的一个封装，sousuo分词
        Result result = ToAnalysis.parse(sousuo);
        terms=result.getTerms();  //获取terms
        for(int j=0;j<terms.size();j++)
            if(expectedNature.contains(terms.get(j).getNatureStr())) str.add(terms.get(j).getName());
        return str;
    }
}

