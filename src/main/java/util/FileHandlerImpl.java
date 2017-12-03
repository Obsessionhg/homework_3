package util;

import vo.StockInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileHandlerImpl implements FileHandler {
    /**
     * This func gets stock information from the given interfaces path.
     * If interfaces don't exit,or it has a illegal/malformed format, return NULL.
     * The filepath can be a relative path or a absolute path
     *
     * @param filePath
     * @return the Stock information array from the interfaces,or NULL
     */
    @Override
    public  StockInfo[] getStockInfoFromFile(String filePath) {   //读入data文件
        //TODO: write your code here
        int k=60,    //  总行数
            i=0;     //记录行数
        String w;    //临时储存整行字符串
        StockInfo[] stockInfo=new StockInfo[k];  //存储文本数组
        for(int i1=0;i1<k;i1++) stockInfo[i1]=new StockInfo(); //实例化对象
        try{
            /*
            建立输入流，链接相应文件，将文件读入数组
             */
            File file=new File(filePath);
            InputStreamReader inStream=new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader bfile=new BufferedReader(inStream); //读
            bfile.readLine();
            while((w=bfile.readLine())!=null){      //读入数据
                String[] str=w.split("\t");
                stockInfo[i].setID(str[0]);
                stockInfo[i].setTitle(str[1]);
                stockInfo[i].setAuthor(str[2]);
                stockInfo[i].setDate(str[3]);
                stockInfo[i].setLastupdate(str[4]);
                stockInfo[i].setContent(str[5]);
                stockInfo[i].setAnswerauthor(str[6]);
                stockInfo[i].setAnswer(str[7]);
                i++;
            }
            bfile.close();
            inStream.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return stockInfo;
    }
}

