package vo;

public class StockInfo {
    private	String id,   //评论
            title, //标题
            author, //作者
            date,   //发布时间
            lastupdate,  //最新修改时间
            content,    //文章内容
            answerauthor,   //评论者
            answer;   //评论内容
    public void setID(String id){this.id=id;}
    public void setTitle(String title){this.title=title;}
    public void setAuthor(String author){this.author=author;}
    public void setDate(String date){this.date=date;}
    public void setLastupdate(String lastupdate){this.lastupdate=lastupdate;}
    public void setContent(String content){this.content=content;}
    public void setAnswerauthor(String answerauthor){this.answerauthor=answerauthor;}
    public void setAnswer(String answer){this.answer=answer;}
    public String getID(){return id;}
    public String getTitle(){return title;}
    public String getAuthor(){return author;}
    public String getDate(){return date;}
    public String getLastupdate(){return lastupdate;}
    public String getContent(){return content;}
    public String getAnswerauthor(){return answerauthor;}
    public String getAnswer(){return answer;}
}

