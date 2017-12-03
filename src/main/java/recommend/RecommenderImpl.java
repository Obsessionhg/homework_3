package recommend;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;


public class RecommenderImpl implements Recommender {

    @Override
    public double cosSimiliarity(Pair<String,Double>[] pair1,Pair<String,Double>[] pair2){   //////(余弦相似性)
        List<String> str=new ArrayList<>(),   //存放两个新闻的关键字
                str1=new ArrayList<>();  //存放无重复关键字
        for(int i=0;i<pair1.length;i++) str.add(pair1[i].getKey());
        for(int i=0;i<30;i++) str.add(pair2[i].getKey());
        str1.add(str.get(0));
        start:
        for(int i=1;i<str.size();i++){
            for(int j=0;j<str1.size();j++) if(str.get(i)==str1.get(j)) continue start;
            str1.add(str.get(i));
        }
        double[] key1=new double[str1.size()],   //向量1
                key2=new double[str1.size()];   //向量2
        for(int i=0;i<str1.size();i++){
            int f1=0,f2=0;   //计算两组关键字的频数
            for(int j=0;j<pair1.length;j++){
                if(str1.get(i)==pair1[j].getKey()) f1++;
            }
            for(int j=0;j<30;j++) if(str1.get(i)==pair2[j].getKey()) f2++;
            key1[i]=f1;
            key2[i]=f2;
        }
        double x=0,   //分子
                y1=0,   //分母1
                y2=0;   //分母2
        for(int i=0;i<str1.size();i++){
            x=x+key1[i]*key2[i];
            y1=y1+key1[i]*key1[i];
            y2=y2+key2[i]*key2[i];
        }
        y1=Math.sqrt(y1);
        y2=Math.sqrt(y2);
        return x/(y1*y2);
    }
}

