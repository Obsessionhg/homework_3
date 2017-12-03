import javafx.util.Pair;
import recommend.RecommenderImpl;
import segmenter.ChineseSegmenterImpl;
import tf_idf.TF_IDFImpl;
import util.StockSorterImpl;
import vo.StockInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class WindowSearch extends JFrame{

    final WindowSearch THIS=this;   //当前对象
    JFileChooser fileChooser;
    JTextField textField1; //显示导入文件
    JTextField  textField2;  //搜索框
    JButton openFile;//打开文件选择器
    JButton search; //搜索按钮
    JButton analysis; //解析按钮
    Box baseBox,box1,box2;
    JScrollPane scrollPane;
    JPanel panel;
    JLabel label[];
    StockInfo[] stocks;
    Pair<String,Double>[][] keyWordsOrder;
    String str;
    final int COUNT=10;
    WindowSearch(){
        init();
        setBounds(380,140,1250,800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    void init(){
        //初始化
        fileChooser=new JFileChooser();
        setLayout(new FlowLayout());
        search=new JButton();
        openFile=new JButton();
        analysis=new JButton();
        label=new JLabel[COUNT];
        panel=new JPanel();
        for(int i=0;i<COUNT;i++) {
            label[i]=new JLabel();
            label[i].setFont(new Font(Font.SERIF,Font.PLAIN,15));
        }
        //路径框
        textField1=new JTextField(50);
        //搜索框
        textField2=new JTextField(50);


        //设置
        panel.setLayout(new GridLayout(20,1));
        panel.setPreferredSize(new Dimension(50000,650));
        for(int i=0;i<10;i++){
            panel.add(label[i]);
            panel.add(Box.createVerticalStrut(10));
        }
        scrollPane=new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(200,600));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.red));
        textField1.setFont(new Font(Font.SERIF,Font.PLAIN,20));
        textField2.setFont(new Font(Font.SERIF,Font.PLAIN,20));
        search.setText("搜索");
        openFile.setText("打开");
        analysis.setText("解析");


        // 注册事件监听器

        //“打开”按钮事件
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int state=fileChooser.showOpenDialog(THIS);
                if(state==JFileChooser.APPROVE_OPTION){
                    textField1.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        //“解析”按钮事件
        analysis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!textField1.getText().isEmpty()) {
                    //解析文件
                    Analysis.run(textField1.getText(),THIS);
                    JOptionPane.showMessageDialog(THIS,"解析完成","提示",JOptionPane.PLAIN_MESSAGE);
                }else{
                        JOptionPane.showMessageDialog(THIS,"路径框不能为空","错误",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //“搜索”按钮事件
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().isEmpty()){
                    JOptionPane.showMessageDialog(THIS,"路径框不能为空","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for(int i=0;i<COUNT;i++) label[i].setText("");

                //搜索代码
                if(!textField2.getText().isEmpty()){
                    List<String> sousuo=new ChineseSegmenterImpl().getWordsFromInput(textField2.getText());
                    Pair<String,Double>[] search1=new TF_IDFImpl().getResult(sousuo);
                    Pair<Integer,Double>[] order=new Pair[stocks.length];
                    for(int i=0;i<stocks.length;i++) {
                        order[i]=new Pair<>(i,new RecommenderImpl().cosSimiliarity(search1,keyWordsOrder[i]));
                    }
                    new StockSorterImpl().bubbleSort(order);
                    for(int i=0;i<COUNT;i++) {
                        StockInfo stock = stocks[order[i].getKey()];
                        String strLabel;
                        strLabel=((i + 1) + "." + stock.getID() + "\t" + stock.getTitle() + "\t" +
                                stock.getAuthor() + "\t" + stock.getDate() + "\t" + stock.getLastupdate() + "\t" + stock.getContent() +
                                "\t" + stock.getAnswerauthor() + "\t" + stock.getAnswer());
                        List<String> stringList=new ChineseSegmenterImpl().getWordsFromInput(strLabel);
                        for(int j=0;j<stringList.size();j++) {
                            for (int k = 0; k < sousuo.size(); k++)
                                if(stringList.get(j).equals(sousuo.get(k))) {
                                label[i].setText(label[i].getText()+"<font color=\'red\'>"+stringList.get(j)+"</font>");
                                continue;
                                }
                            label[i].setText(label[i].getText()+stringList.get(j));
                        }
                        label[i].setText("<html><body>"+label[i].getText()+"</body></html>");
                    }
                    JOptionPane.showMessageDialog(THIS,"输出完成","提示",JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        //panel监听鼠标双击
       for(int i=0;i<COUNT;i++) label[i].addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               JLabel label=(JLabel)e.getSource();
               if(!label.getText().isEmpty()){
                   super.mouseClicked(e);
                   if(e.getClickCount()==2){
                       new WordCloudWindow().build(THIS,label.getText());
                   }
               }
           }
       });

        //label监听鼠标事件
       for(int i=0;i<COUNT;i++) label[i].addMouseListener(new LabelMouseListener());


        //窗口界面排版
        box1=Box.createHorizontalBox();
        box2=Box.createHorizontalBox();
        box1.add(textField1);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(openFile);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(analysis);
        box2.add(textField2);
        box2.add(Box.createHorizontalStrut(10));
        box2.add(search);
        baseBox=Box.createVerticalBox();
        baseBox.add(box1);
        baseBox.add(Box.createVerticalStrut(10));
        baseBox.add(box2);
        baseBox.add(Box.createVerticalStrut(30));
        baseBox.add(scrollPane);
        baseBox.setPreferredSize(new Dimension(1000,700));
        add(baseBox);
    }

    //label鼠标事件监听器
    private class LabelMouseListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel label=(JLabel)e.getSource();
            if(!label.getText().isEmpty()){

            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel label=(JLabel)e.getSource();
            if(!label.getText().isEmpty()) {
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                str=label.getText();
                label.setText("<html><body color=\'blue\'><u>"+label.getText()+"</u><body></html>");
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel label=(JLabel)e.getSource();
            if(!label.getText().isEmpty()){
                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                label.setText(str);
            }
        }
    }
}