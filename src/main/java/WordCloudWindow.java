import com.kennycason.kumo.WordCloud;
import javafx.util.Pair;
import recommend.RecommenderImpl;
import segmenter.ChineseSegmenterImpl;
import tf_idf.TF_IDFImpl;
import util.StockSorterImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class WordCloudWindow {
    JDialog dialog;
    Pair<Integer,Double>[] pairRecommend;
    WindowSearch THIS;
    List<String> str1;
    WordCloud cloud;
    public void build(WindowSearch THIS,String label){
        this.THIS=THIS;
        List<String> str=new ChineseSegmenterImpl().getWordsFromInput(label,0);
        Pair<String,Double>[] pair=new TF_IDFImpl().getResult(str);
        str1=new ArrayList<>();
        for(int i=0;i<pair.length;i++) str1.add(pair[i].getKey());
        pairRecommend=new Pair[THIS.stocks.length];
        for(int i=0;i<THIS.stocks.length;i++) {
            pairRecommend[i]=new Pair<>(i,new RecommenderImpl().cosSimiliarity(pair,THIS.keyWordsOrder[i]));
        }
        new StockSorterImpl().bubbleSort(pairRecommend);
        dialog=new CloudDiaglog(THIS,"词云",true);
    }
    //生成词云图片
    private class CloudDiaglog extends JDialog{
        final CloudDiaglog T_H_I_S=this;
        String str;
        CloudDiaglog(JFrame THIS,String title,boolean modal){
            super(THIS,title,modal);
            init();
            setBounds(350,110,1200,800);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setResizable(false);
            setVisible(true);
        }
        private void init(){
            //初始化
            JPanel panel=new CloudPanel();
            JPanel panel1=new JPanel();
            JPanel jPanel=new JPanel();
            JButton button=new JButton("保存为png文件");
            JLabel title1=new JLabel("<html><body color=\'red\'>可能喜欢</body><html>");
            JScrollPane scrollPane=new JScrollPane(panel1);
            JLabel[] labels=new JLabel[5];
            Box box=Box.createVerticalBox(),box1=Box.createVerticalBox();
            for(int i=0;i<5;i++){
                labels[i]=new JLabel();
                labels[i].setFont(new Font(Font.SERIF,Font.PLAIN,13));
            }

            //设置可能喜欢
            for(int i=0,j=1;i<5;j++) {
                    labels[i].setText("<html><body>"+THIS.stocks[pairRecommend[j].getKey()].getID()+"&emsp;"+THIS.stocks[pairRecommend[j].getKey()].getTitle()+"&emsp;"
                            +THIS.stocks[pairRecommend[j].getKey()].getAuthor()+"&emsp;"+THIS.stocks[pairRecommend[j].getKey()].getDate()+"&emsp;"+THIS.stocks[pairRecommend[j].getKey()].getLastupdate()
                            +"&emsp;"+THIS.stocks[pairRecommend[j].getKey()].getContent()+"&emsp;"+THIS.stocks[pairRecommend[j].getKey()].getAnswerauthor()+"&emsp;"+THIS.stocks[pairRecommend[j].getKey()].getAnswer()+"</body></html>");
                    i++;
            }

            //labels事件
            for(int i=0;i<5;i++) labels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel label=(JLabel)e.getSource();
                    if(!label.getText().isEmpty()){
                        if(e.getClickCount()==2){
                            new WordCloudWindow().build(THIS,label.getText());
                        }
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
            });

            //button事件
            button.addActionListener(new ActionListener() {
                @Override
                //保存png文件
                public void actionPerformed(ActionEvent e) {
                    WordCloudBuilder.writeToFile(cloud,"data.png");
                    JOptionPane.showMessageDialog(dialog,"保存成功","提示",JOptionPane.PLAIN_MESSAGE);
                }
            });

            //格式设置
            button.setFont(new Font(Font.SERIF,Font.PLAIN,15));
            title1.setFont(new Font(Font.SERIF,Font.PLAIN,15));

            panel.setBorder(BorderFactory.createLineBorder(Color.red));
            panel.setPreferredSize(new Dimension(1100,700));

            panel1.setBorder(BorderFactory.createLineBorder(Color.CYAN));
            panel1.setLayout(new GridLayout(20,1));
            panel1.setPreferredSize(new Dimension(50000,650));
            jPanel.setLayout(new FlowLayout());

            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setPreferredSize(new Dimension(300,640));



            //面板排版
            for(int i=0;i<5;i++) {
                panel1.add(labels[i]);
                panel1.add(Box.createVerticalStrut(15));
            }
            box.add(title1);
            box.add(scrollPane);
            jPanel.add(box);
            add(panel,BorderLayout.CENTER);
            add(Box.createVerticalStrut(50),BorderLayout.NORTH);
            add(jPanel,BorderLayout.WEST );
            add(button,BorderLayout.EAST);
            add(Box.createVerticalStrut(50),BorderLayout.SOUTH);
        }

        //词云生成面板类
        private  class CloudPanel extends  JPanel{
            public void paintComponent(Graphics g){
                int x=0,y=0; Color[] colors = new Color[10];
                for (int i = 0; i < colors.length; i++) {
                    colors[i] = new Color(
                            (new Double(Math.random() * 128)).intValue() + 128,
                            (new Double(Math.random() * 128)).intValue() + 128,
                            (new Double(Math.random() * 128)).intValue() + 128);
                }
                //生成词云
                cloud=WordCloudBuilder.buildWordCouldByWordFrequencies(200,200,4,20,10,
                        WordCloudBuilder.buildWordFrequencies(str1),new Color(0xF1F5FF),colors);
                //获取图片
                BufferedImage icon=cloud.getBufferedImage();
                g.drawImage(icon.getScaledInstance(this.getWidth(),this.getHeight(), 0),x,y,this.getWidth(),this.getHeight(),T_H_I_S);
            }
        }
    }
}
